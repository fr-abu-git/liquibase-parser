package net.abu.liquibase.parser.beans;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name="changeSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChangeSet {

    @XmlAttribute(name="author")
    private String author;

    @XmlAttribute(name="id")
    private String id;

    @XmlElement(name="insert")
    private List<Insert> inserts;

    @XmlElement(name="update")
    private List<Update> updates;

    @XmlElement(name="sql")
    private List<Sql> sql;

    public List<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Update> updates) {
        this.updates = updates;
    }

    public List<Sql> getSql() {
        return sql;
    }

    public void setSql(List<Sql> sql) {
        this.sql = sql;
    }

    public List<Insert> getInserts() {
        return inserts;
    }

    public void setInserts(List<Insert> inserts) {
        this.inserts = inserts;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
