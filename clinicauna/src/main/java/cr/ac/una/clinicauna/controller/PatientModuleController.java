package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.PatientDto;
import cr.ac.una.clinicauna.services.PatientService;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class PatientModuleController implements Initializable {

    @FXML
    private VBox parent;
    @FXML
    private JFXTextField txfSearchPatient;
    @FXML
    private ComboBox<String> cbSearchParameter;
    @FXML
    private TableView<PatientDto> tblPatientsView;
    @FXML
    private TableColumn<PatientDto, String> tcIdentification;
    @FXML
    private TableColumn<PatientDto, String> tcName;
    @FXML
    private TableColumn<PatientDto, String> tcLastName;
    @FXML
    private TableColumn<PatientDto, String> tcPhone;
    @FXML
    private TableColumn<PatientDto, String> tcRole;
    @FXML
    private Button btnEdit;

    private PatientService patientService = new PatientService();
    private PatientDto patientBuffer;
    private List<PatientDto> patientDtos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (Data.getLanguageOption().equals("en")) {
                cbSearchParameter.getItems().addAll("Name", "Last Name", "Phone", "Identification", "Birth Date");
            } else {
                cbSearchParameter.getItems().addAll("Nombre", "Apellido", "Telefono", "Cédula", "Fecha de Nacimiento");
            }
            btnEdit.setDisable(true);
            initializeList();
            patientDtos = (List<PatientDto>) patientService.getPatients().getData();
            loadPatients(patientDtos);
            txfSearchPatient.setOnKeyPressed(e -> searchPatientAction(e));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    private void btnNewPatientAction(ActionEvent event) {
        Animation.MakeDefaultFadeTransition(parent, "PatientRegister");
    }

    @FXML
    private void btnViewPatientAction(ActionEvent event) {
        Data.setData("patientBuffer", patientBuffer);
        Animation.MakeDefaultFadeTransition(parent, "PatientHistory");
    }

    @FXML
    private void btnDeletePatientAction(ActionEvent event) {
        if (patientBuffer != null) {
            ResponseWrapper response = patientService.deletePatient(patientBuffer);
            if (response.getCode() == ResponseCode.OK) {
                tblPatientsView.getItems().remove(patientBuffer);
            } else {
                Message.showNotification("Error", MessageType.ERROR, response.getMessage());
            }
        }
    }

    private void searchPatientAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String key = txfSearchPatient.getText(), parameterKey = cbSearchParameter.getValue();
            if (key.isBlank() || parameterKey == null) {
                loadPatients(patientDtos);
                return;
            }
            loadPatients(filterPatients(patientDtos, parameterKey, key));
        }
    }

    private List<PatientDto> filterPatients(List<PatientDto> patients, String parameter, String key) {
        List<PatientDto> patientsFiltered = new ArrayList<>();
        if (patients != null) {
            if (parameter.equals("Name") || parameter.equals("Nombre")) {
                patientsFiltered = patients
                        .stream()
                        .filter(user -> user.getName().toLowerCase().contains(key.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (parameter.equals("Last Name") || parameter.equals("Apellido")) {
                patientsFiltered = patients
                        .stream()
                        .filter(user -> user.getFirstLastname().toLowerCase().contains(key.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (parameter.equals("Phone") || parameter.equals("Telefono")) {
                patientsFiltered = patients
                        .stream()
                        .filter(user -> user.getSecondLastname().toLowerCase().contains(key.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (parameter.equals("Identification") || parameter.equals("Cédula")) {
                patientsFiltered = patients
                        .stream()
                        .filter(user -> user.getIdentification().contains(key))
                        .collect(Collectors.toList());
            } else if (parameter.equals("Birth Date") || parameter.equals("Fecha de Nacimiento")) {
                patientsFiltered = patients
                        .stream()
                        .filter(user -> user.getBirthDate().toLowerCase().contains(key.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        return patientsFiltered;
    }

    private void initializeList() {
        tcIdentification.setCellValueFactory(new PropertyValueFactory<>("identification"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("firstLastname"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tcRole.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tblPatientsView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    patientBuffer = newValue;
                    if (patientBuffer != null) {
                        btnEdit.setDisable(false);
                        return;
                    }
                    btnEdit.setDisable(true);

                });
    }

    private void loadPatients(List<PatientDto> patients) {
        tblPatientsView.setItems(FXCollections.observableArrayList(patients));
    }

}
