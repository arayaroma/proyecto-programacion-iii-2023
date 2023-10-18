package cr.ac.una.clinicauna.controller;

import cr.ac.una.clinicauna.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class ReportModuleController implements Initializable {

    @FXML
    private VBox parent;
    @FXML
    private Tab tabDoctorReport;
    @FXML
    private Tab tabPatientReport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void doctorReportAction(Event event) {
        try {
            FXMLLoader loader = App.getFXMLLoader("DoctorReport");
            tabDoctorReport.setContent(loader.load());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    private void patientReportAction(Event event) {
        try {
            FXMLLoader loader = App.getFXMLLoader("PatientCareReport");
            tabPatientReport.setContent(loader.load());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

}
