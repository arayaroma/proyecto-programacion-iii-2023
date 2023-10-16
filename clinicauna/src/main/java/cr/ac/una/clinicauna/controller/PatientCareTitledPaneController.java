package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextArea;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.model.PatientCareDto;
import cr.ac.una.clinicauna.model.PatientPersonalHistoryDto;
import cr.ac.una.clinicauna.util.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class PatientCareTitledPaneController implements Initializable {

    @FXML
    private VBox mainView;
    @FXML
    private TextField txfBloodPressure;
    @FXML
    private TextField txfHeartRate;
    @FXML
    private TextField txfTemperature;
    @FXML
    private TextField txfHeight;
    @FXML
    private TextField txfWeight;
    @FXML
    private JFXTextArea txfCarePlan;
    @FXML
    private JFXTextArea txfObservations;
    @FXML
    private JFXTextArea txfPhysicalExam;
    @FXML
    private JFXTextArea txfTreatment;
    @FXML
    private Label lblIMC;
    @FXML
    private Button btnViewMedicalAppointment;
    private PatientCareDto patientCareBuffer;
    private PatientPersonalHistoryDto patientPersonalHistoryBuffer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void btnEditPatientCareAction(ActionEvent event) throws IOException {
        Data.setData("patientPersonalHistoryBuffer", patientPersonalHistoryBuffer);
        Data.setData("patientCareBuffer", patientCareBuffer);
        App.setRoot("PatientCareRegister");
    }

    @FXML
    private void btnViewMedicalAppointmentAction(ActionEvent event) {
    }

    public void setData(PatientCareDto patientCareDto, PatientPersonalHistoryDto personalHistoryDto) {
        patientCareBuffer = patientCareDto;
        patientPersonalHistoryBuffer = personalHistoryDto;
        bindPatientCare();
        btnViewMedicalAppointment.setVisible(false);
    }

    public void bindPatientCare() {
        if (patientCareBuffer != null) {
            txfBloodPressure.textProperty().bindBidirectional(patientCareBuffer.bloodPressure);
            txfHeartRate.textProperty().bindBidirectional(patientCareBuffer.heartRate);
            txfHeight.textProperty().bindBidirectional(patientCareBuffer.height);
            txfTemperature.textProperty().bindBidirectional(patientCareBuffer.temperature);
            txfWeight.textProperty().bindBidirectional(patientCareBuffer.weight);
            txfObservations.textProperty().bindBidirectional(patientCareBuffer.observations);
            txfPhysicalExam.textProperty().bindBidirectional(patientCareBuffer.physicalExam);
            txfCarePlan.textProperty().bindBidirectional(patientCareBuffer.carePlan);
            txfTreatment.textProperty().bindBidirectional(patientCareBuffer.treatment);
            lblIMC.setText(patientCareBuffer.getBodyMassIndex());
        }
    }

}
