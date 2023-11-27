package com.example.car_rental_system_final;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Sign_up_Controller {

    @FXML
    private AnchorPane signUp1;

    @FXML
    private AnchorPane signUp2;
    private Stage primaryStage;
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
    private TextField Passport;
    @FXML
    private TextField DriverLicence;
    @FXML
    private CheckBox A;
    @FXML
    private CheckBox A1;
    @FXML
    private CheckBox B;
    @FXML
    private CheckBox B1;
    @FXML
    private CheckBox BE;
    @FXML
    private CheckBox C;
    @FXML
    private CheckBox C1;
    @FXML
    private CheckBox C1E;
    @FXML
    private CheckBox CE;
    @FXML
    private CheckBox D;
    @FXML
    private CheckBox DE;
    @FXML
    private CheckBox D1;
    @FXML
    private CheckBox D1E;
    @FXML
    private CheckBox T;

    @FXML
    public void onClickSignUpButton() {
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
    public void onClickCirclePage1() {
        navigateToSignUp();
    }

    @FXML
    public void onClickCirclePage2() {
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
            primaryStage.setTitle("Sign In Page");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    private void navigateToSignUp() {
        primaryStage.setTitle("Sign Up Page");
        signUp1.setVisible(true);
        signUp2.setVisible(false);
    }
    private void navigateToSignUp2() {
        signUp1.setVisible(false);
        signUp2.setVisible(true);
        primaryStage.setTitle("Sign Up Page2");
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
