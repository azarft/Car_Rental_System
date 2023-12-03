package com.example.car_rental_system_final;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RentPageController {
    @FXML
    private ImageView Logo;

    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResource("/images/logo.png").toExternalForm());
        Logo.setImage(image);
    }

}
