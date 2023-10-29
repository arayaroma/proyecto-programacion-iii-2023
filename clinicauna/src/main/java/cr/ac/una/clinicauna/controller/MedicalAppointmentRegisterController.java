package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.AgendaDto;
import cr.ac.una.clinicauna.model.DoctorDto;
import cr.ac.una.clinicauna.model.MedicalAppointmentDto;
import cr.ac.una.clinicauna.model.PatientDto;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.AgendaService;
import cr.ac.una.clinicauna.services.MedicalAppointmentService;
import cr.ac.una.clinicauna.services.PatientService;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.util.StringConverter;

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
    private ComboBox<PatientDto> cbIdentification;
    @FXML
    private ComboBox<String> cbHoursAvailable;
    @FXML
    private StackPane parent;
    @FXML
    private ToggleGroup rbGroup;
    @FXML
    private DatePicker dpAppoinmentDate;
    @FXML
    private Spinner<Integer> spSlots;

    private Data data = Data.getInstance();

    private PatientService patientService = new PatientService();
    private AgendaService agendaService = new AgendaService();
    private MedicalAppointmentService medicalAppointmentService = new MedicalAppointmentService();
    private List<PatientDto> patients = new ArrayList();
    private Map<String, AgendaDto> agendaDtos = new HashMap<>();
    private List<String> allHours = new ArrayList<>();
    private MedicalAppointmentDto medicalAppointmentBuffer;
    private PatientDto patientBuffer;
    private AgendaDto agendaBuffer = new AgendaDto();
    private DoctorDto doctorBuffer;
    private String fechaAppointment;
    private UserDto scheduledBy;

    private boolean isEditing;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeSpinners();
            initializeComboBox();
            agendaBuffer = (AgendaDto) data.getData("agendaBuffer");
            scheduledBy = (UserDto) data.getData("scheduledBy");
            fechaAppointment = (String) data.getData("fechaAppointment");
            doctorBuffer = (DoctorDto) data.getData("doctorBuffer");
            String hourAppointment = (String) data.getData("hourAppointment");
            patientBuffer = (PatientDto) data.getData("patientBuffer");//Este buffer viene de la pantalla de registro de paciente en caso de no existir el paciente
            medicalAppointmentBuffer = (MedicalAppointmentDto) data.getData("medicalAppointmentBuffer");
            agendaBuffer = agendaBuffer == null ? new AgendaDto() : agendaBuffer;
            medicalAppointmentBuffer = medicalAppointmentBuffer == null ? new MedicalAppointmentDto() : medicalAppointmentBuffer;
            if (medicalAppointmentBuffer.getId() == null) {
                medicalAppointmentBuffer.setScheduledDate(fechaAppointment);
                medicalAppointmentBuffer.setScheduledStartTime(hourAppointment);
            } else {
                patientBuffer = medicalAppointmentBuffer.getPatient();
            }
            isEditing = medicalAppointmentBuffer.getId() != null;
            loadAgendas(doctorBuffer);
            addPatientsInCb();
            bindMedicalAppointment();
            loadHoursInComboBox();
        } catch (Exception e) {
            System.out.println(e.toString());
            backAction(null);
        }
    }

    @FXML
    private void backAction(MouseEvent event) {
        try {
            FXMLLoader loader = App.getFXMLLoader("Main");
            Animation.MakeDefaultFadeTransition(mainView, loader.load());
            MainController controller = loader.getController();
            if (controller != null) {
                controller.loadView("agendaModule");
            }
        } catch (IOException e) {
        }

    }

    @FXML
    private void btnCreateMedicalAppointment(ActionEvent event) {
        if (!verifyFields()) {
            Message.showNotification("Ups", MessageType.INFO, "fieldsEmpty");
            return;
        }
        boolean isAgendaCreated = agendaBuffer == null ? createAgenda(dpAppoinmentDate.getValue().toString()) : true;
        if (isAgendaCreated) {
            medicalAppointmentBuffer.setAgenda(agendaBuffer);
            medicalAppointmentBuffer.setPatient(patientBuffer);
            medicalAppointmentBuffer.setScheduledBy(scheduledBy);
            medicalAppointmentBuffer.setSlotsNumber((long) spSlots.getValue());
            medicalAppointmentBuffer.setScheduledDate(agendaBuffer.getAgendaDate());
            String hour = cbHoursAvailable.getValue();
            medicalAppointmentBuffer.setScheduledStartTime(hour);
            medicalAppointmentBuffer.setScheduledEndTime(getEndTime(hour, agendaBuffer.getHourlySlots(), spSlots.getValue()));
            if (!medicalAppointmentBuffer.getPatientEmail().equals(patientBuffer.getEmail()) || !medicalAppointmentBuffer.getPatientPhoneNumber().equals(patientBuffer.getPhoneNumber())) {
                patientService.updatePatient(patientBuffer);
            }
            saveMedicalAppointment(medicalAppointmentBuffer);
        }
    }

    private void addPatientsInCb() {
        patients = (List<PatientDto>) patientService.getPatients().getData();
        cbIdentification.getItems().addAll(patients);
    }

    @FXML
    private void createPatient(ActionEvent event) throws IOException {
        data.setData("isFromAppointmentRegister", true);
        Animation.MakeDefaultFadeTransition(parent, App.getFXMLLoader("PatientRegister").load());
    }

    @FXML
    private void searchById(KeyEvent event) {
        String idToSearch = cbIdentification.getEditor().getText();
        if (idToSearch != null) {
            cbIdentification.show();
            cbIdentification.getItems().clear();
            if (!idToSearch.isEmpty()) {
                cbIdentification.getItems().addAll(patients.stream().filter(t -> t.getIdentification().contains(idToSearch)).collect(Collectors.toList()));
                return;
            }
            cbIdentification.getItems().addAll(patients);
            cbIdentification.show();
        }

    }

    @FXML
    private void cbSelectPatient(ActionEvent event) {
        PatientDto patient = cbIdentification.getValue();
        if (patient != null) {
            patientBuffer = patient;
            medicalAppointmentBuffer.setPatientEmail(patientBuffer.getEmail());
            medicalAppointmentBuffer.setPatientPhoneNumber(patientBuffer.getPhoneNumber());
        }
    }

    @FXML
    private void setSlotsAvailable(MouseEvent event) {
        loadHoursInComboBox();
    }

    @FXML
    private void dpAppoinmentChange(ActionEvent event) {
        loadHoursInComboBox();
    }

    private void loadHoursInComboBox() {
        if (dpAppoinmentDate.getValue() != null) {
            agendaBuffer = agendaDtos.get(dpAppoinmentDate.getValue().toString());
            if (agendaBuffer == null) {
                addAllHoursInCb(getAvailableHoursForAppointment(allHours,
                        doctorBuffer.getHourlySlots(), spSlots.getValue(), new ArrayList()));
            } else {
                addAllHoursInCb(getAvailableHoursForAppointment(allHours,
                        agendaBuffer.getHourlySlots(), spSlots.getValue(), agendaBuffer.getMedicalAppointments()));
            }
        }
    }

    private void initializeComboBox() {
        cbIdentification.setCellFactory(param -> new ListCell<PatientDto>() {
            @Override
            protected void updateItem(PatientDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getIdentification());
                }
            }
        });

        cbIdentification.setConverter(new StringConverter<PatientDto>() {
            @Override
            public String toString(PatientDto user) {
                return user == null ? null : user.getIdentification();
            }

            @Override
            public PatientDto fromString(String string) {
                return null;
            }
        });
        cbIdentification.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Paciente seleccionado: " + newValue);
            }
        });
    }

    public void bindMedicalAppointment() {
        if (patientBuffer != null) {
            txfEmail.textProperty().bindBidirectional(patientBuffer.email);
            txfPhoneNumber.textProperty().bindBidirectional(patientBuffer.phoneNumber);
        }
        txfReason.textProperty().bindBidirectional(medicalAppointmentBuffer.reason);
        txfEmail.textProperty().bindBidirectional(medicalAppointmentBuffer.patientEmail);
        txfPhoneNumber.textProperty().bindBidirectional(medicalAppointmentBuffer.patientPhoneNumber);
        dpAppoinmentDate.valueProperty().bindBidirectional(medicalAppointmentBuffer.scheduledDate);
        cbHoursAvailable.valueProperty().bindBidirectional(medicalAppointmentBuffer.scheduledStartTime);
        if (medicalAppointmentBuffer.getSlotsNumber() != null) {
            Long slotsNumber = medicalAppointmentBuffer.getSlotsNumber();
            spSlots.getEditor().setText(slotsNumber.toString());
            spSlots.setEditable(true);
            spSlots.commitValue();
            spSlots.setEditable(false);
        }
        if (medicalAppointmentBuffer.getPatient() != null) {
            cbIdentification.setValue(medicalAppointmentBuffer.getPatient());
        }
        rbGroup.selectedToggleProperty()
                .addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
                    if (newValue != null && medicalAppointmentBuffer != null) {
                        String prueba = ((RadioButton) newValue).getText();
                        medicalAppointmentBuffer.setState(medicalAppointmentBuffer.parseState(((RadioButton) newValue).getText().toLowerCase()));//no sirve
                    }
                });

        if (isEditing) {
            rbGroup.getToggles().forEach(t -> {
                if (t instanceof RadioButton) {
                    if (medicalAppointmentBuffer.getState().toLowerCase().equals("attended")) {
                        if (((RadioButton) t).getText().toLowerCase().equals("attended")
                                || ((RadioButton) t).getText().toLowerCase().equals("atendida")) {
                            t.setSelected(true);
                        }
                    } else if (medicalAppointmentBuffer.getState().toLowerCase().equals("cancelled")) {
                        if (((RadioButton) t).getText().toLowerCase().equals("cancelled")
                                || ((RadioButton) t).getText().toLowerCase().equals("cancelada")) {
                            t.setSelected(true);
                        }
                    } else if (medicalAppointmentBuffer.getState().toLowerCase().equals("scheduled")) {
                        if (((RadioButton) t).getText().toLowerCase().equals("scheduled")
                                || ((RadioButton) t).getText().toLowerCase().equals("agendada")) {
                            t.setSelected(true);
                        }
                    }
                }
            });
        }
    }

    public void initializeSpinners() {
        spSlots.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
        spSlots.valueProperty().addListener((observable, oldValue, newValue) -> {
            setSlotsAvailable(null);
        });
        StringConverter<Integer> formatter = new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return String.format("%1d", value);
            }

            @Override
            public Integer fromString(String text) {
                try {
                    return Integer.valueOf(text);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        };
        spSlots.getValueFactory().setConverter(formatter);
    }

    private boolean verifyFields() {
        List<Node> fields = Arrays.asList(txfEmail, txfPhoneNumber, txfReason, spSlots, cbHoursAvailable);
        for (Node i : fields) {
            if (i instanceof JFXTextField && ((JFXTextField) i).getText() != null
                    && ((JFXTextField) i).getText().isBlank() && i instanceof JFXTextArea && ((JFXTextArea) i).getText() != null
                    && ((JFXTextArea) i).getText().isBlank()
                    && i instanceof Spinner && ((Spinner) i).getValue() != null
                    && i instanceof ComboBox && ((ComboBox) i).getValue() != null) {
                return false;
            }
        }
        return patientBuffer != null;
    }

    public boolean createAgenda(String fechaAppointment) {
        agendaBuffer = new AgendaDto();
        agendaBuffer.setDoctor(doctorBuffer);
        agendaBuffer.setAgendaDate(fechaAppointment);
        agendaBuffer.setHourlySlots(doctorBuffer.getHourlySlots());
        agendaBuffer.setShiftStartTime(doctorBuffer.getShiftStartTime());
        agendaBuffer.setShiftEndTime(doctorBuffer.getShiftEndTime());
        return saveAgenda(agendaBuffer);
    }

    public boolean saveAgenda(AgendaDto agendaDto) {
        ResponseWrapper response = agendaService.createAgenda(agendaDto);
        if (response.getCode() == ResponseCode.OK) {
            agendaBuffer = (AgendaDto) response.getData();
            return true;
        }
        Message.showNotification("Ups", MessageType.ERROR, response.getMessage());
        return false;
    }

    public boolean saveMedicalAppointment(MedicalAppointmentDto medicalAppointment) {
        ResponseWrapper response = isEditing ? medicalAppointmentService.updateMedicalAppointments(medicalAppointment)
                : medicalAppointmentService.createMedicalAppointments(medicalAppointment);
        if (response.getCode() == ResponseCode.OK) {
            Message.showNotification("Success", MessageType.CONFIRMATION, response.getMessage());
            medicalAppointmentBuffer = (MedicalAppointmentDto) response.getData();
            backAction(null);
            return true;
        }
        Message.showNotification("Ups", MessageType.ERROR, response.getMessage());
        System.out.println(response.getMessage());
        return false;
    }

    private List<String> getHours(String startTime, String endTime, Long fieldsPerHour) {
        List<String> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            startTime = (startTime.charAt(1) == ':') ? "0" + startTime : startTime;
            endTime = (endTime.charAt(1) == ':') ? "0" + endTime : endTime;

            LocalTime start = LocalTime.parse(startTime, formatter);
            LocalTime end = LocalTime.parse(endTime, formatter);
            long intervalMinutes = TimeUnit.HOURS.toMinutes(1) / fieldsPerHour;

            while (!start.isAfter(end)) {
                result.add(start.format(formatter));
                start = start.plusMinutes(intervalMinutes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean checkOverlap(String startTime, String endTime, MedicalAppointmentDto mA) {
        LocalTime newStartTimeAppointment = LocalTime.parse(startTime);
        LocalTime newEndTimeAppointment = LocalTime.parse(endTime);
        String end = doctorBuffer.getShiftEndTime();
        end = (end.charAt(1) == ':') ? "0" + end : end;
        if (mA != null) {
            String s = mA.getScheduledStartTime();
            String e = mA.getScheduledEndTime();
            s = (s.charAt(1) == ':') ? "0" + s : s;
            e = (e.charAt(1) == ':') ? "0" + e : e;
            LocalTime startExist = LocalTime.parse(s);
            LocalTime endExist = LocalTime.parse(e);
            if ((newStartTimeAppointment.isBefore(endExist) && newEndTimeAppointment.isAfter(startExist))
                    || (newEndTimeAppointment.equals(startExist))
                    || (newStartTimeAppointment.isBefore(startExist) && newEndTimeAppointment.isAfter(LocalTime.parse(end)))) {
                return true;
            }
        }
        return newEndTimeAppointment.isAfter(LocalTime.parse(end));
    }

    private String getEndTime(String startTime, Long slots, int medicalAppointmentSlots) {
        long intervalMillis = TimeUnit.HOURS.toMillis(1) / slots;
        long intervalMinutes = TimeUnit.MILLISECONDS.toMinutes(intervalMillis);
        LocalTime horaInicioLocal = LocalTime.parse(startTime);
        LocalTime horaFin = horaInicioLocal.plusMinutes(intervalMinutes * medicalAppointmentSlots);
        return horaFin.toString();
    }

    private List<String> getAvailableHoursForAppointment(List<String> horasDisponibles, Long slots, int nAppSlots, List<MedicalAppointmentDto> appointments) {
        List<String> hoursAvailable = new ArrayList<>();
        if (!appointments.isEmpty()) {
            for (String hour : horasDisponibles) {
                boolean disponible = true;

                for (MedicalAppointmentDto mA : appointments) {
                    if (checkOverlap(hour, getEndTime(hour, slots, nAppSlots), mA)) {
                        disponible = false;
                    }
                }

                if (disponible) {
                    hoursAvailable.add(hour);
                }
            }
        } else {
            for (String hour : horasDisponibles) {
                boolean disponible = true;

                if (checkOverlap(hour, getEndTime(hour, slots, nAppSlots), null)) {
                    disponible = false;
                }

                if (disponible) {
                    hoursAvailable.add(hour);
                }
            }
        }
        return hoursAvailable;
    }

    private void loadAgendas(DoctorDto doctorDto) {
        if (doctorDto != null) {
            for (AgendaDto i : doctorDto.getAgendas()) {
                AgendaDto agenda = (AgendaDto) agendaService.getAgendaById(i.getId()).getData();
                if (agenda != null) {
                    agendaDtos.put(agenda.getAgendaDate(), agenda);
                }
            }
            allHours = getHours(doctorDto.getShiftStartTime(), doctorDto.getShiftEndTime(), doctorDto.getHourlySlots());
        }

    }

    private void addAllHoursInCb(List<String> horasDisp) {//restringir hora fin
        cbHoursAvailable.setItems(FXCollections.observableArrayList(horasDisp));
    }

}
