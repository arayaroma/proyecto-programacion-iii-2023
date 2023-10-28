package cr.ac.una.clinicauna.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ReportGeneratorController implements Initializable {

    @FXML
    private Label lbReportName;
    @FXML
    private JFXTextField tfReportName;
    @FXML
    private Label lbReportDescription;
    @FXML
    private JFXTextField tfReportDescription;
    @FXML
    private Label lbReportQuery;
    @FXML
    private JFXTextField tfReportQuery;
    @FXML
    private DatePicker dpReportDate;
    @FXML
    private ComboBox<String> cbReportFrequency;

    private final String[] frequencies = {"DAILY", "WEEKLY", "MONTHLY", "ANNUALLY"};
    @FXML
    private Label lbRecipientEmail;
    @FXML
    private JFXTextField tfRecipientName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFrequencies();
    }

    private void loadFrequencies() {
        cbReportFrequency.getItems().addAll(frequencies);
    }

}
