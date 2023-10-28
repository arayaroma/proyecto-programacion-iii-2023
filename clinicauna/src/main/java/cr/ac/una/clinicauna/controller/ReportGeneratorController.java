package cr.ac.una.clinicauna.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ReportGeneratorController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private VBox vbReportContainer;
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
    private GridPane gpQueryResult;
    @FXML
    private JFXDatePicker dpReportDate;
    @FXML
    private JFXComboBox<String> cbReportFrequency;
    @FXML
    private Label lbRecipientName;
    @FXML
    private JFXTextField tfRecipientEmail;
    @FXML
    private JFXButton btGenerateReport;

    private final String[] frequencies = { "DAILY", "WEEKLY", "MONTHLY", "ANNUALLY" };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFrequencies();
    }

    private void loadFrequencies() {
        cbReportFrequency.getItems().addAll(frequencies);
    }

}
