module com.example.car_rental_system_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.car_rental_system_final to javafx.fxml;
    exports com.example.car_rental_system_final;
}