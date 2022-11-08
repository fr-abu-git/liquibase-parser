package net.abu.liquibase.parser.beans;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name="insert")
@XmlAccessorType(XmlAccessType.FIELD)
public class Insert {

    @XmlAttribute(name="tableName")
    private String tableName;

    @XmlElement(name="column")
    private List<Column> columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
