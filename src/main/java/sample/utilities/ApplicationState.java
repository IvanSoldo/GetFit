package sample.utilities;

import sample.models.Account;
import sample.models.RemainingCalories;

public class ApplicationState {

    public static Account account;
    public static RemainingCalories remainingCalories;

    public static RemainingCalories getRemainingCalories() {
        return remainingCalories;
    }

    public static void setRemainingCalories(RemainingCalories remainingCalories) {
        ApplicationState.remainingCalories = remainingCalories;
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        ApplicationState.account = account;
    }
}
