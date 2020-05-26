package sample.service;

import sample.models.Account;

public interface AccountService {

    void createAccount(Account account, String username, String password,String confirmPassword);

    boolean checkDate(Account account,  String lastLoggedIn);

}
