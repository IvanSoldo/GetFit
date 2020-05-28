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
import sample.repositories.*;
import sample.service.AccountService;
import sample.service.AccountServiceImpl;
import sample.utilities.ApplicationState;

import java.io.IOException;
import java.time.LocalDate;


public class LoginController {


    private AccountRepository accountRepository = new AccountRepositoryImpl();
    private AccountService accountService = new AccountServiceImpl();
    private RemainingCaloriesRepository remainingCaloriesRepository = new RemainingCaloriesRepositoryImpl();
    private FoodRepository foodRepository = new FoodRepositoryImpl();


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private void initialize() {

    }

    @FXML
    void signUpButtonClick(ActionEvent event) throws IOException {
        Parent calculatorViewParent = FXMLLoader.load(getClass().getResource("/views/SignUpView.fxml"));
        Scene calculatorViewScene = new Scene(calculatorViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(calculatorViewScene);
        window.show();
    }

    @FXML
    public void loginClick(ActionEvent event) throws Exception {

        Account account = new Account();
        RemainingCalories remainingCalories = new RemainingCalories();
        account.setUsername(usernameField.getText());
        account.setPassword(passwordField.getText());

        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Leave no fields empty.");
            alert.showAndWait();
        } else if (!accountRepository.checkUsernameAndPass(account)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Username and/or password is incorrect.");
            alert.showAndWait();
        } else {
            accountRepository.logIn(account);
            ApplicationState.setAccount(account);
            remainingCalories.setAccountID(account.getId());

            String lastLoggedIn = accountRepository.getLastLoginDate(account);
            account.setDateOfLogIn(String.valueOf(LocalDate.now()));

            if (accountService.checkDate(account, lastLoggedIn) == true) {
                remainingCaloriesRepository.getRemainingCaloriesFromDB(remainingCalories);
            } else {
                remainingCaloriesRepository.resetRemainingCalories(account);
                remainingCaloriesRepository.getRemainingCaloriesFromDB(remainingCalories);
                foodRepository.resetTotalFoodsForUser(ApplicationState.getAccount());
                // ako nisam ulogiran danas, resetiraj podatke sa podatcima iz accounta - stavi te podatke u remaining cal
            }

            accountRepository.setLastLoginDate(ApplicationState.getAccount());
            ApplicationState.setRemainingCalories(remainingCalories);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HomeView.fxml"));
            Parent root = loader.load();
            Scene homeView = new Scene(root);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeView);
            window.show();
        }

    }


}

