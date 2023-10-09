package cr.ac.una.clinicauna.controller;

import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.PatientDto;
import cr.ac.una.clinicauna.util.Data;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import animatefx.animation.FlipOutY;
import animatefx.animation.FlipInY;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class PatientHistoryController implements Initializable {

    @FXML
    private Label lblIdentification;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblBirthDate;
    @FXML
    private Label lblPhoneNumber;
    @FXML
    private Label lblGender;
    @FXML
    private LineChart<?, ?> chartMassIndex;
    @FXML
    private HBox parent;
    @FXML
    private VBox mainView;

    @FXML
    private Label lblAlergies;
    @FXML
    private Label lblTreatments;
    @FXML
    private Label lblPathological;
    @FXML
    private Label lblHospitalizations;
    @FXML
    private Label lblSurgical;
    @FXML
    private Accordion acMedicalAppoimentHistory;

    private PatientDto patientBuffer;
    @FXML
    private VBox familyHistoryView;
    @FXML
    private TableView<?> tblFamilyHistory;
    @FXML
    private TableColumn<?, ?> tcDisease;
    @FXML
    private TableColumn<?, ?> tcRelationship;
    @FXML
    private VBox personalHistoryView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientBuffer = (PatientDto) Data.getData("patientBuffer");
        bindPatient();
    }

    @FXML
    private void backAction(MouseEvent event) {
        Data.removeData("patientBuffer");
        Animation.MakeDefaultFadeTransition(mainView, "Main");
    }

    @FXML
    private void btnNewHistoryAction(ActionEvent event) {
    }

    @FXML
    private void editPatientAction(MouseEvent event) {
        Animation.MakeDefaultFadeTransition(mainView, "PatientRegister");
    }

    private void bindPatient() {
        lblFullName.setText(patientBuffer.getName() + " " + patientBuffer.getFirstLastname() + " " + patientBuffer.getSecondLastname());
        lblGender.textProperty().bindBidirectional(patientBuffer.gender);
        lblIdentification.textProperty().bindBidirectional(patientBuffer.identification);
        lblPhoneNumber.textProperty().bindBidirectional(patientBuffer.phoneNumber);
        lblBirthDate.setText(patientBuffer.getBirthDate());
        lblGender.textProperty().bindBidirectional(patientBuffer.gender);
    }

    @FXML
    private void editPersonalHistoryAction(MouseEvent event) {
    }

    @FXML
    private void showPersonalHistoryView(MouseEvent event) {
        new FlipInY(personalHistoryView).play();
        personalHistoryView.toFront();
    }

    @FXML
    private void editFamilyHistoryAction(MouseEvent event) {

    }

    @FXML
    private void showFamilyHistory(MouseEvent event) {
        new FlipInY(familyHistoryView).play();
        familyHistoryView.toFront();
    }
}
