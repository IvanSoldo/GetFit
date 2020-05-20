package sample.repositories;

import sample.models.Account;
import sample.models.Calories;
import sample.models.Macros;
import sample.utilities.DatabaseConnection;

import java.io.IOException;
import java.sql.*;

public class AccountRepositoryImpl implements AccountRepository {

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
    public String logIn(Account account) {
        String returnString = "";
        Calories calories = new Calories();
        Macros macros = new Macros();
        try (Connection connection = databaseConnection.connectToDb()) {
            String sqlQuery = "select * from accounts where username ='" + account.getUsername() + "' and password ='" + account.getPassword() + "'";
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlQuery);
            if (resultSet.next()) {
                calories.setCalories(resultSet.getInt("calories"));
                macros.setProteins(resultSet.getInt("proteins"));
                macros.setCarbs(resultSet.getInt("carbs"));
                macros.setFats(resultSet.getInt("fats"));
                account.setMacros(macros);
                account.setCalories(calories);
                returnString = "Success";
            } else {
                returnString = "Failed";
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    @Override
    public void saveCalories(Account account) {
        try (Connection connection = databaseConnection.connectToDb()) {
            String sqlQuery = "update accounts set calories =" + account.getCalories().getCalories() + "where username ='" + account.getUsername() + "'";
            Statement query = connection.createStatement();
            query.executeUpdate(sqlQuery);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMacros(Account account) {
        try (Connection connection = databaseConnection.connectToDb()) {
            String sqlQuery = "update accounts set proteins ="
                    + account.getMacros().getProteins() +
                    ",  carbs ="+ account.getMacros().getCarbs()
                    + ", fats =" + account.getMacros().getFats()
                    + "where username ='" + account.getUsername() + "'";
            Statement query = connection.createStatement();
            query.executeUpdate(sqlQuery);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
