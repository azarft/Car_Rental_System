package com.example.car_rental_system_final;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.List;

public class MainPageController {

    @FXML
    private ImageView Logo;

    @FXML
    private Slider PriceSlider;

    @FXML
    private Label Price;

    @FXML
    private TableView<Car> tableView;

    @FXML
    private TableColumn<Car, String> brandColumn;

    @FXML
    private TableColumn<Car, String> typeColumn;

    @FXML
    private TableColumn<Car, Double> priceColumn;

    private CarDB carDB;



    @FXML
    public void initialize() {
        // Set the image programmatically
        Image image = new Image(getClass().getResource("/images/logo.png").toExternalForm());
        Logo.setImage(image);


        // Set the minimum and maximum values for the PriceSlider
        PriceSlider.setMin(0);
        PriceSlider.setMax(400);


        PriceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Update the Label text with the current value of the slider
                Price.setText("$" + String.format("%.2f", newValue.doubleValue()));
            }
        });

        // Set up TableView columns
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPricePerDay()).asObject());

        // Initialize CarDB
        carDB = new CarDB();

        // Fetch cars from the database and populate the TableView
        fetchCarsFromDatabase();

    }

    private void fetchCarsFromDatabase() {
        try {
            // Fetch all cars
            List<Car> carsFromDB = carDB.getAllCars();

            // Add fetched cars to the TableView
            ObservableList<Car> carObservableList = FXCollections.observableArrayList(carsFromDB);
            tableView.setItems(carObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
