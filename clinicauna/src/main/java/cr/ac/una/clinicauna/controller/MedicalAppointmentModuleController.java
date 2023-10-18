package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.model.DoctorDto;
import cr.ac.una.clinicauna.model.MedicalAppointmentDto;
import cr.ac.una.clinicauna.model.PatientDto;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.MedicalAppointmentService;
import cr.ac.una.clinicauna.util.Data;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
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
public class MedicalAppointmentModuleController implements Initializable {

    @FXML
    private VBox parent;
    @FXML
    private JFXTextField txfSearchMedicalAppointment;
    @FXML
    private ComboBox<String> cbSearchParameter;
    @FXML
    private TableView<MedicalAppointmentDto> tblMedicalAppointmentsView;
    @FXML
    private TableColumn<MedicalAppointmentDto, String> tcDate;
    @FXML
    private TableColumn<MedicalAppointmentDto, String> tcHour;
    @FXML
    private TableColumn<MedicalAppointmentDto, String> tcDoctor;
    @FXML
    private TableColumn<MedicalAppointmentDto, String> tcPatient;
    @FXML
    private TableColumn<MedicalAppointmentDto, String> tcState;
    @FXML
    private Button btnEdit;
    private List<MedicalAppointmentDto> medicalAppointmentDtos = new ArrayList<>();
    private MedicalAppointmentService medicalAppointmentService = new MedicalAppointmentService();
    private MedicalAppointmentDto medicalAppointmentBuffer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Data.getLanguageOption().equals("en")) {
            cbSearchParameter.getItems().addAll("Date", "Hour", "Doctor", "Patient", "State");
        } else {
            cbSearchParameter.getItems().addAll("Fecha", "Hora", "Doctor", "Paciente", "Estado");
        }
        btnEdit.setDisable(true);
        initializeList();
        medicalAppointmentDtos = (List<MedicalAppointmentDto>) medicalAppointmentService.getMedicalAppointments().getData();
        loadMedicalAppointments(medicalAppointmentDtos);
    }

    @FXML
    private void btnNewMedicalAppointmentAction(ActionEvent event) {

    }

    @FXML
    private void btnEditMedicalAppointmentAction(ActionEvent event) {
    }

    @FXML
    private void btnDeleteMedicalAppointmentAction(ActionEvent event) {
        if (medicalAppointmentBuffer != null) {
            //delete
        }
    }

    @FXML
    private void seachMedicalAppointmentKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String key = txfSearchMedicalAppointment.getText(), parameterKey = cbSearchParameter.getValue();
            if (key.isBlank() || parameterKey == null) {
                loadMedicalAppointments(medicalAppointmentDtos);
                return;
            }
            loadMedicalAppointments(filterMedicalAppointments(medicalAppointmentDtos, parameterKey, key));
        }
    }

    private List<MedicalAppointmentDto> filterMedicalAppointments(List<MedicalAppointmentDto> medicalAppointments, String parameter, String key) {
        List<MedicalAppointmentDto> medicalAppointmentDtosFiltered = new ArrayList<>();
        if (medicalAppointments != null) {
            parameter = parameter.toLowerCase();
            if (parameter.equals("date") || parameter.equals("fecha")) {
                medicalAppointmentDtosFiltered = medicalAppointments
                        .stream()
                        .filter(medicalAppointment -> medicalAppointment.getScheduledDate().toLowerCase().contains(key))
                        .collect(Collectors.toList());
            } else if (parameter.equals("hora") || parameter.equals("hour")) {
                medicalAppointmentDtosFiltered = medicalAppointments
                        .stream()
                        .filter(medicalAppointment -> medicalAppointment.getScheduledTime().toLowerCase().contains(key))
                        .collect(Collectors.toList());
            } else if (parameter.equals("doctor")) {
//                medicalAppointmentDtosFiltered = medicalAppointments
//                        .stream()
//                        .filter(medicalAppointment -> medicalAppointment.getAgenda()..toLowerCase().contains(key))
//                        .collect(Collectors.toList());
            } else if (parameter.equals("patient") || parameter.equals("paciente")) {
//                medicalAppointmentDtosFiltered = medicalAppointments
//                        .stream()
//                        .filter(medicalAppointment -> medicalAppointment.getp.toLowerCase().contains(key))
//                        .collect(Collectors.toList());
            } else if (parameter.equals("state") || parameter.equals("estado")) {
                medicalAppointmentDtosFiltered = medicalAppointments
                        .stream()
                        .filter(medicalAppointment -> medicalAppointment.getState().toLowerCase().contains(key))
                        .collect(Collectors.toList());
            }
        }
        return medicalAppointmentDtosFiltered;
    }

    private void loadMedicalAppointments(List<MedicalAppointmentDto> medicalAppointmentDtos) {
        if (medicalAppointmentDtos != null) {
            tblMedicalAppointmentsView.setItems(FXCollections.observableArrayList(medicalAppointmentDtos));
        }
    }

    private void initializeList() {
        tcDate.setCellValueFactory(new PropertyValueFactory<>("scheduledDate"));
        tcDoctor.setCellValueFactory(cellData -> {
            MedicalAppointmentDto medicalAppointmentDto = cellData.getValue();
//            DoctorDto doctor = medicalAppointmentDto.getAgenda().getDoctor();
//            if (patient != null) {
//                String name = patient.getName() + " " + patient.getFirstLastname() + " " + patient.getSecondLastname();
//                return new SimpleStringProperty(name);
//            }
            return new SimpleStringProperty("-");
        });
        tcHour.setCellValueFactory(new PropertyValueFactory<>("scheduledTime"));
        tcPatient.setCellValueFactory(cellData -> {
            MedicalAppointmentDto medicalAppointmentDto = cellData.getValue();
            PatientDto patient = medicalAppointmentDto.getPatient();
            if (patient != null) {
                String name = patient.getName() + " " + patient.getFirstLastname() + " " + patient.getSecondLastname();
                return new SimpleStringProperty(name);
            }
            return new SimpleStringProperty("-");
        });
        tblMedicalAppointmentsView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    medicalAppointmentBuffer = newValue;
//                    if (doctorBuffer != null) {
//                        btnEdit.setDisable(false);
//                        return;
//                    }
//                    btnEdit.setDisable(true);

                });
    }

}
