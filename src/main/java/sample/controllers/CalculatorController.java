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
import sample.repositories.AccountRepository;
import sample.repositories.AccountRepositoryImpl;
import sample.service.*;
import sample.utilities.ApplicationState;

public class CalculatorController {

    private AccountRepository accountRepository = new AccountRepositoryImpl();
    private ApplicationState applicationState = new ApplicationState();
    private BmrService bmrService = new BmrServiceImpl();
    private CaloriesService caloriesService = new CaloriesServiceImpl();
    private MacrosService macrosService = new MacrosServiceImpl();
    private ObservableList<String> activityLevelList = FXCollections.observableArrayList("Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Extremely Active");
    private ToggleGroup radioGroup = new ToggleGroup();
    private Account account = new Account();

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
    void goBackClick(ActionEvent event) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/views/homeView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
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
        textArea.setVisible(false);
        hideText(true);
        RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
        String radioButton = selectedRadioButton.getId();
        Bmr bmr = bmrService.addBmr(Integer.parseInt(heightField.getText()), Integer.parseInt(weightField.getText()), Integer.parseInt(ageField.getText()));
        Calories calories;
        Macros macros;
        ActivityLevel activityLevel = caloriesService.getActivityLevel(choiceBox.getValue().toString());

        calories = caloriesService.bmrToCalories(bmr,radioButton,activityLevel);
        macros = macrosService.caloriesToMacros(calories);
        account.setCalories(calories);
        account.setMacros(macros);


        caloriesLabel.setText(String.valueOf(calories.getCalories()));
        proteinLabel.setText(String.valueOf(macros.getProteins()));
        carbsLabel.setText(String.valueOf(macros.getCarbs()));
        fatsLabel.setText(String.valueOf(macros.getFats()));

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
        accountRepository.saveCalories(applicationState.getAccount());
        accountRepository.saveMacros(applicationState.getAccount());
    }


}
