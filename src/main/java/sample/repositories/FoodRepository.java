package sample.repositories;

import sample.models.Account;
import sample.models.Food;

import java.util.ArrayList;

public interface FoodRepository {

    ArrayList<Food> getFoodsFromDb();

    void addFoodToDb(Account account, Food food);

    void  insertIntoTotalFoodsTable(Account account, Food food);

    void resetTotalFoodsForUser(Account account);

    ArrayList<Food> getTotalFoodsFromDb();
}
