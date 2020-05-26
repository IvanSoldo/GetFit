package sample.service;

import sample.models.Food;
import sample.models.RemainingCalories;

public interface RemainingCaloriesService {

    void subtractFoodFromRemainingCalories(RemainingCalories remainingCalories, Food food);

}
