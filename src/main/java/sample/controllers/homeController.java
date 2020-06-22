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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.models.Food;
import sample.repositories.FoodRepository;
import sample.repositories.FoodRepositoryImpl;
import sample.service.FoodService;
import sample.service.FoodServiceImpl;
import sample.utilities.ApplicationState;

import java.io.IOException;
import java.util.ArrayList;

public class HomeController {
    private ApplicationState applicationState = new ApplicationState();
    private FoodRepository foodRepository = new FoodRepositoryImpl();
    private FoodService foodService = new FoodServiceImpl();
    private ArrayList<Food> foods = new ArrayList<>();


    @FXML
    private Label usernameLabel;

    @FXML
    private Label caloriesLabel;

    @FXML
    private Label macrosGoalLabel;

    @FXML
    private Label calCalcLabel;

    @FXML
    private Label foodLabel;

    @FXML
    private Label remainingLabel;

    @FXML
    private Label macrosRemLabel;

    @FXML
    private TableView<Food> foodTable;

    @FXML
    private TableColumn<Food, String> columnName;

    @FXML
    private TableColumn<Food, Integer> columnCalories;

    @FXML
    private TableColumn<Food, Integer> columnProteins;

    @FXML
    private TableColumn<Food, Integer> columnCarbs;

    @FXML
    private TableColumn<Food, Integer> columnFats;

    @FXML
    private TableColumn<Food, Integer> columnAmount;

    @FXML
    private void initialize() {

        foods = foodRepository.getTotalFoodsFromDb();
        usernameLabel.setText(applicationState.getAccount().getUsername());
        caloriesLabel.setText(String.valueOf(applicationState.getAccount().getCalories().getCalories()));
        String proteins = String.valueOf(applicationState.getAccount().getMacros().getProteins());
        String carbs = String.valueOf(applicationState.getAccount().getMacros().getCarbs());
        String fats = String.valueOf(applicationState.getAccount().getMacros().getFats());
        macrosGoalLabel.setText("P:" + proteins + " C:" + carbs + " F:" + fats);

        calCalcLabel.setText(String.valueOf(applicationState.getAccount().getCalories().getCalories()));
        remainingLabel.setText(String.valueOf(applicationState.getRemainingCalories().getCalories()));

        String remProteins = String.valueOf(applicationState.getRemainingCalories().getProteins());
        String remCarbs = String.valueOf(applicationState.getRemainingCalories().getCarbs());
        String remFats = String.valueOf(applicationState.getRemainingCalories().getFats());

        macrosRemLabel.setText("P:" + remProteins + " C:" + remCarbs + " F:" + remFats);
        int foodsCal = foodService.calculateTotalFoodCalories(foods);
        foodLabel.setText(String.valueOf(foodsCal));

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        columnProteins.setCellValueFactory(new PropertyValueFactory<>("proteins"));
        columnCarbs.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        columnFats.setCellValueFactory(new PropertyValueFactory<>("fats"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        foodTable.setPlaceholder(new Label("No food added today"));

        ObservableList<Food> observableList = FXCollections.observableArrayList(foods);
        foodTable.setItems(observableList);

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

        if (applicationState.getAccount().getCalories().getCalories() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please calculate your daily goals first, or input desired macros manually.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/views/foodIntakeView.fxml"));
            Scene scene = new Scene(root);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }

    @FXML
    void customizeCaloriesAndMacrosClick(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/customizeView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void logOutButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/loginView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}