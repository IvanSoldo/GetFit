package sample.service;

import sample.models.Food;
import sample.models.RemainingCalories;

public class RemainingCaloriesServiceImpl implements RemainingCaloriesService  {

    @Override
    public void subtractFoodFromRemainingCalories(RemainingCalories remainingCalories, Food food) {
        remainingCalories.setCalories(remainingCalories.getCalories() - food.getCalories());
        remainingCalories.setProteins(remainingCalories.getProteins() - food.getProteins());
        remainingCalories.setCarbs(remainingCalories.getCarbs() - food.getCarbs());
        remainingCalories.setFats(remainingCalories.getFats() - food.getFats());
    }
}
