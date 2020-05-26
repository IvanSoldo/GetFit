package sample.repositories;

import sample.models.Account;
import sample.models.Food;
import sample.utilities.ApplicationState;
import sample.utilities.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class FoodRepositoryImpl implements FoodRepository {

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private ApplicationState applicationState = new ApplicationState();

    @Override
    public ArrayList<Food> getFoodsFromDb() {
        ArrayList<Food> foods = new ArrayList<>();
        try (Connection connection = databaseConnection.connectToDb()) {
            String sqlQuery = "select * from foods"
                    + " UNION"
                    + " SELECT name, calories, proteins, carbs, fats, servingSize from CUSTOM_FOODS"
                    + " where ACCOUNTID = " + applicationState.getAccount().getId();
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery(sqlQuery);
            while (resultSet.next()) {
                foods.add(new Food(resultSet.getString("name"), resultSet.getInt("calories"),
                        resultSet.getInt("proteins"), resultSet.getInt("carbs"), resultSet.getInt("fats"), resultSet.getDouble("servingSize")));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return foods;
    }

    @Override
    public void addFoodToDb(Account account, Food food) {
        try (Connection connection = databaseConnection.connectToDb()) {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("insert into CUSTOM_FOODS(name, calories, proteins, carbs, fats, servingsize, accountId)"
                            + "values (?,?,?,?,?,?,?)");
            preparedStatement.setString(1, food.getName());
            preparedStatement.setInt(2, food.getCalories());
            preparedStatement.setInt(3, food.getProteins());
            preparedStatement.setInt(4, food.getCarbs());
            preparedStatement.setInt(5, food.getFats());
            preparedStatement.setInt(6, (int) food.getServingSize());
            preparedStatement.setInt(7, account.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
