package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.utilities.ApplicationState;

public class HomeController {
    ApplicationState applicationState = new ApplicationState();


    @FXML
    private Label usernameLabel;

    @FXML
    private Label caloriesLabel;

    @FXML
    private Label macrosGoalLabel;

    @FXML
    private void initialize() {
        usernameLabel.setText(applicationState.getAccount().getUsername());
        caloriesLabel.setText(String.valueOf(applicationState.getAccount().getCalories().getCalories()));
        String proteins = String.valueOf(applicationState.getAccount().getMacros().getProteins());
        String carbs = String.valueOf(applicationState.getAccount().getMacros().getCarbs());
        String fats = String.valueOf(applicationState.getAccount().getMacros().getFats());
        macrosGoalLabel.setText("P:" + proteins + " C:" + carbs + " F:" + fats);

    }

    @FXML
    void goToCalculatorClick(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/calculatorView.fxml"));
        Scene calculatorViewScene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(calculatorViewScene);
        window.show();
    }

    @FXML
    void goToCalAndMacLog(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/foodIntakeView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @FXML
    void goToWorkoutLog(ActionEvent event) {

    }

}