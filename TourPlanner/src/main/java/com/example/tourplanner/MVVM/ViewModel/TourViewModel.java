package com.example.tourplanner.MVVM.ViewModel;

import com.example.tourplanner.BuisnessLayer.BuisnessLogicFactory;
import com.example.tourplanner.BuisnessLayer.Event.EventManagerFactory;
import com.example.tourplanner.BuisnessLayer.Event.IEventManager;
import com.example.tourplanner.BuisnessLayer.IBuisnessLogic;
import com.example.tourplanner.MVVM.Model.Tour;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

public class TourViewModel {

    private final IEventManager eventManager = EventManagerFactory.getEM();
    private final IBuisnessLogic bl = BuisnessLogicFactory.getBuisnessLogic();

    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty from;
    private final StringProperty to;
    private final StringProperty transportType;
    private final FloatProperty distance;
    private final StringProperty estTime;
    private final StringProperty routeInfo;


    public TourViewModel() {
        this.id = null;
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.from = new SimpleStringProperty("");
        this.to = new SimpleStringProperty("");
        this.transportType = new SimpleStringProperty("");
        this.distance = new SimpleFloatProperty(0);
        this.estTime = new SimpleStringProperty("");
        this.routeInfo = new SimpleStringProperty("");
    }


    public TourViewModel(Tour t) {
        this.id = new SimpleStringProperty(t.getId());
        this.name = new SimpleStringProperty(t.getTourName());
        this.description = new SimpleStringProperty(t.getDescription());
        this.from = new SimpleStringProperty(t.getStartDest());
        this.to = new SimpleStringProperty(t.getEndDest());
        this.transportType = new SimpleStringProperty(t.getTransportType());
        this.distance = new SimpleFloatProperty(t.getDist());
        this.estTime = new SimpleStringProperty(t.getEstTime());
        this.routeInfo = new SimpleStringProperty(t.getInfo());
    }




    public String createNewTour() {
        UUID uuid = UUID.randomUUID();
        String tourId = ""+uuid;
        float mockValue = 0.0f; // TEMPORARY VALUE
        Tour t = new Tour(tourId, name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), mockValue, null, null);
        String result = bl.addTour(t);

        if(result != null) {
            eventManager.notify("CreateTour", result);
        }

        return result;
    }


    public boolean editTour() {
        Tour t = new Tour(id.getValue(), name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), distance.getValue(), estTime.getValue(), routeInfo.getValue());
        boolean result = bl.updateTour(t);
        if(result) {
            eventManager.notify("updateTour", id.getValue());
        }
        return result;
    }

    public Tour initializeTour() {
        Tour t = new Tour(id.getValue(), name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), distance.getValue(), estTime.getValue(), routeInfo.getValue());
        return t;
    }

    @Override
    public String toString() {
        return name.getValue();
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProp(){ return name;}

    public StringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }


    public StringProperty descriptionProperty() {
        return description;
    }

    public String getFrom() {
        return from.get();
    }

    public StringProperty fromProperty() {
        return from;
    }

    public String getTo() {
        return to.get();
    }

    public StringProperty toProperty() {
        return to;
    }

    public String getTransportType() {
        return transportType.get();
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public float getDistance() {
        return distance.get();
    }

    public FloatProperty distanceProperty() {
        return distance;
    }

    public String getEstTime() {
        return estTime.get();
    }

    public StringProperty estTimeProperty() {
        return estTime;
    }

    public String getRouteInfo() {
        return routeInfo.get();
    }

    public StringProperty routeInfoProperty() {
        return routeInfo;
    }
}
