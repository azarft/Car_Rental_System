package com.example.car_rental_system_final;

import java.sql.*;
import java.sql.DriverManager;

public class TaskDAO {
    private static Connection conn ;

    private static final String dbURL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "azar";

    private static final String password = "azar";
    public static String ConnectToDatabase() throws SQLException {

        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            return "Connected to database";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
