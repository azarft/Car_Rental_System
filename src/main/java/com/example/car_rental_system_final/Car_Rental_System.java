package com.example.car_rental_system_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Car_Rental_System extends Application {

    private static Car car;
    private static String errorMessage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sign_in.fxml"));
        Parent root = fxmlLoader.load();

        // Set the main page as the root of the scene
        Scene scene = new Scene(root, 900, 600);

        // Set the controller and the primary stage for the main page
        Sign_in_Controller controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);

        stage.setTitle("Sign In Page");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws SQLException {
        UserDB.ConnectToDatabase();
        launch();
    }

    public static void updateUserInfo(String userName, int userId) {
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setUserName(userName);
        userInfo.setUserId(userId);
    }

    public static Car getCar() {
        return car;
    }

    public static void setCar(Car car1) {
        car = car1;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(String errorMessage1) {
        errorMessage = errorMessage1;
    }
}

