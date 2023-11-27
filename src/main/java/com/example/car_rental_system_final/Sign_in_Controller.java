package com.example.car_rental_system_final;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Sign_in_Controller {

    private Stage primaryStage;

    @FXML
    public TextField emailorphone = new TextField();

    @FXML
    public TextField password = new TextField();


    @FXML
    public void onSinginButtonClick(){
        User user = new User();
        user.setUser_email(emailorphone.getText());
        user.setUser_phone(emailorphone.getText());
        user.setPassword(password.getText());
        boolean userExists = TaskDAO.isUserExist(user);

        if (userExists) {
            System.out.println("User in database");
        } else {
            System.out.println("Failed to find user");
            // Add code for handling the failure to create a user
        }
    }
    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Hyperlink SignUp;

    @FXML
    private void onClickLinkSignUp() {
        try {
            // Load the FXML file for the sign-in page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_up.fxml"));
            Parent signUpPage = fxmlLoader.load();

            Sign_up_Controller signUpController = fxmlLoader.getController();
            signUpController.setPrimaryStage(primaryStage);

            // Set the sign-in page as the root of the existing scene
            primaryStage.getScene().setRoot(signUpPage);
            primaryStage.setTitle("Sign in page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
