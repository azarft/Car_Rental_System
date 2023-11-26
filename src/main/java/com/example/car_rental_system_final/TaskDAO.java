package com.example.car_rental_system_final;

import java.sql.*;
import java.sql.DriverManager;
public class TaskDAO {
    private static Connection conn;
    private static final String dbURL = "jdbc:postgresql://localhost:5432/carRent";
    private static final String username = "postgres";

    private static final String password = "1234";
    public static String ConnectToDatabase() throws SQLException {

        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            return "Connected to database";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    public static int createUser(User user){

        String sql = "INSERT INTO \"user\"(" +
                " \"name\", surname, email, phone, address, password)" +
                " VALUES (?, ?, ?, ?, ?, ?);";

        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, user.getUser_name());
            stmt.setString(2, user.getUser_surname());
            stmt.setString(3, user.getUser_email());
            stmt.setString(4, user.getUser_phone());
            stmt.setString(5, user.getUser_address());
            stmt.setString(6, user.getPassword());
//            stmt.setDate(7, new java.sql.Date(user.getBirthdate().getDate()));
            int affectedRows = stmt.executeUpdate();

            if( affectedRows == 0){
                throw new SQLException("Creating task failed.");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return 1;
    }
}
