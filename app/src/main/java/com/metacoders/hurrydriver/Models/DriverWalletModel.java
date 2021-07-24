package com.metacoders.hurrydriver.Models;

public class DriverWalletModel {
    String time , tripID , driverID  ;
    double amount ;

    boolean isEarned  ; // true as received false as withdrawed ;

    public DriverWalletModel() {
    }

    public DriverWalletModel(String time, String tripID, String driverID, double amount, boolean isEarned) {
        this.time = time;
        this.tripID = tripID;
        this.driverID = driverID;
        this.amount = amount;
        this.isEarned = isEarned;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isEarned() {
        return isEarned;
    }

    public void setEarned(boolean earned) {
        isEarned = earned;
    }
}
