package sample.repositories;

import sample.models.Food;
import sample.utilities.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodRepositoryImpl implements FoodRepository {

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public ArrayList<Food> getFoodsFromDb() {
        ArrayList<Food> foods = new ArrayList<>();
        try (Connection connection = databaseConnection.connectToDb()) {
            String sqlQuery = "select * from foods";
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
}
