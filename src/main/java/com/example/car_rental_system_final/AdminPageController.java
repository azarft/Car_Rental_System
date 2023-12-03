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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import javafx.stage.Modality;

public class AdminPageController {
    private Stage primaryStage;

    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

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
    private TextField CaType;
    @FXML
    private TextField CarBrand;
    @FXML
    private TextField CarColor;
    @FXML
    private TextField CarCapacity;
    @FXML
    private ImageView CarImage;
    @FXML
    private TextField CarVolume;
    @FXML
    private TextField CarPrice;

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
    private Button Delete;
    @FXML
    private Button Save;
    @FXML
    private Button AddImage;
    @FXML
    private Label imgName;



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
    public void onClickFilterButton() {
        filterCars();
    }

    @FXML
    private void onSelectCarMouse(MouseEvent event) {
        selectCarInTable();
    }

    @FXML
    private void onClickAddImageButton() {
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();

        if (selectedCar == null) {
            // No car selected, allow the user to import an image
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );

            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                // Copy the selected file to the resources/images directory
                String imageName = selectedFile.getName();
                String destinationPath = "src/main/resources/images/" + imageName;

                try {
                    Files.copy(selectedFile.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);

                    // Update the image name label
                    imgName.setText(imageName);

                    // Display the new image in the ImageView
                    Image carImage = new Image(selectedFile.toURI().toString());
                    CarImage.setImage(carImage);

                    // Inform the user that the image has been added
                    System.out.println("Image added successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately (e.g., show an error message)
                }
            }
        } else {
            // A car is selected, do nothing
            System.out.println("Cannot add image when a car is selected.");
        }
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

    @FXML
    private void onClickSaveButton() {
        // Get the currently selected car
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();

        if (selectedCar != null) {
            // Update the selected car with the values from text fields
            updateCarValues(selectedCar);

            // Update the database with the modified car
            updateCarInDatabase(selectedCar);

            // Refresh the table view to reflect the changes
            fetchCarsFromDatabase();
        } else {
            // Handle the case where no item is selected
            // For example, show an alert or display a message to the user
            if (    !CaType.getText().isEmpty() &&
                    !CarBrand.getText().isEmpty() &&
                    !CarColor.getText().isEmpty() &&
                    !CarCapacity.getText().isEmpty() &&
                    !CarVolume.getText().isEmpty() &&
                    !CarPrice.getText().isEmpty() &&
                    !imgName.getText().isEmpty())
            {
                Car car = new Car();
                car.setType(CaType.getText());
                car.setBrand(CarBrand.getText());
                car.setColor(CarColor.getText());
                car.setCapacity(Integer.parseInt(CarCapacity.getText()));
                car.setVolume(Integer.parseInt(CarVolume.getText()));
                car.setPricePerDay(Double.parseDouble(CarPrice.getText()));
                car.setImagePath(imgName.getText());
                try {
                    carDB.insertCar(car);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                clearCarDetails();
            }
        }
    }

    @FXML
    private void onClickDeleteButton() {
        // Get the currently selected car
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();

        if (selectedCar != null) {
            // Delete the selected car from the database
            deleteCarFromDatabase(selectedCar);

            // Refresh the table view to reflect the changes
            fetchCarsFromDatabase();

            // Clear the text fields and image after deletion
            clearCarDetails();
        } else {
            // Handle the case where no item is selected
            // For example, show an alert or display a message to the user
            clearCarDetails();
        }
    }

    private void clearCarDetails() {
        // Clear all text fields and the image
        CaType.clear();
        CarBrand.clear();
        CarColor.clear();
        CarCapacity.clear();
        CarVolume.clear();
        CarPrice.clear();
        imgName.setText("");
        CarImage.setImage(null);
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
    private Car currentlySelectedCar;

    private void selectCarInTable() {
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();

        if (selectedCar != null) {
            // Check if the selected car is the same as the currently selected one
            if (selectedCar.equals(currentlySelectedCar)) {
                // Clear the selection
                tableView.getSelectionModel().clearSelection();
                currentlySelectedCar = null;

                // Clear all text fields and the image
                CaType.clear();
                CarBrand.clear();
                CarColor.clear();
                CarCapacity.clear();
                CarVolume.clear();
                CarPrice.clear();
                imgName.setText("");
                CarImage.setImage(null);
            } else {
                // Set the currentlySelectedCar to the selected car
                currentlySelectedCar = selectedCar;

                CaType.setText(selectedCar.getType());
                CarBrand.setText(selectedCar.getBrand());
                CarColor.setText(selectedCar.getColor());
                CarCapacity.setText(Integer.toString(selectedCar.getCapacity()));
                CarVolume.setText(String.valueOf(selectedCar.getVolume()));
                CarPrice.setText(String.valueOf(selectedCar.getPricePerDay()));
                imgName.setText(selectedCar.getImagePath());

                // Add space after ')' in the image file name
                String imageNameWithSpace = selectedCar.getImagePath().replace(")", ") ");

                // Construct the file path to the image
                String imagePath = "src/main/resources/images/" + imageNameWithSpace;

                // Load image directly from file
                File file = new File(imagePath);
                Image carImage = new Image(file.toURI().toString());
                CarImage.setImage(carImage);
            }
        } else {
            // Clear all text fields and the image when no item is selected
            CaType.clear();
            CarBrand.clear();
            CarColor.clear();
            CarCapacity.clear();
            CarVolume.clear();
            CarPrice.clear();
            imgName.setText("");
            CarImage.setImage(null);
            currentlySelectedCar = null;
        }
    }

    private void updateCarValues(Car car) {
        // Update the car object with values from text fields
        car.setType(CaType.getText());
        car.setBrand(CarBrand.getText());
        car.setColor(CarColor.getText());
        car.setCapacity(Integer.parseInt(CarCapacity.getText()));
        car.setVolume(Integer.parseInt(CarVolume.getText()));
        car.setPricePerDay(Double.parseDouble(CarPrice.getText()));
        car.setImagePath(imgName.getText());
    }

    private void updateCarInDatabase(Car car) {
        // Update the car in the database
        try {
            carDB.updateCar(car);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }

    private void deleteCarFromDatabase(Car car) {
        // Get the image name from the car
        String imageName = car.getImagePath();

        // Delete the car from the database
        try {
            carDB.deleteCar(car.getId());

            // Delete the associated image file
            deleteImageFile(imageName);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }

    private void deleteImageFile(String imageName) {
        // Construct the path to the image file
        String imagePath = "src/main/resources/images/" + imageName;

        // Create a File object representing the image file
        File imageFile = new File(imagePath);

        // Check if the file exists before attempting to delete it
        if (imageFile.exists()) {
            // Attempt to delete the image file
            boolean deleted = imageFile.delete();

            // Check if the deletion was successful
            if (deleted) {
                System.out.println("Image file deleted: " + imagePath);
            } else {
                // Handle the case where the file deletion fails
                System.out.println("Failed to delete image file: " + imagePath);
            }
        } else {
            // Handle the case where the file does not exist
            System.out.println("Image file does not exist: " + imagePath);
        }
    }
}
