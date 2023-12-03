package com.example.car_rental_system_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Car_Rental_System extends Application {

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
        launch();
    }
}

//package com.example.car_rental_system_final;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class Car_Rental_System extends Application {
//
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin_Page.fxml"));
//        Parent root = fxmlLoader.load();
//
//        // Set the main page as the root of the scene
//        Scene scene = new Scene(root, 900, 600);
//
//        // Set the controller and the primary stage for the main page
//        AdminPageController controller = fxmlLoader.getController();
//        controller.setPrimaryStage(stage);
//
//        stage.setTitle("Admin Page");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) throws SQLException {
//        launch();
//    }
//}

