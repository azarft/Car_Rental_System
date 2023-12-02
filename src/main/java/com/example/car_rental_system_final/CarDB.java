package com.example.car_rental_system_final;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CarDB {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "123456";

    public CarDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "select * from car c join images i on c.id = i.car_id";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        cars.add(getCarFromResultSet(resultSet));
                    }
                }
            }
        }
        return cars;
    }

    private Car getCarFromResultSet(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getInt("id"));
        car.setType(resultSet.getString("type"));
        car.setPricePerDay(resultSet.getDouble("price_per_day"));
        car.setColor(resultSet.getString("color"));
        car.setBrand(resultSet.getString("brand"));
        car.setVolume(resultSet.getInt("volume"));
        car.setCapacity(resultSet.getInt("capacity"));
        car.setImagePath(resultSet.getString("image_path"));
        return car;
    }
}
