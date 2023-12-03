package com.example.car_rental_system_final;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import javafx.stage.Modality;

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

    @FXML
    private CheckBox TypeSport;
    @FXML
    private CheckBox TypeSUV;
    @FXML
    private CheckBox TypeMPV;
    @FXML
    private CheckBox TypeSedan;
    @FXML
    private CheckBox TypeCoupe;
    @FXML
    private CheckBox TypeHatchback;

    @FXML
    private CheckBox Capacity2Person;
    @FXML
    private CheckBox Capacity4Person;
    @FXML
    private CheckBox Capacity6Person;
    @FXML
    private CheckBox Capacity8PersonOrMore;

    @FXML
    private Button Filter;

    private CarDB carDB;
    private String filterText = "";

    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResource("/images/logo.png").toExternalForm());
        Logo.setImage(image);

        PriceSlider.setMin(100);
        PriceSlider.setMax(400);

        // Set the initial value to the maximum
        PriceSlider.setValue(PriceSlider.getMax());

        PriceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Price.setText("$" + String.format("%.2f", newValue.doubleValue()));
            }
        });

        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPricePerDay()).asObject());

        carDB = new CarDB();
        fetchCarsFromDatabase();

        // Automatically select the first item in the tableView
        tableView.getSelectionModel().selectFirst();
        // Show information for the selected car
        selectCarInTable();
    }

    @FXML
    public void onClickRentButton(){
        openRentPage();
    }
    @FXML
    public void onClickFilterButton() {
        filterCars();
    }

    @FXML
    private void onSelectCarMouse(MouseEvent event) {
        selectCarInTable();
    }
    @FXML
    private void onSelectCarKey(KeyEvent event) {
        selectCarInTable();
    }

    private void fetchCarsFromDatabase() {
        try {
            List<Car> carsFromDB = carDB.getAllCars();
            ObservableList<Car> carObservableList = FXCollections.observableArrayList(carsFromDB);
            tableView.setItems(carObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchFilteredCarsFromDatabase() {
        try {
            List<Car> filteredCarsFromDB = carDB.getFilteredCars(filterText);
            ObservableList<Car> filteredCarObservableList = FXCollections.observableArrayList(filteredCarsFromDB);
            tableView.setItems(filteredCarObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterCars(){
        int check = 0;
        filterText = "where 1=1 "; // Start with a true condition

        if (TypeCoupe.isSelected() || TypeHatchback.isSelected() || TypeMPV.isSelected() || TypeSedan.isSelected() || TypeSport.isSelected() || TypeSUV.isSelected()) {
            filterText += "and type in (";

            if (TypeCoupe.isSelected()) {
                filterText += "'Coupe',";
                check++;
            }

            if (TypeHatchback.isSelected()) {
                filterText += "'Hatchback',";
                check++;
            }

            if (TypeMPV.isSelected()) {
                filterText += "'MPV',";
                check++;
            }

            if (TypeSedan.isSelected()) {
                filterText += "'Sedan',";
                check++;
            }

            if (TypeSport.isSelected()) {
                filterText += "'Sport',";
                check++;
            }

            if (TypeSUV.isSelected()) {
                filterText += "'SUV',";
                check++;
            }

            // Remove the trailing comma if there are selected types
            if (check > 0) {
                filterText = filterText.substring(0, filterText.length() - 1);
            }

            filterText += ") ";
        }

        check = 0;
        if (Capacity2Person.isSelected() || Capacity4Person.isSelected() || Capacity6Person.isSelected() || Capacity8PersonOrMore.isSelected()) {
            filterText += "and capacity in (";

            if (Capacity2Person.isSelected()) {
                filterText += "2,";
                check++;
            }

            if (Capacity4Person.isSelected()) {
                filterText += "4,";
                check++;
            }

            if (Capacity6Person.isSelected()) {
                filterText += "6,";
                check++;
            }

            if (Capacity8PersonOrMore.isSelected()) {
                filterText += "8,";
                check++;
            }

            // Remove the trailing comma if there are selected capacities
            if (check > 0) {
                filterText = filterText.substring(0, filterText.length() - 1);
            }

            filterText += ") ";
        }

        double maxPrice = PriceSlider.getValue();
        filterText += "and price_per_day <= " + maxPrice + " ";

        // Fetch filtered cars from the database and populate the TableView
        fetchFilteredCarsFromDatabase();
    }

    private void selectCarInTable(){
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();

        if (selectedCar != null) {
            CaType.setText(selectedCar.getType());
            CarBrand.setText(selectedCar.getBrand());
            CarColor.setText(selectedCar.getColor());
            CarCapacity.setText(Integer.toString(selectedCar.getCapacity()));
            CarVolume.setText(String.valueOf(selectedCar.getVolume() + "L"));
            CarPrice.setText(String.valueOf(selectedCar.getPricePerDay() + "$ / day"));

            // Add space after ')' in the image file name
            String imageNameWithSpace = selectedCar.getImagePath();

            String imagePath = "/images/" + imageNameWithSpace;
            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl != null) {
                Image carImage = new Image(imageUrl.toExternalForm());
                CarImage.setImage(carImage);
            } else {
                System.out.println("Image resource not found: " + imagePath);
            }
        }
    }

    private Stage rentPageStage;

    private void openRentPage() {
        try {
            // Check if the rentPageStage is already open
            if (rentPageStage != null && rentPageStage.isShowing()) {
                // Close the existing rentPageStage
                rentPageStage.close();
            }

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Rent_page.fxml"));
            Parent root = loader.load();

            // Create a new stage
            rentPageStage = new Stage();

            // Set the scene with the new FXML content
            Scene scene = new Scene(root);
            rentPageStage.setScene(scene);

            // Set the controller for the new FXML file
            RentPageController rentPageController = loader.getController();

            // Set the new stage as a modal to block interactions with the main stage
            rentPageStage.initModality(Modality.WINDOW_MODAL);
            rentPageStage.initOwner(tableView.getScene().getWindow());  // Use tableView's scene as owner

            // Show the new stage without closing the old one
            rentPageStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
