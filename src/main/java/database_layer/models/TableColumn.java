package database_layer.models;

import java.sql.SQLType;

public class TableColumn<T> {
    public final String name;
    public final SQLType type;
    public final T typeParam;

    public TableColumn(String name, SQLType type, T typeParamter) {
        this.name = name;
        this.type = type;
        this.typeParam = typeParamter;
    }

    public TableColumn(String name, SQLType type){
        this.name = name;
        this.type = type;
        this.typeParam = null;
    }
}
