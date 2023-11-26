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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_page.fxml"));
        Parent root = fxmlLoader.load();

        // Set the main page as the root of the scene
        Scene scene = new Scene(root, 900, 600);

        // Set the controller and the primary stage for the main page
        Car_Rental_Controller controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);

        stage.setTitle("Car Rental System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(TaskDAO.ConnectToDatabase());
        launch();
    }
}

