package sample.service;

import sample.models.Food;

public interface FoodService {

    Food calculateFoodByServingSize(Food food, Double numberOfServings);
}
