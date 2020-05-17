package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Account;
import sample.repositories.AccountRepository;
import sample.repositories.AccountRepositoryImpl;

import java.io.IOException;


public class LoginController {


    AccountRepository accountRepository = new AccountRepositoryImpl();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label noUserFoundLabel;

    @FXML
    private void initialize() {
        noUserFoundLabel.setVisible(false);
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
        account.setUsername(usernameField.getText());
        account.setPassword(passwordField.getText());
        accountRepository.logIn(account);

        String checker = accountRepository.logIn(account);

        if (checker.equals("Success")) {
            Parent calculatorViewParent = FXMLLoader.load(getClass().getResource("/views/HomeView.fxml"));
            Scene calculatorViewScene = new Scene(calculatorViewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(calculatorViewScene);
            window.show();
        } else {
            noUserFoundLabel.setVisible(true);
        }


    }

}