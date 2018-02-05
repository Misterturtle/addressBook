import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

public class UnderstandingMySQLTests {

    Properties connectionProps = new Properties();

    @Before
    public void setup(){
        connectionProps.put("user", "pairing");
        connectionProps.put("password", "commonPass");
    }

    @Test
    public void aConnectionShouldBeEstablished() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", connectionProps);
        connection.close();

        Assert.assertNotNull(connection);
    }

    @Test
    public void aDatabaseShouldBeCreatedThroughAConnection() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", connectionProps);
        String expected = "";
        String query = "show databases;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        System.out.println(resultSet.getString(1));
        expected = resultSet.getString(1);

        Assert.assertEquals(expected, "information_schema");
    }


}
