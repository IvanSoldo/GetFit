package sample.repositories;

import sample.models.Account;
import sample.models.RemainingCalories;

public interface RemainingCaloriesRepository {

    void updateRemainingCalories(RemainingCalories remainingCalories);

    void createRemainingCaloriesTable(RemainingCalories remainingCalories);

    void getRemainingCaloriesFromDB(RemainingCalories remainingCalories);

    void resetRemainingCalories(Account account);

}
