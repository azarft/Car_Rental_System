package com.example.car_rental_system_final;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Sign_up_Controller {


    @FXML
    public TextField user_name = new TextField();

    @FXML
    public TextField user_surname = new TextField();

    @FXML
    public TextField user_email = new TextField();

    @FXML
    public TextField user_phone = new TextField();

    @FXML
    public TextField user_address = new TextField();

    @FXML
    public DatePicker user_birthday = new DatePicker();
    @FXML
    public TextField user_password1 = new TextField();

    @FXML
    public TextField user_password2 = new TextField();




    @FXML
    private void OnSignupButtonClick(){
        User newUser = new User();
        newUser.setUser_name(user_name.getText());
        newUser.setUser_surname(user_surname.getText());
        newUser.setUser_email(user_email.getText());
        newUser.setUser_phone(user_phone.getText());
        newUser.setUser_address(user_address.getText());
        newUser.setPassword(user_password1.getText());
//        newUser.setBirthdate(user_birthday.getValue());

        int result = TaskDAO.createUser(newUser);

        if (result == 1) {
            System.out.println("User created successfully");
            // Add code for any further actions after successful user creation
        } else {
            System.out.println("Failed to create user");
            // Add code for handling the failure to create a user
        }
    }



    private Stage primaryStage;


    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    private Hyperlink SignIn;

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
}
