package sample.repositories;

import sample.models.Account;

import java.io.IOException;
import java.sql.SQLException;

public interface AccountRepository {

    void signUp(Account account);

    String logIn(Account account) throws IOException, SQLException;

    void saveCalories(Account account);

    void saveMacros(Account account);

}
