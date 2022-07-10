package com.example.tourplanner.MVVM.ViewModel;

import com.example.tourplanner.BuisnessLayer.BuisnessLogicFactory;
import com.example.tourplanner.BuisnessLayer.Event.EventManagerFactory;
import com.example.tourplanner.BuisnessLayer.Event.IEventListener;
import com.example.tourplanner.BuisnessLayer.Event.IEventManager;
import com.example.tourplanner.BuisnessLayer.IBuisnessLogic;
import com.example.tourplanner.MVVM.Model.Tour;
import com.example.tourplanner.MVVM.Model.TourLog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class MainViewModel implements IEventListener {
    private final IEventManager eventManager = EventManagerFactory.getEM();
    private final IBuisnessLogic bl = BuisnessLogicFactory.getBuisnessLogic();


    private final StringProperty id = new SimpleStringProperty("");
    private final StringProperty searchField = new SimpleStringProperty("");
    private final StringProperty currTourName = new SimpleStringProperty("");
    private final StringProperty currTourDescription = new SimpleStringProperty("");
    private final ObjectProperty<Image> currTourImage = new SimpleObjectProperty<>();
    private final ObservableList<TourViewModel> allTours = FXCollections.observableArrayList();
    FilteredList<TourViewModel> filteredTourList = new FilteredList<>(allTours, s -> true);
    private final ObservableList<TourLogViewModel> logsOfTours = FXCollections.observableArrayList();


    public MainViewModel() {
        eventManager.subscribe("createTour", this);
        eventManager.subscribe("updateTour", this);
        eventManager.subscribe("deleteTour", this);
        eventManager.subscribe("createTourLog", this);
        eventManager.subscribe("updateTourLog", this);
        eventManager.subscribe("deleteTourLog", this);

        for(Tour t : bl.getAllTours()) {
            allTours.add(new TourViewModel(t));
        }
    }

    @Override
    public void update(String event, Object object) {
        if("updateTour".equals(event)) {
            this.syncAllTours();
            this.syncCurrentTour();
        }
        if("createTour".equals(event)||"deleteTour".equals(event)) {
            this.syncAllTours();
        }
        if("createTourLog".equals(event) || "updateTourLog".equals(event) || "deleteTourLog".equals(event)) {
            this.syncCurrentTourLog();
        }
    }

    public void fillInCurrentTourDetails(TourViewModel t) {
        this.id.setValue(t.getId());
        this.currTourName.setValue(t.getName() + " (Distance: " + t.getDistance() + "km)");
        this.currTourDescription.setValue(t.getDescription()
                + "\nTransport Type: " + t.getTransportType()
                + "\nEstimated Time: " + t.getEstTime());
        this.loadCurrentTourLogs(t);

        try {
            File tourMap = bl.getTourImage(t.getId());
            FileInputStream fileInputStream = new FileInputStream(new File(tourMap.getPath().replace('\\','/')));
            Image tourImage = new Image(fileInputStream);
            this.currTourImage.setValue(tourImage);
            fileInputStream.close();
        } catch(IOException e) {
            this.currTourImage.setValue(null);
        }
    }

    private void syncAllTours() {
        allTours.clear();
        for(Tour t : bl.getAllTours()) {
            allTours.add(new TourViewModel(t));
        }
    }

    private void syncCurrentTour() {
        Tour t = bl.getTourById(id.getValue());
        this.fillInCurrentTourDetails(new TourViewModel(t));
    }

    private void syncCurrentTourLog() {
        Tour t = bl.getTourById(id.getValue());
        this.loadCurrentTourLogs(new TourViewModel(t));
    }

    public void loadCurrentTourLogs(TourViewModel tvm) {
        logsOfTours.clear();

        Tour tour = tvm.initializeTour();
        for(TourLog tl : bl.getLogOfTour(tour)) {
            TourLogViewModel tourLogViewModel = new TourLogViewModel(tl);
            logsOfTours.add(tourLogViewModel);
        }
    }

    private void unloadCurrentTour() {
        this.id.setValue("");
        this.currTourName.setValue("");
        this.currTourDescription.setValue("");
        this.currTourImage.setValue(null);
        this.logsOfTours.setAll(FXCollections.observableArrayList());
    }

    public boolean deleteTourLog(String id) {
        boolean result = bl.deleteTourLogById(id);

        if(result) {
            eventManager.notify("deleteTourLog", id);
        }

        return result;
    }

    public boolean deleteTourImage(String name) {
        boolean result = bl.deleteTourImageByName(name);

        return result;
    }

    public boolean deleteTour(String id) {
        boolean result = bl.deleteTourById(id);
        if(result) {
            eventManager.notify("deleteTour", id);
            this.unloadCurrentTour();
            this.deleteTourImage(id.toString());
        }

        return result;
    }

    public void generateTourReport(TourViewModel tvm) {
        String tourId = tvm.getId();

        Tour t = bl.getTourById(tourId);
        List<TourLog> tls = bl.getLogOfTour(t);

        bl.generateTourReport(t, tls);
    }


    public void generateSummaryReport() {
        List<Tour> allTours = bl.getAllTours();
        List<TourLog> allTourLogs = bl.getAllTourLogs();

        bl.generateSummaryReport(allTours, allTourLogs);
    }

    public StringProperty getId() { return id; }

    public StringProperty getSearchField() { return searchField; }

    public StringProperty getCurrTourName() { return currTourName; }

    public StringProperty getCurrTourDescription() { return currTourDescription; }

    public ObjectProperty<Image> getCurrTourImage() { return currTourImage; }

    public ObservableList<TourViewModel> getAllTours() { return allTours; }

    public FilteredList<TourViewModel> getFilteredTourList() { return filteredTourList; }

    public ObservableList<TourLogViewModel> getLogsOfTours() { return logsOfTours; }
}
