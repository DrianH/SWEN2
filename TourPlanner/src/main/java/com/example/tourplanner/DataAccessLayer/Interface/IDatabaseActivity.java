package com.example.tourplanner.DataAccessLayer.Interface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IDatabaseActivity {
    String add(String query, ArrayList<Object> params) throws SQLException;
    boolean update(String query, ArrayList<Object> params) throws SQLException;
    boolean delete(String query, ArrayList<Object> params) throws SQLException;
    <T> List<T> read(String query) throws SQLException;
    <T> List<T> read(String query, ArrayList<Object> params) throws SQLException;
}
