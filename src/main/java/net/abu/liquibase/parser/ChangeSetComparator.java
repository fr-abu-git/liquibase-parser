package net.abu.liquibase.parser;

import net.abu.liquibase.parser.beans.ChangeSet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ChangeSetComparator implements Comparator<ChangeSet> {

    private String[] tables = {"roles", "users", "user_roles", "versions",
            "bb_coaches", "bb_rulesets", "bb_rosters", "bb_staffprofiles",
            "bb_characteristicprofiles", "bb_staffprofilevalues", "bb_playerprofiles", "bb_characteristicvalues",
            "bb_skillcategories", "bb_skillprofiles", "bb_playerskilllvalues", "bb_improvementprofiles",
            "bb_achievementprofiles", "bb_injuries", "bb_playerpositions", "bb_teams", "bb_players", "bb_staffs",
            "bb_leagues", "bb_members", "bb_seasons", "bb_competitions", "bb_commissioners", "bb_schedules",
            "bb_rounds", "bb_participatingteams", "bb_tiebreakers", "bb_matches", "bb_opponents", "bb_participatingplayers",
            "bb_achievements", "bb_achievement_totals", "bb_improvements", "bb_leavingplayers", "bb_scheduled_mails"};

    private Map<String, Integer> ordering = new HashMap<>();

    public ChangeSetComparator() {
        System.err.println(String.format("%d tables recencées", tables.length));
        for (int i = 0; i < tables.length; i++) {
            ordering.put(tables[i], i);
        }
    }

    @Override
    public int compare(ChangeSet o1, ChangeSet o2) {
        String t1 = o1.getInserts().get(0).getTableName();
        String t2 = o2.getInserts().get(0).getTableName();
        try {
            return ordering.get(t1).compareTo(ordering.get(t2));
        } catch (NullPointerException e) {
            System.err.println(String.format("t1: %s, t2: %s", t1, t2));
            throw e;
        }
    }
}
