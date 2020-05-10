package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.service.DatabaseConnection;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginClick(ActionEvent event) throws Exception{

        DatabaseConnection.getData();

        if(usernameField.getText().equals("ivan") && passwordField.getText().equals("soldo")){
            Parent calculatorViewParent = FXMLLoader.load(getClass().getResource("/views/homeView.fxml"));
            Scene calculatorViewScene = new Scene(calculatorViewParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(calculatorViewScene);
            window.show();
        }


    }

}