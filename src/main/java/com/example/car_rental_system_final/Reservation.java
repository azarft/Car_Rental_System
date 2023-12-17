package com.example.car_rental_system_final;

import java.math.BigDecimal;
import java.util.Date;

public class Reservation {
    private String pickUpLocation;
    private String dropOfLocation;
    private Date pickUpDate;
    private Date dropOfDate;
    private Card card;


    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDropOfLocation() {
        return dropOfLocation;
    }

    public void setDropOfLocation(String dropOfLocation) {
        this.dropOfLocation = dropOfLocation;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getDropOfDate() {
        return dropOfDate;
    }

    public void setDropOfDate(Date dropOfDate) {
        this.dropOfDate = dropOfDate;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String toString(){
        return pickUpLocation + "-" + dropOfLocation + " (" + pickUpDate + " - " + dropOfDate + ")";
    }
}
