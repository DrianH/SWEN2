package com.example.tourplanner.BuisnessLayer;

import com.example.tourplanner.MVVM.Model.Tour;
import com.example.tourplanner.MVVM.Model.TourLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface IBuisnessLogic {
    // add
    String addTour(Tour t);
    String addTourLog(Tour t, TourLog tl);

    // get
    List<Tour> getAllTours();
    List<TourLog> getAllTourLogs();
    Tour getTourById(String id);
    List<TourLog> getLogOfTour(Tour t);
    File getTourImage(String name);

    // update
    boolean updateTour(Tour t);
    boolean updateTourLog(TourLog tl);

    // delete
    boolean deleteTourById(String id);
    boolean deleteTourLogById(String id);
    boolean deleteTourImageByName(String name);

    // map-quest
    String getTourMap(Tour t);
    ArrayList<String> getTourDetails(Tour t);
    void generateTourReport(Tour t, List<TourLog> tls);
    void generateSummaryReport(List<Tour> ts, List<TourLog> tls);
}
