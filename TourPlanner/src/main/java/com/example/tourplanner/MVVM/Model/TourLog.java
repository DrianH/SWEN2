package com.example.tourplanner.MVVM.Model;

import java.time.LocalDate;

public class TourLog {
    private String id;
    private Tour tour;
    private LocalDate date;
    private String commentary;
    private String totalTime; // time that was actually recired in hh:mm

    public TourLog(String id, Tour tour, LocalDate date, String commentary, String totalTime) {
        this.id = id;
        this.tour = tour;
        this.date = date;
        this.commentary = commentary;
        this.totalTime = totalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
