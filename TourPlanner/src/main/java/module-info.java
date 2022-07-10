module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires io;
    requires kernel;
    requires layout;
    requires javafx.media;

    opens com.example.tourplanner to javafx.fxml;
    exports com.example.tourplanner;
    exports com.example.tourplanner.MVVM.Controller;
    opens com.example.tourplanner.MVVM.Controller to javafx.fxml;
    opens com.example.tourplanner.MVVM.Controller.Add to javafx.fxml;
    opens com.example.tourplanner.MVVM.Controller.Update to javafx.fxml;
    opens com.example.tourplanner.MVVM.ViewModel;
    exports com.example.tourplanner.BuisnessLayer;
    exports com.example.tourplanner.BuisnessLayer.Event;
    exports com.example.tourplanner.BuisnessLayer.Manager;
    exports com.example.tourplanner.BuisnessLayer.MapQuestAPI;
    exports com.example.tourplanner.DataAccessLayer.Database;
    exports com.example.tourplanner.DataAccessLayer.Factory;
    exports com.example.tourplanner.DataAccessLayer.Interface;
    exports com.example.tourplanner.DataAccessLayer.LocalFiles;
    exports com.example.tourplanner.MVVM.Model;
}