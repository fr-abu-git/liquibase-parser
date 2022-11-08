package net.abu.liquibase.parser;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import net.abu.liquibase.parser.beans.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LiquibaseParser {

    public static final String AUTHOR = "abuffet";

    public static void main(String[] args) throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance(DatabaseChangeLog.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        DatabaseChangeLog changeLog = (DatabaseChangeLog) unmarshaller.unmarshal(new File("liquibase-datas.xml"));

        // tri des tables selon leurs dépendances
        changeLog.getChangeSets().sort(new ChangeSetComparator());

        // gestion de la foreign key bb_users.coach_id
        updateTableUSers(changeLog.getChangeSets());

        //calcul des n° de séquence
        Map<String, Long> sequences = computeSequenceValue(changeLog.getChangeSets());
        appendSequenceChangeSet(sequences, changeLog.getChangeSets());

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(changeLog, new FileWriter("output.xml"));
    }

    private static void updateTableUSers(List<ChangeSet> changeSets) {
        Map<Long, Long> userIds = new HashMap<>();

        // dans un premier temps, on supprime la colonne coach_id car les coachs ne sont pas encore insérés
        changeSets.stream().flatMap(
                        changeSet -> changeSet.getInserts().stream().filter(insert -> insert.getTableName().equals("users")))
                .forEach(insert -> {
                    Long id = insert.getColumns().stream().filter(column -> column.getName().equals("id"))
                            .mapToLong(col -> Long.valueOf(col.getValueNumeric())).findAny().orElseThrow();
                    insert.getColumns().stream().filter(column -> column.getName().equals("coach_id"))
                            .filter(column -> column.getValueNumeric() != null)
                            .mapToLong(col -> Long.valueOf(col.getValueNumeric())).findAny().ifPresent(
                                    coachId -> userIds.put(id, coachId)
                            );

                    insert.getColumns().removeIf(column -> column.getName().equals("coach_id"));
                });

        // on génère les update pour remettre les coach_id sur la table users
        List<Update> updates = new ArrayList<>();
        userIds.forEach((id, coachId) -> {
            Update update = new Update();
            update.setTableName("users");
            updates.add(update);
            Column column = new Column();
            update.setColumns(Arrays.asList(column));
            column.setName("coach_id");
            column.setValue(String.valueOf(coachId));
            update.setWhere(String.format("id=%d", id));
        });

        // on insert les updates à la fin, on est sûr que tous les éléments ont été insérés
        ChangeSet changeSet = new ChangeSet();
        changeSet.setAuthor(AUTHOR);
        changeSet.setId(String.format("%s-2", new SimpleDateFormat("yyyyMMdd").format(new Date())));
        changeSet.setUpdates(updates);

        changeSets.add(changeSet);
    }

    private static void appendSequenceChangeSet(Map<String, Long> sequences, List<ChangeSet> changeSets) {
        Sql sql = new Sql();
        sql.setDbms("postgresql");
        List<String> contents = new ArrayList<>();
        contents.add("");
        sequences.forEach((tableName, id) -> contents.add(String.format("ALTER SEQUENCE %s_id_seq RESTART WITH %d;", tableName, id + 1)));
        contents.add("");
        sql.setContent(String.join("\n\t\t", contents));

        ChangeSet changeSet = new ChangeSet();
        changeSet.setAuthor(AUTHOR);
        changeSet.setId(String.format("%s-1", new SimpleDateFormat("yyyyMMdd").format(new Date())));
        changeSet.setSql(Arrays.asList(sql));
        changeSets.add(changeSet);
    }

    private static Map<String, Long> computeSequenceValue(List<ChangeSet> changeSets) {
        Map<String, Long> sequences = new HashMap<>();

        changeSets.stream().filter(changeSet -> changeSet.getInserts() != null)
                .flatMap(changeSet -> changeSet.getInserts().stream()).forEach(insert -> {
                    insert.getColumns().stream().filter(column -> column.getName().equals("id")).forEach(column -> {
                        if (column.getValueNumeric() == null) {
                            throw new IllegalStateException();
                        }
                        Long valueOf = Long.valueOf(column.getValueNumeric());
                        if (!sequences.containsKey(insert.getTableName())) {
                            sequences.put(insert.getTableName(), valueOf);
                        } else if (sequences.get(insert.getTableName()).compareTo(valueOf) < 0) {
                            sequences.put(insert.getTableName(), valueOf);
                        }
                    });
                });

        return sequences;
    }

}
