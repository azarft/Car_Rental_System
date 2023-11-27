package com.example.car_rental_system_final;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainPageController {

    @FXML
    private ImageView Logo;

    @FXML
    private Slider PriceSlider;

    @FXML
    private Label Price;

    @FXML
    public void initialize() {
        // Set the image programmatically
        Image image = new Image(getClass().getResource("/images/logo.png").toExternalForm());
        Logo.setImage(image);


        // Set the minimum and maximum values for the PriceSlider
        PriceSlider.setMin(0);
        PriceSlider.setMax(400);


        PriceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Update the Label text with the current value of the slider
                Price.setText("$" + String.format("%.2f", newValue.doubleValue()));
            }
        });

    }


}
