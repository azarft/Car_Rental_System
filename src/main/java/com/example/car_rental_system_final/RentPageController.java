package com.example.car_rental_system_final;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;

public class RentPageController {
    @FXML
    private ImageView Logo;
    @FXML
    private TextField PickUpCity;
    @FXML
    private TextField DropOfCity;
    @FXML
    private TextField CardNumber;
    @FXML
    private TextField CardHolder;
    @FXML
    private TextField Cvc;
    @FXML
    private DatePicker DatePickUp;
    @FXML
    private DatePicker DateDropOf;
    @FXML
    private DatePicker CardExpirationDate;


    private Stage rentPageStage;

    @FXML
    private Label Error;

    @FXML
    private Button RentNow;

    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResource("/images/logo.png").toExternalForm());
        Logo.setImage(image);
    }

    @FXML
    public void onClickRentNowButton() {
        if (areAllFieldsFilled()) {
            try {
                Reservation reservation = createReservation();
                ReservationDB.insertReservation(reservation);
                Error.setText("You rented this car successfully!!");

                clearFields();
                // Close the stage after 3 seconds
                closeStage();
            } catch (Exception e) {
                if (Car_Rental_System.getErrorMessage() == null) {
                    Car_Rental_System.setErrorMessage(e.getMessage());
                    Error.setText(Car_Rental_System.getErrorMessage());
                } else {
                    Error.setText(Car_Rental_System.getErrorMessage());
                }
            }
        } else {
            Error.setText("Every field should be filled!!");
        }
    }

    private boolean areAllFieldsFilled() {
        // Check if all text fields and date pickers are not empty
        return !PickUpCity.getText().trim().isEmpty() &&
                !DropOfCity.getText().trim().isEmpty() &&
                !CardNumber.getText().trim().isEmpty() &&
                !CardHolder.getText().trim().isEmpty() &&
                !Cvc.getText().trim().isEmpty() &&
                DatePickUp.getValue() != null &&
                DateDropOf.getValue() != null &&
                CardExpirationDate.getValue() != null;
    }

    private Reservation createReservation() {
        // Retrieve values from JavaFX components
        String pickUpCity = PickUpCity.getText();
        String dropOfCity = DropOfCity.getText();
        String cardNumber = CardNumber.getText();
        String cardHolder = CardHolder.getText();
        String cvc = Cvc.getText();
        LocalDate datePickUp = DatePickUp.getValue();
        LocalDate dateDropOf = DateDropOf.getValue();
        LocalDate cardExpirationDate = CardExpirationDate.getValue();

        // Create a Reservation object
        Reservation reservation = new Reservation();

        // Set values to the Reservation object
        reservation.setPickUpLocation(pickUpCity);
        reservation.setDropOfLocation(dropOfCity);

        // Create a Card object and set values
        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setCardHolder(cardHolder);
        card.setCvc(Integer.parseInt(cvc));
        card.setExpirationDate(java.sql.Date.valueOf(cardExpirationDate));

        // Set the Card object to the Reservation
        reservation.setCard(card);

        // Set the date values
        reservation.setPickUpDate(java.sql.Date.valueOf(datePickUp));
        reservation.setDropOfDate(java.sql.Date.valueOf(dateDropOf));

        return reservation;
    }

    // Declare the rentPageStage at the class level

// ...

    private void closeStage() {
        // Check if the rentPageStage is not null and is showing
        if (rentPageStage != null && rentPageStage.isShowing()) {
            // Close only the rentPageStage, not the entire stage
            rentPageStage.close();
        }
    }

    @FXML
    private void clearFields() {
        PickUpCity.clear();
        DropOfCity.clear();
        CardNumber.clear();
        CardHolder.clear();
        Cvc.clear();
        DatePickUp.setValue(null);
        DateDropOf.setValue(null);
        CardExpirationDate.setValue(null);
    }
}
