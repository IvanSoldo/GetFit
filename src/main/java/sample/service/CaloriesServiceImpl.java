package sample.service;

import sample.models.ActivityLevel;
import sample.models.Bmr;
import sample.models.Calories;

public class CaloriesServiceImpl implements CaloriesService {


    @Override
    public Calories bmrToCalories(Bmr bmr, String goal, ActivityLevel activityLevel) {
        Calories calories = new Calories();

        Integer caloriesFromBmr = calculateCaloriesFromGoal(bmr, goal);
        double calculatedCalories = calculateCaloriesFromActivityLevel(caloriesFromBmr, activityLevel);

        calories.setCalories((int) calculatedCalories);
        return calories;
    }

    @Override
    public ActivityLevel getActivityLevel(String level) {
        if (level.equals("Sedentary")) {
            return ActivityLevel.SEDENTARY;
        } else if (level.equals("Lightly Active")) {
            return ActivityLevel.LIGHTLY;
        } else if (level.equals("Moderately Active")) {
            return ActivityLevel.MODERATELY;
        } else if (level.equals("Very Active")) {
            return ActivityLevel.VERY;
        } else if (level.equals("Extremely Active")) {
            return ActivityLevel.EXTREMELY;
        } else {
            return null;
        }
    }

    private Integer calculateCaloriesFromGoal (Bmr bmr, String goal) {
        if (goal.equals("loseWeightRadioButton")) {
            return  (bmr.getHeight() * 6) + (bmr.getWeight() * 10) - (bmr.getAge() * 5) - 500;
        } else if (goal.equals("gainWeightRadioButton")) {
            return  (bmr.getHeight() * 6) + (bmr.getWeight() * 10) - (bmr.getAge() * 5) + 500;
        } else if (goal.equals("maintainWeightRadioButton")) {
            return (bmr.getHeight() * 6) + (bmr.getWeight() * 10) - (bmr.getAge() * 5);
        } else {
            return 0;
        }
    }

    private Double calculateCaloriesFromActivityLevel(Integer calories, ActivityLevel activityLevel) {
        switch(activityLevel) {
            case SEDENTARY:
                return calories * 1.2;
            case LIGHTLY:
                return calories * 1.375;
            case MODERATELY:
                return calories * 1.55;
            case VERY:
                return calories * 1.725;
            case EXTREMELY:
                return calories * 1.9;
        }
        return 0.0;
    }

}
