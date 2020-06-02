package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Food;
import sample.repositories.FoodRepository;
import sample.repositories.FoodRepositoryImpl;
import sample.utilities.ApplicationState;

import java.io.IOException;

public class CreateFoodController {

    FoodRepository foodRepository = new FoodRepositoryImpl();
    ApplicationState applicationState = new ApplicationState();

    @FXML
    private TextField nameField;

    @FXML
    private TextField caloriesField;

    @FXML
    private TextField proteinsField;

    @FXML
    private TextField carbsField;

    @FXML
    private TextField fatsField;

    @FXML
    private TextField servingSizeField;


    @FXML
    void createNewFoodButtonClick(ActionEvent event) {

        nameField.setText(nameField.getText().replaceAll("\\s",""));
        caloriesField.setText(caloriesField.getText().replaceAll("\\s",""));
        proteinsField.setText(caloriesField.getText().replaceAll("\\s",""));
        carbsField.setText(carbsField.getText().replaceAll("\\s",""));
        fatsField.setText(fatsField.getText().replaceAll("\\s",""));
        servingSizeField.setText(servingSizeField.getText().replaceAll("\\s",""));

        if (nameField.getText().isBlank() || caloriesField.getText().isBlank() || proteinsField.getText().isBlank() || carbsField.getText().isBlank()
                || fatsField.getText().isBlank() || servingSizeField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Leave no fields empty.");
            alert.showAndWait();
        } else if (!isInteger(caloriesField) || (!isInteger(proteinsField)) || (!isInteger(carbsField)) || (!isInteger(fatsField)) || (!isInteger(servingSizeField))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please input numeric value.");
            alert.showAndWait();
        } else if (!isInputValid(caloriesField) || (!isInputValid(proteinsField)) || (!isInputValid(carbsField)) || (!isInputValid(fatsField)) || (!isInputValid(servingSizeField))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Value can not be in the negatives.");
            alert.showAndWait();
        } else {

            String name = nameField.getText();
            Integer calories = Integer.valueOf(caloriesField.getText());
            Integer proteins = Integer.valueOf(proteinsField.getText());
            Integer carbs = Integer.valueOf(carbsField.getText());
            Integer fats = Integer.valueOf(fatsField.getText());
            Double servingSize = Double.valueOf(servingSizeField.getText());
            Food food = new Food(name, calories, proteins, carbs, fats, servingSize);
            foodRepository.addFoodToDb(applicationState.getAccount(), food);
        }


    }

    @FXML
    void goBackButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/foodIntakeView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    private boolean isInteger(TextField textField) {
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isInputValid(TextField textField) {
        if (Integer.valueOf(textField.getText()) < 0) {
            return false;
        }
        return true;
    }

}
