package com.example.tourplanner.MVVM.Controller.Update;

import com.example.tourplanner.HelloApplication;
import com.example.tourplanner.MVVM.ViewModel.TourLogViewModel;
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

public class UpdateTourLogController implements Initializable {
    private TourLogViewModel tourLogViewModel;

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


    public void setTourLogViewModel(TourLogViewModel tourLogViewModel) { this.tourLogViewModel = tourLogViewModel; }
    public TourLogViewModel getTourLogViewModel() { return tourLogViewModel; }

    public static void updateTourLogWindow(Stage stage, TourLogViewModel tlvm) {
        try {
            UpdateTourLogController updateTourLogViewController = new UpdateTourLogController();
            updateTourLogViewController.setTourLogViewModel(tlvm);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("tour-log-view.fxml"));
            fxmlLoader.setController(updateTourLogViewController);
            Parent root = fxmlLoader.load();
            Stage editTourStage = new Stage();
            editTourStage.setTitle("Edit Tour Log");
            Scene editTourLogScene = new Scene(root, 600, 400);
            editTourStage.setScene(editTourLogScene);
            editTourStage.setMinWidth(600);
            editTourStage.setMinHeight(400);
            editTourStage.initOwner(stage);
            editTourStage.initModality(Modality.APPLICATION_MODAL);
            editTourStage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelTourLogButton(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    public void applyTourLogButton(ActionEvent actionEvent) {
        if(tourLogViewModel.editTourLog()) {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}
