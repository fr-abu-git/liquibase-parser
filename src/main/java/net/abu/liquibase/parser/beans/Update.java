package net.abu.liquibase.parser.beans;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name="update")
@XmlAccessorType(XmlAccessType.FIELD)
public class Update {

    @XmlAttribute(name="tableName")
    private String tableName;

    @XmlElement(name="column")
    private List<Column> columns;

    @XmlElement(name="where")
    private String where;

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

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
