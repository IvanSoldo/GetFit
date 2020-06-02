package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.models.*;
import sample.repositories.*;
import sample.service.*;
import sample.utilities.ApplicationState;
import java.util.ArrayList;

public class CalculatorController {

    private AccountRepository accountRepository = new AccountRepositoryImpl();
    private ApplicationState applicationState = new ApplicationState();
    private RemainingCaloriesRepository remainingCaloriesRepository = new RemainingCaloriesRepositoryImpl();
    private BmrService bmrService = new BmrServiceImpl();
    private CaloriesService caloriesService = new CaloriesServiceImpl();
    private MacrosService macrosService = new MacrosServiceImpl();
    private ObservableList<String> activityLevelList = FXCollections.observableArrayList("Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Extremely Active");
    private ToggleGroup radioGroup = new ToggleGroup();
    private Account account = new Account();
    private FoodRepository foodRepository = new FoodRepositoryImpl();
    private ArrayList<Food> foods = foodRepository.getTotalFoodsFromDb();
    private FoodService foodService = new FoodServiceImpl();

    @FXML
    private TextField ageField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField heightField;

    @FXML
    private RadioButton loseWeightRadioButton;

    @FXML
    private RadioButton gainWeightRadioButton;

    @FXML
    private RadioButton maintainWeightRadioButton;

    @FXML
    private Label caloriesLabel;

    @FXML
    private Label proteinLabel;

    @FXML
    private Label carbsLabel;

    @FXML
    private Label fatsLabel;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private Label caloriesAndMacrosText;

    @FXML
    private Label caloriesText;

    @FXML
    private Label proteinText;

    @FXML
    private Label carbohydrateText;

    @FXML
    private Label fatText;

    @FXML
    private TextArea textArea;

    @FXML
    private Button saveButton;


    @FXML
    void goBackClick(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/views/homeView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void initialize() {
        choiceBox.setItems(activityLevelList);
        loseWeightRadioButton.setToggleGroup(radioGroup);
        gainWeightRadioButton.setToggleGroup(radioGroup);
        maintainWeightRadioButton.setToggleGroup(radioGroup);
        textArea.setEditable(false);
        hideText(false);

    }


    @FXML
    void calculateClick() {

        ageField.setText(ageField.getText().replaceAll("\\s",""));
        heightField.setText(heightField.getText().replaceAll("\\s",""));
        weightField.setText(weightField.getText().replaceAll("\\s",""));


        if (ageField.getText().isBlank() || heightField.getText().isBlank() || weightField.getText().isBlank() || choiceBox.getValue() == null || radioGroup.getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Leave no fields empty.");
            alert.showAndWait();
        } else if (!isInteger(ageField) || (!isInteger(heightField)) || (!isInteger(weightField))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please input numeric value.");
            alert.showAndWait();
        } else if (!isAgeValid(ageField)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Age must be between 13 and 80.");
            alert.showAndWait();
        } else if(!isWeightValid(weightField)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Enter a valid weight.");
            alert.showAndWait();
        } else if (!isHeightValid(heightField)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Enter a valid height.");
            alert.showAndWait();
        }
        else {
            textArea.setVisible(false);
            hideText(true);
            RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
            String radioButton = selectedRadioButton.getId();
            Bmr bmr = bmrService.addBmr(Integer.parseInt(heightField.getText()), Integer.parseInt(weightField.getText()), Integer.parseInt(ageField.getText()));
            Calories calories;
            Macros macros;
            ActivityLevel activityLevel = caloriesService.getActivityLevel(choiceBox.getValue().toString());

            calories = caloriesService.bmrToCalories(bmr, radioButton, activityLevel);
            macros = macrosService.caloriesToMacros(calories);
            account.setCalories(calories);
            account.setMacros(macros);


            caloriesLabel.setText(String.valueOf(calories.getCalories()));
            proteinLabel.setText(String.valueOf(macros.getProteins()));
            carbsLabel.setText(String.valueOf(macros.getCarbs()));
            fatsLabel.setText(String.valueOf(macros.getFats()));
        }


    }

    private void hideText(boolean hideOrShow) {
        caloriesAndMacrosText.setVisible(hideOrShow);
        caloriesText.setVisible(hideOrShow);
        proteinText.setVisible(hideOrShow);
        carbohydrateText.setVisible(hideOrShow);
        fatText.setVisible(hideOrShow);
        saveButton.setVisible(hideOrShow);
    }

    @FXML
    void saveButtonClick(ActionEvent event) {

        applicationState.getAccount().setCalories(account.getCalories());
        applicationState.getAccount().setMacros(account.getMacros());

        int remCalories = applicationState.getAccount().getCalories().getCalories() - foodService.calculateTotalFoodCalories(foods);
        int remProteins = applicationState.getAccount().getMacros().getProteins() - foodService.calculateTotalFoodProteins(foods);
        int remCarbs = applicationState.getAccount().getMacros().getCarbs() - foodService.calculateTotalFoodCarbs(foods);
        int remFats = applicationState.getAccount().getMacros().getFats() - foodService.calculateTotalFoodFats(foods);

        applicationState.getRemainingCalories().setCalories(remCalories);
        applicationState.getRemainingCalories().setProteins(remProteins);
        applicationState.getRemainingCalories().setCarbs(remCarbs);
        applicationState.getRemainingCalories().setFats(remFats);

        accountRepository.saveCalories(applicationState.getAccount());
        accountRepository.saveMacros(applicationState.getAccount());
        remainingCaloriesRepository.updateRemainingCalories(applicationState.getRemainingCalories());
    }

    private boolean isInteger(TextField textField) {
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isAgeValid (TextField textField) {
        if (Integer.valueOf(textField.getText()) >= 13 && Integer.valueOf(textField.getText()) <= 80) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isWeightValid (TextField textField) {
        if (Integer.valueOf(textField.getText()) >= 40 && Integer.valueOf(textField.getText()) <= 300) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isHeightValid (TextField textField) {
        if (Integer.valueOf(textField.getText()) >= 50 && Integer.valueOf(textField.getText()) <= 250) {
            return true;
        } else {
            return false;
        }
    }

}
