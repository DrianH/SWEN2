package com.example.tourplanner.MVVM.ViewModel;

import com.example.tourplanner.BuisnessLayer.BuisnessLogicFactory;
import com.example.tourplanner.BuisnessLayer.Event.EventManagerFactory;
import com.example.tourplanner.BuisnessLayer.Event.IEventManager;
import com.example.tourplanner.BuisnessLayer.IBuisnessLogic;
import com.example.tourplanner.MVVM.Model.Tour;
import com.example.tourplanner.MVVM.Model.TourLog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.UUID;

public class TourLogViewModel {

    private final IEventManager eventManager = EventManagerFactory.getEM();
    private final IBuisnessLogic bl = BuisnessLogicFactory.getBuisnessLogic();

    private final StringProperty tourLogId;
    private Tour tour;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty comment;
    private final StringProperty totalTime;


    public TourLogViewModel() {
        this.tourLogId = null;
        this.tour = null;
        this.date = new SimpleObjectProperty<>();
        this.comment = new SimpleStringProperty("");
        this.totalTime = new SimpleStringProperty("");
    }

    public TourLogViewModel(TourLog tl) {
        this.tourLogId = new SimpleStringProperty(tl.getId());
        this.tour = tl.getTour();
        this.date = new SimpleObjectProperty<>(tl.getDate());
        this.comment = new SimpleStringProperty(tl.getCommentary());
        this.totalTime = new SimpleStringProperty(tl.getTotalTime());
    }

    public String createNewTourLog() {
        TourLog tl = this.initializeTourLog();
        String result = bl.addTourLog(tour, tl);

        if(result != null) {
            eventManager.notify("createTourLog", result);
        }
        return result;
    }

    public TourLog initializeTourLog() {
        String id = UUID.randomUUID().toString();
        TourLog tl = new TourLog(id, tour, date.getValue(), comment.getValue(), totalTime.getValue());
        return tl;
    }


    public boolean editTourLog() {
        TourLog updatedTourLog = new TourLog(getTourLogIdValue(), getTour(), getDateValue(), getCommentValue(), getTotalTimeValue());
        boolean result = bl.updateTourLog(updatedTourLog);
        if(result) {
            eventManager.notify("updateTourLog", getTourLogIdValue());
        }

        return result;
    }

    public StringProperty tourLogIdProperty() { return tourLogId; }
    public String getTourLogIdValue() { return tourLogId.getValue(); }

    public Tour getTour() { return tour; }
    public void setTour(Tour tour) { this.tour = tour; }

    public ObjectProperty<LocalDate> dateProperty() { return date; }
    public LocalDate getDateValue() { return date.getValue(); }

    public StringProperty commentProperty() { return comment; }
    public String getCommentValue() { return comment.getValue(); }

    public StringProperty totalTimeProperty() { return totalTime; }
    public String getTotalTimeValue() { return totalTime.getValue(); }
}
