package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class homeController {

    @FXML
    void goToCalculatorClick(ActionEvent event) throws Exception {
        Parent calculatorViewParent = FXMLLoader.load(getClass().getResource("/views/calculatorView.fxml"));
        Scene calculatorViewScene = new Scene(calculatorViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(calculatorViewScene);
        window.show();
    }

    @FXML
    void goToCalAndMacLog(ActionEvent event) throws Exception {

    }


    @FXML
    void goToWorkoutLog(ActionEvent event) {

    }

}