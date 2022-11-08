package net.abu.liquibase.parser.beans;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="databaseChangeLog")
@XmlAccessorType(XmlAccessType.FIELD)
public class DatabaseChangeLog {

    @XmlElement(name="changeSet")
    private List<ChangeSet> changeSets;

    public List<ChangeSet> getChangeSets() {
        return changeSets;
    }

    public void setChangeSets(List<ChangeSet> changeSets) {
        this.changeSets = changeSets;
    }
}
