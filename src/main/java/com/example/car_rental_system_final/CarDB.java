package com.example.car_rental_system_final;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDB {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "azar";
    private static final String JDBC_PASSWORD = "azar";

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
            String sql = "SELECT * FROM car c JOIN images i ON c.id = i.car_id";
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

    public List<Car> getFilteredCars(String filterText) throws SQLException {
        List<Car> filteredCars = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM car c JOIN images i ON c.id = i.car_id " + filterText;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        filteredCars.add(getCarFromResultSet(resultSet));
                    }
                }
            }
        }
        return filteredCars;
    }

    public void insertCar(Car car) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String insertCarSql = "INSERT INTO car(type, price_per_day, color, brand, volume, capacity) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
            try (PreparedStatement insertCarStatement = connection.prepareStatement(insertCarSql)) {
                insertCarStatement.setString(1, car.getType());
                insertCarStatement.setDouble(2, car.getPricePerDay());
                insertCarStatement.setString(3, car.getColor());
                insertCarStatement.setString(4, car.getBrand());
                insertCarStatement.setInt(5, car.getVolume());
                insertCarStatement.setInt(6, car.getCapacity());

                try (ResultSet resultSet = insertCarStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int carId = resultSet.getInt("id");
                        car.setId(carId);
                    }
                }
            }

            String insertImagesSql = "INSERT INTO images(image_path) VALUES (?)";
            try (PreparedStatement insertImagesStatement = connection.prepareStatement(insertImagesSql)) {
                insertImagesStatement.setString(1, car.getImagePath());
                insertImagesStatement.executeUpdate();
            }
        }
    }

    public void updateCar(Car car) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String updateCarSql = "UPDATE car SET type=?, price_per_day=?, color=?, brand=?, volume=?, capacity=? WHERE id=?";
            try (PreparedStatement updateCarStatement = connection.prepareStatement(updateCarSql)) {
                updateCarStatement.setString(1, car.getType());
                updateCarStatement.setDouble(2, car.getPricePerDay());
                updateCarStatement.setString(3, car.getColor());
                updateCarStatement.setString(4, car.getBrand());
                updateCarStatement.setInt(5, car.getVolume());
                updateCarStatement.setInt(6, car.getCapacity());
                updateCarStatement.setInt(7, car.getId());
                updateCarStatement.executeUpdate();
            }
        }
    }

    public void deleteCar(int carId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String deleteImagesSql = "DELETE FROM images WHERE car_id = ?";
            try (PreparedStatement deleteImagesStatement = connection.prepareStatement(deleteImagesSql)) {
                deleteImagesStatement.setInt(1, carId);
                deleteImagesStatement.executeUpdate();
            }

            String deleteCarSql = "DELETE FROM car WHERE id = ?";
            try (PreparedStatement deleteCarStatement = connection.prepareStatement(deleteCarSql)) {
                deleteCarStatement.setInt(1, carId);
                deleteCarStatement.executeUpdate();
            }
        }
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
