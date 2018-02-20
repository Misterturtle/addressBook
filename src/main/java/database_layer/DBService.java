package database_layer;

import business_models.Contact;
import database_layer.models.ContactRow;
import database_layer.models.TableColumn;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.JDBCType.VARCHAR;

public class DBService {

    public String DB_NAME = "addressBook";
    public String TABLE_NAME = "addressBookTable";

    private Properties connectionProps = new Properties();
    private Connection connection;
    private Statement statement;
    private TableColumn FIRST_COLUMN = new TableColumn("firstName", VARCHAR, 50);

    private boolean databaseExtant;

    public DBService() throws SQLException {
        connectionProps.put("user", "pairing");
        connectionProps.put("password", "commonPass");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", connectionProps);
        statement = connection.createStatement();
        setDatabase();
        createTable();
    }

    private void setDatabase() {
        try {
            statement.executeUpdate("use " + DB_NAME + ";");
            databaseExtant = true;
        } catch (SQLException e) {
            switch (e.getMessage()) {
                case "Unknown database 'addressbook'":
                    try {
                        createDatabase();
                        statement.executeUpdate("use " + DB_NAME + ";");
                        databaseExtant = true;
                    }
                    catch (SQLException e2) {
                        System.out.println(e2.getMessage());
                        System.out.println("Could not access/create database [catch block]");
                        System.exit(1);
                    }
                    break;

                default:
                    System.out.println(e.getMessage());
                    System.out.println("Could not access/create database [default block]");
                    System.exit(1);
            }

        }
    }


    private void createDatabase() throws SQLException {
        statement.executeUpdate("create database " + DB_NAME +";");
    }

    private void createTable() throws SQLException {
        try {
            String query = String.format("create table %s(%s %s (%s));", TABLE_NAME, FIRST_COLUMN.name, FIRST_COLUMN.type, FIRST_COLUMN.typeParam);
            statement.executeUpdate(query);
        }
        catch (SQLException e) {
            switch (e.getMessage()) {
                case "Table 'addressbooktable' already exists":
                    break;
                default:
                    throw e;
            }
        }
    }

    public boolean insertRow(String firstName) {
        try {
            String query = String.format("insert into %s (%s) VALUES ('%s');", TABLE_NAME, FIRST_COLUMN.name, firstName);
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ContactRow[] getRowByFirstName(String firstName) throws SQLException {


        ResultSet resultSet = statement.executeQuery("select * from " + TABLE_NAME + " where firstName = '" + firstName + "';");
        List<ContactRow> contactRow = new ArrayList<>();

        while (resultSet.next()) {

            String resultFirstName = resultSet.getString("firstName");
            contactRow.add(new ContactRow(resultFirstName));
        }

        return contactRow.toArray(new ContactRow[contactRow.size()]);
    }

    public boolean isDatabaseExtant() {
        return databaseExtant;
    }

}
