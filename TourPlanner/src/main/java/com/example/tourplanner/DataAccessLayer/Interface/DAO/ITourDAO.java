package com.example.tourplanner.DataAccessLayer.Interface.DAO;

import com.example.tourplanner.MVVM.Model.Tour;

import java.sql.SQLException;
import java.util.List;

public interface ITourDAO {
    // get
    List<Tour> selectAllTours() throws SQLException;
    Tour findById(String id) throws SQLException;

    // change
    String insertTour(Tour t) throws SQLException;
    boolean updateTour(String id, Tour t) throws SQLException;
    boolean deleteTour(String id) throws SQLException;
}
