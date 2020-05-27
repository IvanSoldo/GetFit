package sample.service;

import sample.models.Calories;
import sample.models.Macros;

public class MacrosServiceImpl implements MacrosService {

    @Override
    public Macros caloriesToMacros(Calories calories) {
        Macros macros = new Macros();

        double fats = (calories.getCalories() * 0.35) / 9;
        double protein = (calories.getCalories() * 0.30) / 4;
        double carbs = (calories.getCalories() * 0.35) / 4;

        macros.setCarbs((int) carbs);
        macros.setProteins((int) protein);
        macros.setFats((int) fats);

        return macros;
    }

    @Override
    public Calories macrosToCalories(Macros macros) {
        Calories calories = new Calories();

        int proteins = macros.getProteins() * 4;
        int carbs = macros.getCarbs() * 4;
        int fats = macros.getFats() * 9;
        int totalCalories = proteins + carbs + fats;

        calories.setCalories(totalCalories);
        return calories;
    }
}
