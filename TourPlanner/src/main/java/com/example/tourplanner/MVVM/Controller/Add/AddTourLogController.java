package com.example.tourplanner.MVVM.Controller.Add;

import com.example.tourplanner.HelloApplication;
import com.example.tourplanner.MVVM.ViewModel.TourLogViewModel;
import com.example.tourplanner.MVVM.ViewModel.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTourLogController implements Initializable {


    private TourViewModel tourViewModel;
    private final TourLogViewModel tourLogViewModel = new TourLogViewModel();

    @FXML
    public DatePicker dateSelection;
    @FXML
    public TextArea commentTextArea;
    @FXML
    public TextField totalTimeField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateSelection.valueProperty().bindBidirectional(tourLogViewModel.dateProperty());
        commentTextArea.textProperty().bindBidirectional(tourLogViewModel.commentProperty());
        totalTimeField.textProperty().bindBidirectional(tourLogViewModel.totalTimeProperty());
    }

    public static void addTourLogWindow(Stage stage, TourViewModel tvm) {
        try {
            AddTourLogController addTourLogViewController = new AddTourLogController();
            addTourLogViewController.initializeTourViewModel(tvm);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("tour-log-view.fxml"));
            fxmlLoader.setController(addTourLogViewController);
            Parent root = fxmlLoader.load();
            Stage newTourLogStage = new Stage();
            newTourLogStage.setTitle("Add Tour Log");
            Scene newTourLogScene = new Scene(root, 600, 400);
            newTourLogStage.setScene(newTourLogScene);
            newTourLogStage.setMinWidth(600);
            newTourLogStage.setMinHeight(400);
            newTourLogStage.initOwner(stage);
            newTourLogStage.initModality(Modality.APPLICATION_MODAL);
            newTourLogStage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeTourViewModel(TourViewModel tourViewModel) {
        this.tourViewModel = tourViewModel;
        tourLogViewModel.setTour(tourViewModel.initializeTour());
    }

    public void cancelTourLogButton(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void applyTourLogButton(ActionEvent actionEvent) {
        if(tourLogViewModel.createNewTourLog() != null) {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}
