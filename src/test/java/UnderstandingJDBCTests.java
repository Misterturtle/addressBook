import com.mysql.cj.api.xdevapi.Table;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.sql.JDBCType.DATE;
import static java.sql.JDBCType.VARCHAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UnderstandingJDBCTests {

    Properties connectionProps = new Properties();
    Connection connection;
    Statement statement;
    List<String> existingDB = new ArrayList<>();
    String DB_NAME = "super_heroes";
    String TABLE_NAME = "super_heroes_table";
    TableColumn FIRST_COLUMN = new TableColumn("name", VARCHAR, 50);

    @Before
    public void setup() throws Exception {
        connectionProps.put("user", "pairing");
        connectionProps.put("password", "commonPass");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", connectionProps);
        statement = connection.createStatement();
        existingDB = getCurrentDBNames();
    }

    @After
    public void takeDown() throws Exception {
        if(!connection.isClosed()){
            deleteTestCreatedDB();
            existingDB = new ArrayList<String>();
        }
        connection.close();
    }


    private ArrayList<String> getCurrentDBNames() throws Exception{
        ArrayList<String> localList = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("show databases;");
        while(rs.next()){
            localList.add(rs.getString(1));
        }
        return localList;
    }

    private void deleteTestCreatedDB() throws Exception{
        ArrayList<String> currentDB = getCurrentDBNames();
        for(int i = 0; i < currentDB.size(); i++){
            if(!existingDB.contains(currentDB.get(i))){
                statement.executeUpdate("drop database " + currentDB.get(i) + ";");
            }
        }
    }

    @Test
    public void aConnectionShouldBeEstablished() throws Exception {
        assertNotNull(connection);
    }

    @Test
    public void aCloseConnectionShouldNotBeNull() throws Exception {
        connection.close();
        assertNotNull(connection);
    }

    @Test
    public void aDatabaseShouldBeReadFromAResultSet() throws Exception {
        ResultSet resultSet = statement.executeQuery("show databases;");
        resultSet.next();

        String actual = resultSet.getString(1);
        String expected = "information_schema";

        assertEquals(expected, actual);
    }

    @Test
    public void aDatabaseShouldBeCreated() throws Exception {
        statement.executeUpdate("create database super_heroes;");
        ResultSet rs = statement.executeQuery("show databases;");
        Boolean actual = false;
        int counter = 0;
        while(rs.next()){
            if(rs.getString(1).equals("super_heroes")){
                actual = true;
                counter += 1;
            }
        }

        assertEquals(true, actual);
        assertEquals(1, counter);
    }

    @Test
    public void aTableShouldBeCreated() throws Exception {
        statement.executeUpdate("create database super_heroes;");
        statement.executeUpdate("use super_heroes");
        statement.executeUpdate("create table super_heroes(name varchar(50));");
        ResultSet rs = statement.executeQuery("show tables");
        rs.next();
        String tableTitle = rs.getString(1);

        assertEquals("super_heroes", tableTitle);
    }

    @Test
    public void fieldsInATableShouldBeDefined() throws Exception {
        statement.executeUpdate("create database super_heroes;");
        statement.executeUpdate("use super_heroes");
        statement.executeUpdate("create table super_heroes_table(name VARCHAR (50));");
        ResultSet rs = statement.executeQuery("describe super_heroes_table;");
        rs.next();
        String column1FieldDesc = rs.getString(1);

        assertEquals("name", column1FieldDesc);
    }

    @Test
    public void aRecordShouldBeAddedToTheTable() throws Exception {
        createDefaultDBAndTable(new TableColumn("name", VARCHAR, 50));
        statement.executeUpdate("insert into super_heroes_table (name) VALUES ('Batman');");

        ResultSet resultSet = statement.executeQuery("select name from super_heroes_table;");
        resultSet.next();
        String firstField = resultSet.getString(1);

        assertEquals("Batman", firstField);

    }

    @Test
    public void aRecordShouldBeAbleToBeDeleted() throws Exception{
        createDefaultDBAndTable(FIRST_COLUMN);
        statement.executeUpdate("insert into super_heroes_table (name) VALUES ('Batman');");
        statement.executeUpdate("delete from super_heroes_table where name = 'Batman';");

        ResultSet rs = statement.executeQuery("select name from super_heroes_table;");

        assertEquals(false, rs.next());
    }

    @Test
    public void aRecordShouldBeAbleToBeAltered() throws Exception{
        createDefaultDBAndTable(FIRST_COLUMN);
        statement.executeUpdate("insert into super_heroes_table (name) VALUES ('Batman');");
        statement.executeUpdate("update super_heroes_table set name = 'Superman' where name = 'Batman';");

        ResultSet rs = statement.executeQuery("select name from super_heroes_table;");
        rs.next();
        String actual = rs.getString(1);


        assertEquals("Superman", actual);
    }

    @Test
    public void aRecordShouldBeAbleToBeFoundByFirstField() throws Exception {
        TableColumn secondColumn = new TableColumn("releaseDate", DATE);
        createDefaultDBAndTable(FIRST_COLUMN, secondColumn);
        statement.executeUpdate("insert into super_heroes_table (name, releaseDate) VALUES ('Batman', '2018-01-02');");
        statement.executeUpdate("insert into super_heroes_table (name, releaseDate) VALUES ('Superman', '2012-05-06');");
        statement.executeUpdate("insert into super_heroes_table (name, releaseDate) VALUES ('Spiderman', '2009-07-08');");

        ResultSet rs = statement.executeQuery("SELECT releaseDate from super_heroes_table where name = 'Superman';");

        rs.next();

        String actual = rs.getString(1);

        assertEquals("2012-05-06", actual);

    }

    private void createDatabase(String name)throws Exception {
        statement.executeUpdate(String.format("create database %s", name));
    }


    private void createDefaultDBAndTable(TableColumn firstColumn, TableColumn ... extraColumns) throws Exception{
        createDatabase(DB_NAME);
        statement.executeUpdate(String.format("use %s", DB_NAME));
        statement.executeUpdate(String.format("create table %s(%s %s (%s));", TABLE_NAME, firstColumn.name, firstColumn.type.getName(), firstColumn.typeParam));
        for(int i =0; i<extraColumns.length; i++){
            if(extraColumns[i].typeParam != null){
                statement.executeUpdate(String.format("alter table %s add %s %s(%s);",TABLE_NAME, extraColumns[i].name, extraColumns[i].type, extraColumns[i].typeParam));
            } else {
                statement.executeUpdate(String.format("alter table %s add %s %s;", TABLE_NAME, extraColumns[i].name, extraColumns[i].type));
            }

        }

    }


}
