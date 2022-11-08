package net.abu.liquibase.parser.beans;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="column")
@XmlAccessorType(XmlAccessType.FIELD)
public class Column {

    @XmlAttribute(name="name")
    private String name;

    @XmlAttribute(name="value")
    private String value;

    @XmlAttribute(name="valueDate")
    private String valueDate;

    @XmlAttribute(name="valueNumeric")
    private String valueNumeric;

    @XmlAttribute(name="valueBoolean")
    private String valueBoolean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(String valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValueBoolean() {
        return valueBoolean;
    }

    public void setValueBoolean(String valueBoolean) {
        this.valueBoolean = valueBoolean;
    }
}
