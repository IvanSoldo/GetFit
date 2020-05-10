package sample.service;

import sample.models.ActivityLevel;
import sample.models.Bmr;
import sample.models.Calories;

public interface CaloriesService {

    Calories bmrToCalories(Bmr bmr, String goal, ActivityLevel activityLevel);

    ActivityLevel getActivityLevel(String level);
}
