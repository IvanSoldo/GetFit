package sample.service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {

    private static final String DATABASE_FILE = "dat/dbh2.properties";

    private static Connection spajanjeNaBazu() throws SQLException, IOException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE));
        String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
        String korisnickoIme = svojstva.getProperty("korisnickoIme");
        String lozinka = svojstva.getProperty("lozinka");
        Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);
        System.out.println("asdasdas");
        return veza;
    }

    public static void pohraniNoviStan(){
        try (Connection veza = spajanjeNaBazu()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement("insert into person(ime) "
                            + "values (?);");
            preparedStatement.setString(1, "a");
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void getData() throws IOException, SQLException {
        StringBuilder sqlUpit = new StringBuilder(
                "SELECT * FROM person where ime =" + "'a'");
        Connection connection = spajanjeNaBazu();
        Statement query = connection.createStatement();
        ResultSet resultSet = query.executeQuery(sqlUpit.toString());
        while (resultSet.next()) {
            String id = resultSet.getString("ime");
            System.out.println(id);
        }
    }


}
