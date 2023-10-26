/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.AgendaDto;
import cr.ac.una.clinicauna.model.DoctorDto;
import cr.ac.una.clinicauna.model.MedicalAppointmentDto;
import cr.ac.una.clinicauna.model.PatientDto;
import cr.ac.una.clinicauna.model.SlotsDto;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.AgendaService;
import cr.ac.una.clinicauna.services.PatientService;
import cr.ac.una.clinicauna.services.SlotsService;
import cr.ac.una.clinicauna.util.Data;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vargas
 */
public class MedicalAppointmentRegisterController implements Initializable {

    @FXML
    private VBox mainView;
    @FXML
    private ImageView imgPhotoProfile;
    @FXML
    private Button createPatient;
    @FXML
    private JFXTextField txfEmail;
    @FXML
    private JFXTextField txfPhoneNumber;
    @FXML
    private JFXTextArea txfReason;
    @FXML
    private ComboBox<String> cbIdentification;
    @FXML
    private DatePicker dpAppoinmentDate;
    private Spinner<Integer> spSlotsHours;
    @FXML
    private Spinner<Integer> spNSlots;
    @FXML
    private ComboBox<String> cbHoursAvailable;
    @FXML
    private StackPane parent;

    private Data data = Data.getInstance();

    private PatientService pService = new PatientService();
    private AgendaService aService = new AgendaService();
    private SlotsService sService = new SlotsService();
    private List<PatientDto> patients = new ArrayList();
    private MedicalAppointmentDto mAppointmentBuffer;
    private PatientDto patientBuffer;
    private AgendaDto agendaBuffer = new AgendaDto();
    private DoctorDto doctorBuffer;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        agendaBuffer = (AgendaDto) data.getData("agenda");
        doctorBuffer = (DoctorDto) data.getData("doctor");
//        patientBuffer = (PatientDto) data.getData("patient");
//        mAppointmentBuffer = (MedicalAppointmentDto) data.getData("medicalAppointment");
        String fechaAppointment = "2023-10-27"/*(String) data.getData("fechaAppointment")*/;
        if (agendaBuffer.getId() == null) {
            agendaBuffer = new AgendaDto();
            agendaBuffer.setDoctor(doctorBuffer);
            agendaBuffer.setAgendaDate(fechaAppointment);
            agendaBuffer.setHourlySlots(doctorBuffer.getHourlySlots());
            agendaBuffer.setShiftStartTime(doctorBuffer.getShiftStartTime());
            agendaBuffer.setShiftEndTime(doctorBuffer.getShiftEndTime());
//            agendaBuffer.setSlots(createSlots(doctorBuffer.getShiftStartTime(), doctorBuffer.getShiftEndTime(), doctorBuffer.getHourlySlots(), fechaAppointment));
            createSlots(doctorBuffer.getShiftStartTime(), doctorBuffer.getShiftEndTime(), doctorBuffer.getHourlySlots(), fechaAppointment);
        }
//        if (doctorBuffer.getId() == null) { //creo que se puede eliminar
            //No selecciona doctor
//        }
//        if (mAppointmentBuffer.getId() == null && patientBuffer.getId() == null) {
//            mAppointmentBuffer = new MedicalAppointmentDto();
//            patientBuffer = new PatientDto();
//        }
        patients = (List<PatientDto>) pService.getPatients().getData();
        addPatientsInCb(patients);
    }

    @FXML
    private void backAction(MouseEvent event) {
        //regresar a agenda
    }

    @FXML
    private void btnCreateMedicalAppointment(ActionEvent event) {
        bindMedicalAppointment();
//        MedicalAppointmentDto medAppointment = new MedicalAppointmentDto();
        if (patientBuffer == null) {
            //alerta
        } else {

        }
    }

    private void addPatientsInCb(List<PatientDto> p) {
        ObservableList<String> ids = FXCollections.observableArrayList();
        cbIdentification.getItems().addAll(mapListToObsevableStringP(patients));
    }

    private void addSlotsInCb(AgendaDto agenda) {
        ObservableList<String> slots = FXCollections.observableArrayList();
        cbHoursAvailable.getItems().addAll(mapListToObsevableStringS(agendaBuffer.getSlots()));
    }

    @FXML
    private void createPatient(ActionEvent event) throws IOException {
        Animation.MakeDefaultFadeTransition(parent, App.getFXMLLoader("UserRegister").load());
        //agregar bandera
    }

    @FXML
    private void searchById(KeyEvent event) {
        if (event.getCode().isDigitKey()) {
            String idToSearch = cbIdentification.getEditor().getText();

            if (idToSearch != null) {
                cbIdentification.getItems().clear();
                if (idToSearch.length() > 2) {
                    cbIdentification.getItems().addAll(patients.stream().filter(t -> t.getIdentification().contains(idToSearch))
                            .map(t -> t.getIdentification()).collect(Collectors.toList()));
                    return;
                }
                cbIdentification.getItems().addAll(mapListToObsevableStringP(patients));
                cbIdentification.show();
            }
        }
    }

    private ObservableList<String> mapListToObsevableStringP(List<PatientDto> p) {
        ObservableList<String> patientsId = FXCollections.observableArrayList();
        for (PatientDto pp : p) {
            patientsId.add(pp.getIdentification());
        }
        return patientsId;
    }

    private ObservableList<String> mapListToObsevableStringS(List<SlotsDto> s) {
        ObservableList<String> slotsA = FXCollections.observableArrayList();
        for (SlotsDto ss : s) {
            if (ss.getAvailable().equals("AVAILABLE")) {
                slotsA.add(ss.getTimeSlot());
            }
        }
        return slotsA;
    }

    private PatientDto getPatientByIdentification(String id) {
        return patients.stream()
                .filter(p -> p.getIdentification().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void setBuffers(AgendaDto agenda, PatientDto patient, MedicalAppointmentDto mPatient) {
        this.agendaBuffer = agenda;
        this.patientBuffer = patient;
        this.mAppointmentBuffer = mPatient;
    }

    private void setDataInAppointment(MedicalAppointmentDto medAppointment) {
        medAppointment.setPatient(patientBuffer);
        medAppointment.setAgenda(agendaBuffer);
        medAppointment.setScheduledBy((UserDto) data.getData("userLoggued"));
//        if () {
        medAppointment.setPatientEmail(txfEmail.getText());
//        }
        medAppointment.setPatientPhoneNumber(txfPhoneNumber.getText());
        medAppointment.setReason(txfReason.getText());
        medAppointment.setScheduledDate(dpAppoinmentDate.getValue().toString());
//        medAppointment.setScheduledTime();
    }

    public void bindMedicalAppointment() {
//        String bloodPressure = patientCareBuffer.getBloodPressure(), heartRate = patientCareBuffer.getHeartRate(),
//                temperature = patientCareBuffer.getTemperature(), weight = patientCareBuffer.getWeight(),
//                height = patientCareBuffer.getHeight();
//        if (bloodPressure != null) {
//            spBloodPresure.getEditor().setText(patientCareBuffer.getBloodPressure());
//        }
//        if (heartRate != null) {
//            spHeartRate.getEditor().setText(patientCareBuffer.getHeartRate());
//        }
//        if (height != null) {
//            spHeight.getEditor().setText(patientCareBuffer.getHeight());
//        }
//        if (temperature != null) {
//            spTemperature.getEditor().setText(patientCareBuffer.getTemperature());
//        }
//        if (weight != null) {
//            spWeight.getEditor().setText(patientCareBuffer.getWeight());
//        }
//        txfTreatment.textProperty().bindBidirectional(patientCareBuffer.treatment);
//        txfCarePlan.textProperty().bindBidirectional(patientCareBuffer.carePlan);
//        txfObservations.textProperty().bindBidirectional(patientCareBuffer.observations);
//        txfPhysicalExam.textProperty().bindBidirectional(patientCareBuffer.physicalExam);
//        if (patientCareBuffer.getBodyMassIndex() != null) {
//            lblBodyMassIndex.setText(patientCareBuffer.getBodyMassIndex());
//        }
    }

    private List<SlotsDto> createSlots(String startTime, String endTime, Long fieldsPerHour, String fechaAppointment) {
        List<SlotsDto> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        try {
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            long intervalMillis = TimeUnit.HOURS.toMillis(1) / fieldsPerHour;
            for (long time = start.getTime(); time < end.getTime(); time += intervalMillis) {
                Date newTime = new Date(time);
                SlotsDto slot = new SlotsDto();
                slot.setAgenda(agendaBuffer);
                slot.setAvailable("AVAILABLE");
                slot.setTimeSlot(newTime.toString());
                slot.setSlotDate(fechaAppointment);
                System.out.println("pene: "+sService.createSlot(slot).getMessage());
                result.add(slot);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean verifyFields() {
        List<Node> fields = Arrays.asList(txfEmail, txfPhoneNumber, txfReason, cbIdentification, spSlotsHours);
        for (Node i : fields) {
            if (i instanceof JFXTextField && ((JFXTextField) i).getText() != null
                    && ((JFXTextField) i).getText().isBlank() && i instanceof JFXTextArea && ((JFXTextArea) i).getText() != null
                    && ((JFXTextArea) i).getText().isBlank()
                    && i instanceof Spinner && ((Spinner) i).getValue() != null
                    && i instanceof ComboBox && ((ComboBox) i).getValue() != null) {
                return false;
            }
        }
        return true;
    }

    @FXML
    private void setSlotsAvailable(ActionEvent event) {
        List<SlotsDto> slotsAvailable = agendaBuffer.getSlots().stream()
                .filter(s -> s.getAvailable().equals("AVAILABLE")).collect(Collectors.toList());
//        int nSlots = spSlotsHours
    }

}
