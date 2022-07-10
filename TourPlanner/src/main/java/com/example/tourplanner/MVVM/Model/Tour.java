package com.example.tourplanner.MVVM.Model;

public class Tour {
    private String id;
    private String tourName;
    private String description;
    private String startDest;
    private String endDest;
    private String transportType;
    private float dist;
    private String estTime; //estimation in hh:mm
    private String info;

    public Tour(String id, String tourName, String description, String startDest, String endDest, String transportType, float dist, String estTime, String info) {
        this.id = id;
        this.tourName = tourName;
        this.description = description;
        this.startDest = startDest;
        this.endDest = endDest;
        this.transportType = transportType;
        this.dist = dist;
        this.estTime = estTime;
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDest() {
        return startDest;
    }

    public void setStartDest(String startDest) {
        this.startDest = startDest;
    }

    public String getEndDest() {
        return endDest;
    }

    public void setEndDest(String endDest) {
        this.endDest = endDest;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    public String getEstTime() {
        return estTime;
    }

    public void setEstTime(String estTime) {
        this.estTime = estTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}