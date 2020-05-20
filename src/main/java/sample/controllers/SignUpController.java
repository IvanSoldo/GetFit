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
import sample.service.AccountService;
import sample.service.AccountServiceImpl;
import sample.utilities.ApplicationState;

import java.io.IOException;

public class SignUpController {

    AccountService accountService = new AccountServiceImpl();
    AccountRepository accountRepository = new AccountRepositoryImpl();
    ApplicationState applicationState = new ApplicationState();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label passNotMatchingLabel;


    @FXML
    private void initialize() {
        passNotMatchingLabel.setVisible(false);
    }

    @FXML
    void createNewAccButton(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        Account account = new Account();
        accountService.createAccount(account,username, password,confirmPassword);
        if (!passCheck(password,confirmPassword)) {
            passNotMatchingLabel.setVisible(true);
        } else {
            accountRepository.signUp(account);
            applicationState.setAccount(account);
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

    private boolean passCheck (String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        } else {
            return false;
        }
    }

}
