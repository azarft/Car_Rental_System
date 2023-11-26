package com.example.car_rental_system_final;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Sign_up_Controller {

    private Stage primaryStage;
    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Hyperlink SignIn;
    @FXML
    private TextField NameField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField PhoneField;
    @FXML
    private TextField AddressField;
    @FXML
    private DatePicker BirthdayPick;
    @FXML
    private RadioButton MaleGender;
    @FXML
    private RadioButton FemaleGender;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField ConfirmPassword;
    @FXML
    private Button SignUpButton;
    @FXML
    private Circle Page1;
    @FXML
    private Circle Page2;
    @FXML
    private Label CautionLabel;

    @FXML
    public void onClickSignUpButton(){
        if (NameField.getText().isEmpty() ||
                EmailField.getText().isEmpty() ||
                PhoneField.getText().isEmpty() ||
                AddressField.getText().isEmpty() ||
                (BirthdayPick.getValue() == null) ||
                PasswordField.getText().isEmpty() ||
                ConfirmPassword.getText().isEmpty()) {
            CautionLabel.setText("Every field should be filled");
        } else {
            CautionLabel.setText("");
            navigateToMainPage();
        }
    }

    @FXML
    public void onClickCirclePage1(){
        navigateToSignUp();
    }

    @FXML
    public void onClickCirclePage2(){
        navigateToSignUp2();
    }

    @FXML
    private void onClickLinkSignIn() {
        try {
            // Load the FXML file for the sign-in page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_in.fxml"));
            Parent signInPage = fxmlLoader.load();

            Sign_in_Controller signInController = fxmlLoader.getController();
            signInController.setPrimaryStage(primaryStage);

            // Set the sign-in page as the root of the existing scene
            primaryStage.getScene().setRoot(signInPage);
            primaryStage.setTitle("Sign in page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void navigateToSignUp2() {
        try {
            // Load the FXML file for the sign-up page 2
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_up2.fxml"));
            Parent signUp2Page = fxmlLoader.load();

            Sign_up_Controller signUp2Controller = fxmlLoader.getController();
            signUp2Controller.setPrimaryStage(primaryStage);

            // Set the sign-up page 2 as the root of the existing scene
            primaryStage.getScene().setRoot(signUp2Page);
            primaryStage.setTitle("Sign up page");
        } catch (IOException e) {
            e.printStackTrace(); // Add more detailed error handling
        }
    }

    private void navigateToSignUp() {
        try {
            // Load the FXML file for the sign-in page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_up.fxml"));
            Parent signUpPage = fxmlLoader.load();

            Sign_up_Controller signUpController = fxmlLoader.getController();
            signUpController.setPrimaryStage(primaryStage);

            // Set the sign-up page as the root of the existing scene
            primaryStage.getScene().setRoot(signUpPage);
            primaryStage.setTitle("Sign in page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void navigateToMainPage(){
        try {
            // Load the FXML file for the sign-in page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main_Page.fxml"));
            Parent mainPage = fxmlLoader.load();

            // Set the sign-up page as the root of the existing scene
            primaryStage.getScene().setRoot(mainPage);
            primaryStage.setTitle("Main page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}
