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
import javafx.scene.input.MouseEvent;

import java.net.URL;
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

    @FXML
    private Label CaType;
    @FXML
    private Label CarBrand;
    @FXML
    private Label CarColor;
    @FXML
    private Label CarCapacity;
    @FXML
    private ImageView CarImage;
    @FXML
    private Label CarVolume;
    @FXML
    private Label CarPrice;
    @FXML
    private Button Rent;

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

    @FXML
    private void onSelectCar(MouseEvent event) {
        // Get the selected car from the TableView
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();

        if (selectedCar != null) {
            // Update labels with selected car information
            CaType.setText(selectedCar.getType());
            CarBrand.setText(selectedCar.getBrand());
            CarColor.setText(selectedCar.getColor());
            CarCapacity.setText(Integer.toString(selectedCar.getCapacity()));
            CarVolume.setText(String.valueOf(selectedCar.getVolume() + "L"));
            CarPrice.setText(String.valueOf(selectedCar.getPricePerDay() + "$ / day"));

            // Add space after ')' in the image file name
            String imageNameWithSpace = selectedCar.getImagePath().replace(")", ") ");

            // Load and set the image for the selected car
            String imagePath = "/images/" + imageNameWithSpace;
            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl != null) {
                Image carImage = new Image(imageUrl.toExternalForm());
                CarImage.setImage(carImage);
            } else {
                // Handle case where image resource is not found
                System.out.println("Image resource not found: " + imagePath);
            }
        }
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
