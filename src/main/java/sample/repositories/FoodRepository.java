package sample.repositories;

import sample.models.Food;

import java.util.ArrayList;

public interface FoodRepository {

    ArrayList<Food> getFoodsFromDb();
}
