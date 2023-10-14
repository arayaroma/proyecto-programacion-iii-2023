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
import animatefx.animation.FlipInY;
import cr.ac.una.clinicauna.model.PatientFamilyHistoryDto;
import cr.ac.una.clinicauna.model.PatientPersonalHistoryDto;
import cr.ac.una.clinicauna.services.PatientService;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private LineChart<PatientFamilyHistoryDto, String> chartMassIndex;
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
    private Accordion acPatientCares;
    @FXML
    private VBox familyHistoryView;
    @FXML
    private TableView<PatientFamilyHistoryDto> tblFamilyHistory;
    @FXML
    private TableColumn<PatientFamilyHistoryDto, String> tcDisease;
    @FXML
    private TableColumn<PatientFamilyHistoryDto, String> tcRelationship;
    @FXML
    private VBox personalHistoryView;
    private PatientDto patientBuffer;
    private PatientService patientService = new PatientService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientBuffer = (PatientDto) Data.getData("patientBuffer");
        patientBuffer = (PatientDto) patientService.getPatientById(patientBuffer.getId()).getData();
        Data.setData("patientBuffer", patientBuffer);
        initializeList();
        loadAccordion();
        bindPatient();

    }

    @FXML
    private void backAction(MouseEvent event) {
        Data.removeData("patientBuffer");
        Animation.MakeDefaultFadeTransition(mainView, "Main");
    }

    @FXML
    private void btnNewHistoryAction(ActionEvent event) {
        //Data.setData("patientCareBuffer", null);
        Data.setData("patientPersonalHistoryBuffer", patientBuffer.getPatientPersonalHistory());
        Animation.MakeDefaultFadeTransition(mainView, "PatientCareRegister");
    }

    @FXML
    private void editPatientAction(MouseEvent event) {
        Animation.MakeDefaultFadeTransition(mainView, "PatientRegister");
    }

    @FXML
    private void editPersonalHistoryAction(MouseEvent event) {
        Animation.MakeDefaultFadeTransition(mainView, "PatientPersonalHistoryRegister");
    }

    @FXML
    private void showPersonalHistoryView(MouseEvent event) {
        new FlipInY(personalHistoryView).play();
        personalHistoryView.toFront();
    }

    @FXML
    private void editFamilyHistoryAction(MouseEvent event) {
        Animation.MakeDefaultFadeTransition(mainView, "PatientFamilyHistoryRegister");

    }

    @FXML
    private void showFamilyHistory(MouseEvent event) {
        new FlipInY(familyHistoryView).play();
        familyHistoryView.toFront();
    }

    private void loadAccordion() {
        acPatientCares.getPanes().add(new TitledPane());
        acPatientCares.getPanes().add(new TitledPane());
        acPatientCares.getPanes().add(new TitledPane());
        acPatientCares.getPanes().add(new TitledPane());
        acPatientCares.getPanes().add(new TitledPane());
        acPatientCares.getPanes().add(new TitledPane());
        acPatientCares.getPanes().add(new TitledPane());
        acPatientCares.getPanes().add(new TitledPane());
    }

    private void initializeList() {
        tcDisease.setCellValueFactory(new PropertyValueFactory<>("disease"));
        tcRelationship.setCellValueFactory(new PropertyValueFactory<>("relationship"));
    }

    private void bindPatient() {
        lblFullName.setText(patientBuffer.getName() + " " + patientBuffer.getFirstLastname() + " " + patientBuffer.getSecondLastname());
        lblGender.textProperty().bindBidirectional(patientBuffer.gender);
        lblIdentification.textProperty().bindBidirectional(patientBuffer.identification);
        lblPhoneNumber.textProperty().bindBidirectional(patientBuffer.phoneNumber);
        lblBirthDate.setText(patientBuffer.getBirthDate());
        lblGender.textProperty().bindBidirectional(patientBuffer.gender);
        PatientPersonalHistoryDto patientPersonalHistoryDto = patientBuffer.getPatientPersonalHistory();
        if (patientPersonalHistoryDto != null) {
            lblAlergies.textProperty().bindBidirectional(patientPersonalHistoryDto.allergies);
            lblHospitalizations.textProperty().bindBidirectional(patientPersonalHistoryDto.hospitalizations);
            lblPathological.textProperty().bindBidirectional(patientPersonalHistoryDto.pathological);
            lblTreatments.textProperty().bindBidirectional(patientPersonalHistoryDto.treatments);
            lblSurgical.textProperty().bindBidirectional(patientPersonalHistoryDto.surgical);
        }
        List<PatientFamilyHistoryDto> patientFamilyHistoryDtos = patientBuffer.getPatientFamilyHistories();
        tblFamilyHistory.setItems(FXCollections.observableArrayList(patientFamilyHistoryDtos));
    }

}
