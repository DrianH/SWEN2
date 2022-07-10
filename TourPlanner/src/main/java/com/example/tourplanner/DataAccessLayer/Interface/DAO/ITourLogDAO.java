package com.example.tourplanner.DataAccessLayer.Interface.DAO;

import com.example.tourplanner.MVVM.Model.Tour;
import com.example.tourplanner.MVVM.Model.TourLog;

import java.sql.SQLException;
import java.util.List;

public interface ITourLogDAO {
    // get
    List<TourLog> selectAllTourLogs() throws SQLException;
    TourLog findById(String id) throws SQLException;
    List<TourLog> selectLogOfTour(Tour t) throws SQLException;

    //change
    String insertTourLog(TourLog tl, Tour t) throws SQLException;
    boolean updateTourLog(String id, TourLog tl) throws SQLException;
    boolean deleteTourLog(String id) throws SQLException;


}
