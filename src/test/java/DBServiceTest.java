import database_layer.DBService;
import database_layer.models.ContactRow;
import database_layer.models.TableColumn;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.JDBCType.VARCHAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class DBServiceTest {


    DBService dbService;

    List<String> existingDB = new ArrayList<>();

    @Before
    public void setup() throws SQLException {
        existingDB = getCurrentDBNames();

        dbService = new DBService();
    }



    @After
    public void takeDown() throws SQLException {
        if(!connection.isClosed()){
            deleteTestCreatedDB();
            existingDB = new ArrayList<String>();
        }
        connection.close();
    }


    private ArrayList<String> getCurrentDBNames() throws Exception{
        ArrayList<String> localList = new ArrayList<>();
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
    public void onStartupTheServiceShouldCreateADatabase() throws SQLException{

        assertTrue(dbService.isDatabaseExtant());
    }

    @Test
    public void theServiceShouldInsertARowToTheTable(){
        boolean success = dbService.insertRow("Cory");

        assertTrue(success);
    }

    @Test
    public void theServiceShouldRetrieveAContactRowFromTheTable() throws Exception{
        ContactRow[] contactRow = dbService.getRowByFirstName("Cory");


        assertEquals("Cory", contactRow[0].firstName);
    }
}
