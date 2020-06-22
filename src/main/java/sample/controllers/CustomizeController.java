package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Calories;
import sample.models.Food;
import sample.models.Macros;
import sample.repositories.*;
import sample.service.FoodService;
import sample.service.FoodServiceImpl;
import sample.service.MacrosService;
import sample.service.MacrosServiceImpl;
import sample.utilities.ApplicationState;

import java.io.IOException;
import java.util.ArrayList;

public class CustomizeController {

    private MacrosService macrosService = new MacrosServiceImpl();
    private AccountRepository accountRepository = new AccountRepositoryImpl();
    private RemainingCaloriesRepository remainingCaloriesRepository = new RemainingCaloriesRepositoryImpl();
    private Calories calories = new Calories();
    private Macros macros = new Macros();
    private FoodRepository foodRepository = new FoodRepositoryImpl();
    private ArrayList<Food> foods = foodRepository.getTotalFoodsFromDb();
    private FoodService foodService = new FoodServiceImpl();

    @FXML
    private TextField proteinField;

    @FXML
    private TextField carbsField;

    @FXML
    private TextField fatsField;

    @FXML
    private Label totalCalField;

    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
        saveButton.setDisable(true);
    }


    @FXML
    void calculateButtonClick(ActionEvent event) {

        proteinField.setText(proteinField.getText().replaceAll("\\s",""));
        carbsField.setText(carbsField.getText().replaceAll("\\s",""));
        fatsField.setText(fatsField.getText().replaceAll("\\s",""));

        if (proteinField.getText().isEmpty()  || carbsField.getText().isEmpty() || fatsField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Leave no fields empty.");
            alert.showAndWait();
        } else if (!isInteger(proteinField) || (!isInteger(carbsField)) || (!isInteger(fatsField))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please input numeric value.");
            alert.showAndWait();
        } else if (!isInputValid(proteinField) || (!isInputValid(carbsField)) || (!isInputValid(fatsField))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Values can not be in the negatives.");
            alert.showAndWait();
        } else {
            int proteins = Integer.valueOf(proteinField.getText());
            int carbs = Integer.valueOf(carbsField.getText());
            int fats = Integer.valueOf(fatsField.getText());

            macros.setProteins(proteins);
            macros.setCarbs(carbs);
            macros.setFats(fats);

            calories = macrosService.macrosToCalories(macros);
            totalCalField.setText(String.valueOf(calories.getCalories()));
            saveButton.setDisable(false);
        }

    }

    @FXML
    void goBackButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HomeView.fxml"));
        Parent root = loader.load();
        Scene homeView = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(homeView);
        window.show();
    }

    @FXML
    void saveButtonClick(ActionEvent event) {

        ApplicationState.getAccount().setCalories(calories);
        ApplicationState.getAccount().setMacros(macros);

        int remCalories = ApplicationState.getAccount().getCalories().getCalories() - foodService.calculateTotalFoodCalories(foods);
        int remProteins = ApplicationState.getAccount().getMacros().getProteins() - foodService.calculateTotalFoodProteins(foods);
        int remCarbs = ApplicationState.getAccount().getMacros().getCarbs() - foodService.calculateTotalFoodCarbs(foods);
        int remFats = ApplicationState.getAccount().getMacros().getFats() - foodService.calculateTotalFoodFats(foods);

        ApplicationState.getRemainingCalories().setCalories(remCalories);
        ApplicationState.getRemainingCalories().setProteins(remProteins);
        ApplicationState.getRemainingCalories().setCarbs(remCarbs);
        ApplicationState.getRemainingCalories().setFats(remFats);


        accountRepository.saveCalories(ApplicationState.getAccount());
        accountRepository.saveMacros(ApplicationState.getAccount());
        remainingCaloriesRepository.updateRemainingCalories(ApplicationState.getRemainingCalories());


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
        if (Integer.valueOf(textField.getText()) >= 0) {
            return true;
        } else {
            return false;
        }
    }

}
