package com.metacoders.hurrydriver.Models;

public class compeletedModel {
    String fromLocation , status , toLocation ,tripID , CompleteDate ,fareGained ;

    public compeletedModel() {
    }

    public compeletedModel(String fromLocation, String status, String toLocation, String tripID, String completeDate, String fareGained) {
        this.fromLocation = fromLocation;
        this.status = status;
        this.toLocation = toLocation;
        this.tripID = tripID;
        this.CompleteDate = completeDate;
        this.fareGained = fareGained;
    }

    public String getFareGained() {
        return fareGained;
    }

    public void setFareGained(String fareGained) {
        this.fareGained = fareGained;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getCompleteDate() {
        return CompleteDate;
    }

    public void setCompleteDate(String completeDate) {
        CompleteDate = completeDate;
    }
}
