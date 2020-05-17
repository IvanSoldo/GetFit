package sample.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String DATABASE_FILE = "dat/dbh2.properties";

    public static Connection connectToDb() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_FILE));
        String databaseURL = properties.getProperty("database");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(databaseURL,username,password);
        return connection;
    }
}
