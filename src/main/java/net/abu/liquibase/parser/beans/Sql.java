package net.abu.liquibase.parser.beans;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name="sql")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sql {

    @XmlAttribute(name="dbms")
    private String dbms;

    @XmlValue
    private String content;

    public String getDbms() {
        return dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
