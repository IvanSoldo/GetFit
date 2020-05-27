package sample.service;

import sample.models.Food;

import java.util.ArrayList;

public interface FoodService {

    Food calculateFoodByServingSize(Food food, Double numberOfServings);

    int calculateTotalFoodCalories(ArrayList<Food> foods);

    int calculateTotalFoodProteins(ArrayList<Food> foods);

    int calculateTotalFoodCarbs(ArrayList<Food> foods);

    int calculateTotalFoodFats(ArrayList<Food> foods);
}
