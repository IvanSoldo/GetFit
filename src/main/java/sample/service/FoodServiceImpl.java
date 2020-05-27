package sample.service;

import sample.models.Food;

import java.util.ArrayList;

public class FoodServiceImpl implements FoodService {

    @Override
    public Food calculateFoodByServingSize(Food food, Double numberOfServings) {
        food.setCalories((int) (food.getCalories() * numberOfServings));
        food.setCarbs((int) (food.getCarbs() * numberOfServings));
        food.setFats((int) (food.getFats() * numberOfServings));
        food.setProteins((int) (food.getProteins() * numberOfServings));
        food.setAmount((int) (food.getServingSize() * numberOfServings));
        return food;
    }

    @Override
    public int calculateTotalFoodCalories(ArrayList<Food> foods) {
        int result = 0;
        for (int i = 0; i < foods.size(); i++) {
            result += foods.get(i).getCalories();
        }
        return result;
    }

    @Override
    public int calculateTotalFoodProteins(ArrayList<Food> foods) {
        int result = 0;
        for (int i = 0; i < foods.size(); i++) {
            result += foods.get(i).getProteins();
        }
        return result;
    }

    @Override
    public int calculateTotalFoodCarbs(ArrayList<Food> foods) {
        int result = 0;
        for (int i = 0; i < foods.size(); i++) {
            result += foods.get(i).getCarbs();
        }
        return result;
    }

    @Override
    public int calculateTotalFoodFats(ArrayList<Food> foods) {
        int result = 0;
        for (int i = 0; i < foods.size(); i++) {
            result += foods.get(i).getFats();
        }
        return result;
    }
}
