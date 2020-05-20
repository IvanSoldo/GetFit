package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.models.Food;
import sample.repositories.FoodRepository;
import sample.repositories.FoodRepositoryImpl;
import sample.service.FoodService;
import sample.service.FoodServiceImpl;

import java.io.IOException;


public class FoodIntakeController {

    FoodRepository foodRepository = new FoodRepositoryImpl();
    FoodService foodService = new FoodServiceImpl();
    Food food = new Food();

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
    private TableColumn<Food, Double> columnServingSize;

    @FXML
    private TextField filterField;

    @FXML
    private Label caloriesLabel;

    @FXML
    private Label carbsLabel;

    @FXML
    private Label proteinLabel;

    @FXML
    private Label fatsLabel;

    @FXML
    private TextField servingsField;


    @FXML
    void calculateFoodButtonClick(ActionEvent event) {
        Double servings = Double.valueOf(servingsField.getText());
        food = foodService.calculateFoodByServingSize(food,servings);
        caloriesLabel.setText(String.valueOf(food.getCalories()));
        carbsLabel.setText(String.valueOf(food.getCarbs()));
        proteinLabel.setText(String.valueOf(food.getProteins()));
        fatsLabel.setText(String.valueOf(food.getFats()));
    }

    @FXML
    void addFoodButtonClick(ActionEvent event) {


    }

    @FXML
    void goBackButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/homeView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @FXML
    private void initialize() {

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        columnProteins.setCellValueFactory(new PropertyValueFactory<>("proteins"));
        columnCarbs.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        columnFats.setCellValueFactory(new PropertyValueFactory<>("fats"));
        columnServingSize.setCellValueFactory(new PropertyValueFactory<>("servingSize"));
        foodTable.setPlaceholder(new Label("Food not found"));

        foodRepository.getFoodsFromDb();
        ObservableList<Food> observableList = FXCollections.observableArrayList(foodRepository.getFoodsFromDb());

        FilteredList<Food> filteredList = new FilteredList<>(observableList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(food -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (food.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Food> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(foodTable.comparatorProperty());
        foodTable.setItems(sortedData);

        foodTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                int index = foodTable.getSelectionModel().getSelectedIndex();
                food = foodTable.getItems().get(index);
            }
        });


    }


}