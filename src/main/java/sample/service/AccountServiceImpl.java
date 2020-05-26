package sample.service;

import sample.models.Account;


public class AccountServiceImpl implements AccountService {

    @Override
    public void createAccount(Account account, String username, String password, String confirmPassword) {
        if (confirmPassword(password,confirmPassword)) {
            account.setUsername(username);
            account.setPassword(password);
        }
    }

    @Override
    public boolean checkDate(Account account,String lastLoggedIn) {
        if (account.getDateOfLogIn().equals(lastLoggedIn)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean confirmPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        } else {
            return false;
        }
    }
}
