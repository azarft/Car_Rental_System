package com.example.car_rental_system_final;

public class Car {
    private int id;
    private String type;
    private double pricePerDay;
    private String color;
    private String brand;
    private int volume;
    private int capacity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        String spaceForBrand = "";
        for(int i = 0; i < 20 - this.brand.length(); i++){
            spaceForBrand += " ";
        }

        String spaceForType = "";
        for(int i = 0; i < 20 - this.type.length(); i++){
            spaceForType += " ";
        }
        return this.brand + spaceForBrand + " " + this.type + spaceForType + " " + this.pricePerDay + "$";
    }
}
