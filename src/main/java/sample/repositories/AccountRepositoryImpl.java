package sample.repositories;

import sample.models.Account;
import sample.utilities.DatabaseConnection;

import java.io.IOException;
import java.sql.*;

public class AccountRepositoryImpl implements AccountRepository{

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public void signUp(Account account) {
        try (Connection connection = databaseConnection.connectToDb()) {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("insert into accounts(username, password)"
                    + "values (?,?)");
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public String logIn(Account account)  {
        String returnString = "";
        try (Connection connection = databaseConnection.connectToDb()) {
            String sqlQuery = "select * from accounts where username ='" + account.getUsername() + "' and password ='" + account.getPassword() + "'";
            Statement query = connection.createStatement();
            ResultSet resultSet =query.executeQuery(sqlQuery);
            if (resultSet.next()) {
                returnString = "Success";
            } else {
                returnString = "Failed";
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }
}
