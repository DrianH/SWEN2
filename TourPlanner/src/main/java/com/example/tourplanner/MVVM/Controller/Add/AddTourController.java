package com.example.tourplanner.MVVM.Controller.Add;

import com.example.tourplanner.HelloApplication;
import com.example.tourplanner.MVVM.ViewModel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTourController implements Initializable {

    public static void addTourWindow(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("tour-view.fxml"));
            fxmlLoader.setController(new AddTourController());
            Parent root = fxmlLoader.load();
            Stage newTourStage = new Stage();
            newTourStage.setTitle("Add Tour");
            Scene newTourScene = new Scene(root, 600, 400);
            newTourStage.setScene(newTourScene);
            newTourStage.setMinWidth(600);
            newTourStage.setMinHeight(400);
            newTourStage.initOwner(stage);
            newTourStage.initModality(Modality.APPLICATION_MODAL);
            newTourStage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private TourViewModel tourViewModel = new TourViewModel();

    @FXML
    public TextField tourNameField;
    @FXML
    public TextField tourFromField;
    @FXML
    public TextField tourToField;
    @FXML
    public ComboBox<String> tourTransportMenu;
    @FXML
    public TextArea tourDescField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tourNameField.textProperty().bindBidirectional(tourViewModel.nameProperty());
        tourFromField.textProperty().bindBidirectional(tourViewModel.fromProperty());
        tourToField.textProperty().bindBidirectional(tourViewModel.toProperty());
        ObservableList<String> transportTypes = FXCollections.observableArrayList();
        transportTypes.add("CAR");
        transportTypes.add("BUS");
        transportTypes.add("WALKING");
        transportTypes.add("BICYCLE");
        tourTransportMenu.setItems(transportTypes);
        tourTransportMenu.valueProperty().bindBidirectional(tourViewModel.transportTypeProperty());
        tourDescField.textProperty().bindBidirectional(tourViewModel.descriptionProperty());
    }
    public void tourCancelButton(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    public void tourApplyButton(ActionEvent actionEvent) {
        if(tourViewModel.createNewTour() != null) {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}
