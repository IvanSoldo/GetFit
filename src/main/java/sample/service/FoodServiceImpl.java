package sample.service;

import sample.models.Food;

public class FoodServiceImpl implements FoodService {

    @Override
    public Food calculateFoodByServingSize(Food food, Double numberOfServings) {
        food.setCalories((int) (food.getCalories() * numberOfServings));
        food.setCarbs((int) (food.getCarbs() * numberOfServings));
        food.setFats((int) (food.getFats() * numberOfServings));
        food.setProteins((int) (food.getProteins() * numberOfServings));
        return food;
    }
}
