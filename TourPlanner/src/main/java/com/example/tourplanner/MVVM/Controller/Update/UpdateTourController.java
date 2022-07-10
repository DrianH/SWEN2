package com.example.tourplanner.MVVM.Controller.Update;

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

public class UpdateTourController implements Initializable {
    private TourViewModel viewModel;

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
        tourNameField.textProperty().bindBidirectional(viewModel.nameProperty());
        tourFromField.textProperty().bindBidirectional(viewModel.fromProperty());
        tourToField.textProperty().bindBidirectional(viewModel.toProperty());
        ObservableList<String> transportTypes = FXCollections.observableArrayList();
        transportTypes.add("CAR");
        transportTypes.add("BUS");
        transportTypes.add("WALKING");
        transportTypes.add("BICYCLE");
        tourTransportMenu.setItems(transportTypes);
        tourTransportMenu.valueProperty().bindBidirectional(viewModel.transportTypeProperty());

        tourDescField.textProperty().bindBidirectional(viewModel.descriptionProperty());
    }


    public TourViewModel getViewModel() {
        return viewModel;
    }
    public void setViewModel(TourViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public static void updateTourWindow(Stage stage, TourViewModel tvm) {
        try {
            UpdateTourController updateTourViewController = new UpdateTourController();
            updateTourViewController.setViewModel(tvm);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("tour-view.fxml"));
            fxmlLoader.setController(updateTourViewController);
            Parent root = fxmlLoader.load();
            Stage editTourStage = new Stage();
            editTourStage.setTitle("Edit Tour");
            Scene editTourScene = new Scene(root, 600, 400);
            editTourStage.setScene(editTourScene);
            editTourStage.setMinWidth(600);
            editTourStage.setMinHeight(400);
            editTourStage.initOwner(stage);
            editTourStage.initModality(Modality.APPLICATION_MODAL);
            editTourStage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void tourCancelButton(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void tourApplyButton(ActionEvent actionEvent) {
        if(viewModel.editTour()) {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}
