package com.example.tourplanner.MVVM.Controller;

import com.example.tourplanner.HelloApplication;
import com.example.tourplanner.MVVM.Controller.Add.AddTourController;
import com.example.tourplanner.MVVM.Controller.Add.AddTourLogController;
import com.example.tourplanner.MVVM.Controller.Update.UpdateTourController;
import com.example.tourplanner.MVVM.Controller.Update.UpdateTourLogController;
import com.example.tourplanner.MVVM.ViewModel.MainViewModel;
import com.example.tourplanner.MVVM.ViewModel.TourLogViewModel;
import com.example.tourplanner.MVVM.ViewModel.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final MainViewModel mainViewModel = new MainViewModel();

    @FXML
    public TextField searchField;
    @FXML
    private Text currTourName;
    @FXML
    private Text currTourDescription;
    @FXML
    private ImageView currTourImage;
    @FXML
    private ListView<TourViewModel> tourListView;
    @FXML
    private TableView<TourLogViewModel> currTourLogs;
    @FXML
    private TableColumn<TourLogViewModel, LocalDate> dateValue;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.textProperty().bindBidirectional(mainViewModel.getSearchField());
        currTourName.textProperty().bindBidirectional(mainViewModel.getCurrTourName());
        currTourDescription.textProperty().bindBidirectional(mainViewModel.getCurrTourDescription());
        currTourImage.imageProperty().bindBidirectional(mainViewModel.getCurrTourImage());
        tourListView.setItems(mainViewModel.getFilteredTourList());
        currTourLogs.setItems(mainViewModel.getLogsOfTours());
    }

    public void generateTourReport(ActionEvent actionEvent) {
        TourViewModel t = tourListView.getSelectionModel().getSelectedItem();

        if(t != null) {
            mainViewModel.generateTourReport(t);
        }
    }

    public void generateSummaryReport(ActionEvent actionEvent) {
        mainViewModel.generateSummaryReport();
    }

    public void addTour(ActionEvent actionEvent) {
        Node newNode = (Node) actionEvent.getSource();
        Stage newStage = (Stage) newNode.getScene().getWindow();

        AddTourController.addTourWindow(newStage);
    }

    public void updateSelectedTour(ActionEvent actionEvent) {
        TourViewModel tvm = tourListView.getSelectionModel().getSelectedItem();

        if(tvm != null) {
            Node newNode = (Node) actionEvent.getSource();
            Stage newStage = (Stage) newNode.getScene().getWindow();

            UpdateTourController.updateTourWindow(newStage, tvm);
        }
    }

    public void deleteSelectedTour(ActionEvent actionEvent) {
        TourViewModel tvm = tourListView.getSelectionModel().getSelectedItem();
        if(tvm != null && tvm.getId() != null) {
            String tourId = tvm.getId();
            mainViewModel.deleteTour(tourId);
        }
    }

    public void tourSelectedClicked(MouseEvent mouseEvent) {
        TourViewModel tvm = tourListView.getSelectionModel().getSelectedItem();

        if(tvm != null) {
            mainViewModel.fillInCurrentTourDetails(tvm);
        }
    }

    public void addTourLog(ActionEvent actionEvent) {
        TourViewModel tour = tourListView.getSelectionModel().getSelectedItem();

        if(tour != null) {
            Node newNode = (Node) actionEvent.getSource();
            Stage newStage = (Stage) newNode.getScene().getWindow();

            AddTourLogController.addTourLogWindow(newStage, tour);
        }
    }

    public void deleteSelectedTourLog(ActionEvent actionEvent) {
        TourLogViewModel tlvm = currTourLogs.getSelectionModel().getSelectedItem();
        if(tlvm != null && tlvm.getTourLogIdValue() != null) {
            String tourLogId = tlvm.getTourLogIdValue();
            mainViewModel.deleteTourLog(tourLogId);
        }
    }

    public void updateSelectedTourLog(ActionEvent actionEvent) {
        TourLogViewModel tlvm = currTourLogs.getSelectionModel().getSelectedItem();
        if(tlvm != null) {
            Node newNode = (Node) actionEvent.getSource();
            Stage newStage = (Stage) newNode.getScene().getWindow();

            UpdateTourLogController.updateTourLogWindow(newStage, tlvm);
        }
    }

    public void importToursClicked(ActionEvent actionEvent) {
    }

    public void exportToursClicked(ActionEvent actionEvent) {
    }

    public void openVideoWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("video-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Video");
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}