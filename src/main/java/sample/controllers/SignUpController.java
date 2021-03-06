package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Account;
import sample.models.RemainingCalories;
import sample.repositories.AccountRepository;
import sample.repositories.AccountRepositoryImpl;
import sample.repositories.RemainingCaloriesRepository;
import sample.repositories.RemainingCaloriesRepositoryImpl;
import sample.service.AccountService;
import sample.service.AccountServiceImpl;
import sample.utilities.ApplicationState;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignUpController {

    AccountService accountService = new AccountServiceImpl();
    AccountRepository accountRepository = new AccountRepositoryImpl();
    RemainingCaloriesRepository remainingCaloriesRepository = new RemainingCaloriesRepositoryImpl();
    ApplicationState applicationState = new ApplicationState();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;


    @FXML
    void createNewAccButton(ActionEvent event) throws IOException, SQLException {
        RemainingCalories remainingCalories = new RemainingCalories();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        Account account = new Account();
        accountService.createAccount(account, username, password, confirmPassword);

        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Leave no fields empty.");
            alert.showAndWait();
        } else if (usernameField.getText().contains(" ") || passwordField.getText().contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Username and password can not contain whitespace.");
            alert.showAndWait();
        } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Password does not match. Please input the same password in both fields.");
            alert.showAndWait();
        } else if (accountRepository.checkIfUsernameIsTaken(account)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Username already taken. Please write different username.");
            alert.showAndWait();
        } else {
            account.setDateOfLogIn(String.valueOf(LocalDate.now()));
            accountRepository.signUp(account);
            applicationState.setAccount(account);
            applicationState.setRemainingCalories(remainingCalories);
            accountRepository.logIn(account);
            accountRepository.setLastLoginDate(account);
            remainingCalories.setAccountID(account.getId());
            remainingCaloriesRepository.createRemainingCaloriesTable(remainingCalories);
            Parent calculatorViewParent = FXMLLoader.load(getClass().getResource("/views/homeView.fxml"));
            Scene calculatorViewScene = new Scene(calculatorViewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(calculatorViewScene);
            window.show();
        }



    }


    @FXML
    void loginButton(ActionEvent event) throws IOException {
        Parent calculatorViewParent = FXMLLoader.load(getClass().getResource("/views/loginView.fxml"));
        Scene calculatorViewScene = new Scene(calculatorViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(calculatorViewScene);
        window.show();
    }

}
