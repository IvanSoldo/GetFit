package sample.utilities;

import sample.models.Account;

public class ApplicationState {

    public static Account account;

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        ApplicationState.account = account;
    }
}
