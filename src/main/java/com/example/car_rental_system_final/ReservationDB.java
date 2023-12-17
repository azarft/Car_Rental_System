package com.example.car_rental_system_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static javax.management.remote.JMXConnectorFactory.connect;

public class ReservationDB {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "azar";
    private static final String JDBC_PASSWORD = "azar";

    public ReservationDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static final String INSERT_RESERVATION_SQL = "INSERT INTO reservation (user_id, car_id,  starting_date, ending_date, pickup_location, return_location, total_cost, arrival_time, arrival_fine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_PAYMENT_SQL = "INSERT INTO payment (user_id, card_number, card_holder, expiration_date, cvc) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_RESERVATION_SQL = "SELECT * FROM reservation WHERE user_id = ?";
    private static final String UPDATE_RESERVATION_SQL = "UPDATE reservation SET starting_date = ?, ending_date = ?, pickup_location = ?, return_location = ?, total_cost = ?, arrival_time = ?, arrival_fine = ? WHERE user_id = ?";
    private static final String CHECK_RESERVATION_SQL = "SELECT COUNT(*) FROM reservation WHERE user_id = ? AND car_id = ?";
    private static final String SELECT_RESERVATIONS_BY_USER_CAR_SQL =
            "SELECT * FROM reservation WHERE user_id = ? AND car_id = ?";
    public static void insertReservation(Reservation reservation) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            UserInfo userInfo = UserInfo.getInstance();
            // Insert reservation
            try (PreparedStatement reservationStmt = conn.prepareStatement(INSERT_RESERVATION_SQL)) {
                reservationStmt.setInt(1, userInfo.getUserId());
                reservationStmt.setInt(2, Car_Rental_System.getCar().getId());
                reservationStmt.setDate(3, new java.sql.Date(reservation.getPickUpDate().getTime()));
                reservationStmt.setDate(4, new java.sql.Date(reservation.getDropOfDate().getTime()));
                reservationStmt.setString(5, reservation.getPickUpLocation());
                reservationStmt.setString(6, reservation.getDropOfLocation());
                int daysBetween = (int) ChronoUnit.DAYS.between(
                        new java.sql.Date(reservation.getPickUpDate().getTime()).toLocalDate(),
                        new java.sql.Date(reservation.getDropOfDate().getTime()).toLocalDate()
                );
                reservationStmt.setDouble(7, Car_Rental_System.getCar().getPricePerDay() * daysBetween);

                // Set arrival time to 01.01.0001 and arrival fine to zero
                reservationStmt.setDate(8, new java.sql.Date(0));
                reservationStmt.setDouble(9, 0.0);

                reservationStmt.executeUpdate();

                // Insert payment
                try (PreparedStatement paymentStmt = conn.prepareStatement(INSERT_PAYMENT_SQL)) {
                    paymentStmt.setInt(1, userInfo.getUserId());
                    paymentStmt.setString(2, reservation.getCard().getCardNumber());
                    paymentStmt.setString(3, reservation.getCard().getCardHolder());
                    paymentStmt.setDate(4, new java.sql.Date(reservation.getCard().getExpirationDate().getTime()));
                    paymentStmt.setInt(5, reservation.getCard().getCvc());
                    paymentStmt.executeUpdate();
                }
            }
            catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
                Car_Rental_System.setErrorMessage(e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            Car_Rental_System.setErrorMessage(e.getMessage());
        }
    }

    public static boolean hasReservation() {
        UserInfo userInfo = UserInfo.getInstance();
        int userId = userInfo.getUserId();
        int carId = Car_Rental_System.getCar().getId();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement checkStmt = conn.prepareStatement(CHECK_RESERVATION_SQL)) {

            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, carId);

            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next()) {
                    int reservationCount = resultSet.getInt(1);
                    return reservationCount > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Return false in case of an exception or if the query did not return any result
        return false;
    }
    public static Reservation[] getReservationsByUserAndCar() {
        UserInfo userInfo = UserInfo.getInstance();
        int userId = userInfo.getUserId();
        int carId = Car_Rental_System.getCar().getId();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement selectStmt = conn.prepareStatement(SELECT_RESERVATIONS_BY_USER_CAR_SQL)) {

            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, carId);

            try (ResultSet resultSet = selectStmt.executeQuery()) {
                List<Reservation> reservations = new ArrayList<>();

                while (resultSet.next()) {
                    Reservation reservation = new Reservation();
                    // Set reservation properties from the ResultSet
                    reservation.setPickUpDate(resultSet.getDate("starting_date"));
                    reservation.setDropOfDate(resultSet.getDate("ending_date"));
                    reservation.setPickUpLocation(resultSet.getString("pickup_location"));
                    reservation.setDropOfLocation(resultSet.getString("return_location"));

                    reservations.add(reservation);
                }

                return reservations.toArray(new Reservation[0]);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Return an empty array in case of an exception
        return new Reservation[0];
    }

}