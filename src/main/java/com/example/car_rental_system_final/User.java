package com.example.car_rental_system_final;

import javafx.scene.control.DatePicker;

import java.sql.Date;

public class User {
    private String user_name;
    private String user_surname;
    private String user_email;
    private Date user_birthday;
    private String user_phone;
    private String user_address;
    private String user_password;

    private String user_passportNumber;
    private String user_licenseNumber;

    public String getUser_licenseCategoria() {
        return user_licenseCategoria;
    }

    public void setUser_licenseCategoria(String user_licenseCategoria) {
        this.user_licenseCategoria = user_licenseCategoria;
    }

    private String user_licenseCategoria;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Date getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(Date user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_passportNumber() {
        return user_passportNumber;
    }

    public void setUser_passportNumber(String user_passportNumber) {
        this.user_passportNumber = user_passportNumber;
    }

    public String getUser_licenseNumber() {
        return user_licenseNumber;
    }

    public void setUser_licenseNumber(String user_licenseNumber) {
        this.user_licenseNumber = user_licenseNumber;
    }



}