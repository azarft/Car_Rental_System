package com.example.car_rental_system_final;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Car_Rental_Controller {

    private Stage primaryStage;

    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Button signUp;
    @FXML
    private void onClickButtonSignUp() {
        try {
            // Load the FXML file for the sign-up page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_up.fxml"));
            Parent signUpPage = fxmlLoader.load();

            // Set the sign-up page as the root of the existing scene
            primaryStage.getScene().setRoot(signUpPage);
            primaryStage.setTitle("Sign up page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
