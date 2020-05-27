package sample.repositories;

import sample.models.Account;
import sample.models.RemainingCalories;
import sample.utilities.DatabaseConnection;

import java.io.IOException;
import java.sql.*;

public class RemainingCaloriesRepositoryImpl implements RemainingCaloriesRepository {

    private DatabaseConnection databaseConnection = new DatabaseConnection();


    @Override
    public void updateRemainingCalories(RemainingCalories remainingCalories) {
        try (Connection connection = databaseConnection.connectToDb()) {
            String sqlQuery = "update REMAINING_CALORIES set"
                    + " calories = " + remainingCalories.getCalories()
                    + ", proteins =" + remainingCalories.getProteins()
                    + ", carbs =" + remainingCalories.getCarbs()
                    + ", fats =" + remainingCalories.getFats()
                    + " where ACCOUNT_ID =" + remainingCalories.getAccountID();
            Statement query = connection.createStatement();
            query.executeUpdate(sqlQuery);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createRemainingCaloriesTable(RemainingCalories remainingCalories) {
        try (Connection connection = databaseConnection.connectToDb()) {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("insert into REMAINING_CALORIES (calories, proteins, carbs, fats, ACCOUNT_ID)"
                            + "values(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, remainingCalories.getCalories());
            preparedStatement.setInt(2, remainingCalories.getProteins());
            preparedStatement.setInt(3, remainingCalories.getCarbs());
            preparedStatement.setInt(4, remainingCalories.getFats());
            preparedStatement.setInt(5, remainingCalories.getAccountID());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRemainingCaloriesFromDB(RemainingCalories remainingCalories) {
        try (Connection connection = databaseConnection.connectToDb()) {
            String sqlQuery = "select * from REMAINING_CALORIES where ACCOUNT_ID = " + remainingCalories.getAccountID();
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlQuery);
            if (resultSet.next()) {
                remainingCalories.setCalories(resultSet.getInt("calories"));
                remainingCalories.setProteins(resultSet.getInt("proteins"));
                remainingCalories.setCarbs(resultSet.getInt("carbs"));
                remainingCalories.setFats(resultSet.getInt("fats"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetRemainingCalories(Account account) {
        try (Connection connection = databaseConnection.connectToDb()) {
                String sqlQuery = "update REMAINING_CALORIES set"
                    + " calories = " + account.getCalories().getCalories()
                    + ", proteins =" + account.getMacros().getProteins()
                    + ", carbs =" + account.getMacros().getCarbs()
                    + ", fats =" + account.getMacros().getFats()
                    + " where ACCOUNT_ID =" + account.getId();
            Statement query = connection.createStatement();
            query.executeUpdate(sqlQuery);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
