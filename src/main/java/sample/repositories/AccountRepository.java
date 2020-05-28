package sample.repositories;

import sample.models.Account;

import java.io.IOException;
import java.sql.SQLException;

public interface AccountRepository {

    void signUp(Account account);

    void logIn(Account account) throws IOException, SQLException;

    boolean checkUsernameAndPass(Account account);

    boolean checkIfUsernameIsTaken(Account account);

    void saveCalories(Account account);

    void saveMacros(Account account);

    void setLastLoginDate(Account account);

    String getLastLoginDate(Account account);


}
