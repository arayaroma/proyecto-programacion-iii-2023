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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private DatePicker dpAppoinmentDate;
    @FXML
    private Spinner<Integer> spNSlots;
    @FXML
    private ComboBox<String> cbHoursAvailable;
    @FXML
    private StackPane parent;
    @FXML
    private ToggleGroup rbGroup;
    private Data data = Data.getInstance();

    private PatientService pService = new PatientService();
    private AgendaService aService = new AgendaService();
    private MedicalAppointmentService mService = new MedicalAppointmentService();
    private List<PatientDto> patients = new ArrayList();
    private MedicalAppointmentDto medicalAppointmentBuffer;
    private PatientDto patientBuffer;
    private AgendaDto agendaBuffer = new AgendaDto();
    private DoctorDto doctorBuffer;
    private String fechaAppointment;
    private UserDto scheduledBy;
    private PatientDto pat;
    private Map<String, AgendaDto> agendaDtos = new HashMap<>();
    private boolean isEditing;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            agendaBuffer = (AgendaDto) data.getData("agendaBuffer");
            scheduledBy = (UserDto) data.getData("scheduledBy");
            if (agendaBuffer == null) {
                agendaBuffer = new AgendaDto();
            }
            medicalAppointmentBuffer = (MedicalAppointmentDto) data.getData("medicalAppointmentBuffer");

            if (medicalAppointmentBuffer == null) {
                medicalAppointmentBuffer = new MedicalAppointmentDto();
            }
            isEditing = medicalAppointmentBuffer.getId() != null;
            doctorBuffer = (DoctorDto) data.getData("doctorBuffer");
            loadAgendas(doctorBuffer);
//        patientBuffer = (PatientDto) data.getData("patientBuffer");//Este buffer viene de la pantalla de registro de paciente en caso de no existir el paciente
            fechaAppointment = (String) data.getData("fechaAppointment");
            LocalDate fecha = LocalDate.parse(fechaAppointment, formatter);
            dpAppoinmentDate.setValue(fecha); //NO SIRVE
            initializeSpinners();
            initializeComboBox();
            addPatientsInCb();
            bindMedicalAppointment();
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
        System.out.println("pppp " + pat.getName());
        MedicalAppointmentDto mAp = new MedicalAppointmentDto();
        if (agendaDtos.get(dpAppoinmentDate.getValue().toString()) == null) {
            createAgenda(dpAppoinmentDate.getValue().toString());
        }
//        bindMedicalAppointment();
        if (patientBuffer == null) {
            //alerta
        }

        if (pat != null) {
            mAp.setAgenda(agendaBuffer);
            mAp.setPatient(pat);
            mAp.setScheduledBy(scheduledBy);
            mAp.setScheduledDate(agendaBuffer.getAgendaDate());
            if (cbHoursAvailable.getValue() != null) {
                mAp.setScheduledStartTime(cbHoursAvailable.getValue());
                mAp.setScheduledEndTime(getEndTime(cbHoursAvailable.getValue(), agendaBuffer.getHourlySlots(), spNSlots.getValue()));
            } else {
                //alerta
            }
            mAp.setState("SCHEDULED");
            mService.createMedicalAppointments(mAp);
        } else {
            //alerta de crear paciente
        }
    }

    private void addPatientsInCb() {
        patients = (List<PatientDto>) pService.getPatients().getData();
        cbIdentification.getItems().addAll(patients);
    }

    @FXML
    private void createPatient(ActionEvent event) throws IOException {
        Animation.MakeDefaultFadeTransition(parent, App.getFXMLLoader("UserRegister").load());
        //agregar bandera
    }

    @FXML
    private void dpChangeDate(ActionEvent event) {
        if (agendaDtos.get(dpAppoinmentDate.getValue().toString()) == null) {
            addAllHoursInCb(getHours(doctorBuffer.getShiftStartTime(), doctorBuffer.getShiftEndTime(), doctorBuffer.getHourlySlots()));
        }
    }

    @FXML
    private void searchById(KeyEvent event) {
        if (event.getCode().isDigitKey()) {
            String idToSearch = cbIdentification.getEditor().getText();
            if (idToSearch != null) {
                cbIdentification.getItems().clear();
                if (idToSearch.length() > 2) {
                    cbIdentification.getItems().addAll(patients.stream().filter(t -> t.getIdentification().contains(idToSearch)).collect(Collectors.toList()));
                    return;
                }
                cbIdentification.getItems().addAll(patients);
                cbIdentification.show();
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
                // Realiza las acciones necesarias con el paciente seleccionado
            }
        });
    }

    public void bindMedicalAppointment() {
        txfReason.textProperty().bindBidirectional(medicalAppointmentBuffer.reason);
        txfEmail.textProperty().bindBidirectional(medicalAppointmentBuffer.patientEmail);
        txfPhoneNumber.textProperty().bindBidirectional(medicalAppointmentBuffer.patientPhoneNumber);
        dpAppoinmentDate.valueProperty().bindBidirectional(medicalAppointmentBuffer.scheduledDate);
        cbHoursAvailable.valueProperty().bindBidirectional(medicalAppointmentBuffer.scheduledStartTime);
        if (medicalAppointmentBuffer.getSlotsNumber() != null) {
            Long slotsNumber = medicalAppointmentBuffer.getSlotsNumber();
            spNSlots.getEditor().setText(slotsNumber.toString());
        }
        if (medicalAppointmentBuffer.getPatient() != null) {
            cbIdentification.setValue(medicalAppointmentBuffer.getPatient());
        }
        rbGroup.selectedToggleProperty()
                .addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
                    if (newValue != null && medicalAppointmentBuffer != null) {
                        medicalAppointmentBuffer.setState(medicalAppointmentBuffer.parseState(((RadioButton) newValue).getText()));
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
                    } else if (medicalAppointmentBuffer.getState().toLowerCase().equals("canceled")) {
                        if (((RadioButton) t).getText().toLowerCase().equals("canceled")
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
        spNSlots.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
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
        spNSlots.getValueFactory().setConverter(formatter);

    }

    private boolean verifyFields() {
        List<Node> fields = Arrays.asList(txfEmail, txfPhoneNumber, txfReason, cbIdentification, spNSlots);
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

    public void createAgenda(String fechaAppointment) {
        AgendaDto a = new AgendaDto();
        a.setDoctor(doctorBuffer);
        a.setAgendaDate(fechaAppointment);
        a.setHourlySlots(doctorBuffer.getHourlySlots());
        a.setShiftStartTime(doctorBuffer.getShiftStartTime());
        a.setShiftEndTime(doctorBuffer.getShiftEndTime());
        if (safeAgenda(a)) {

        }
    }

    public boolean safeAgenda(AgendaDto a) {
        if (a.getId() == null) {
            ResponseWrapper response = aService.createAgenda(a);
            if (response.getCode() == ResponseCode.OK) {
                Message.showNotification("Success", MessageType.CONFIRMATION, response.getMessage());
                agendaBuffer = (AgendaDto) response.getData();
                return true;
            }
            Message.showNotification("Ups", MessageType.ERROR, response.getMessage());
            System.out.println(response.getMessage());
            return false;
        }
        return false;
    }

    @FXML
    private void setSlotsAvailable(MouseEvent event) {;
        AgendaDto ag = agendaDtos.get(dpAppoinmentDate.getValue().toString());
        addAllHoursInCb(getAvailableHoursForAppointment(getHours(ag.getShiftStartTime(), ag.getShiftEndTime(), ag.getHourlySlots()),
                ag.getHourlySlots(), spNSlots.getValue(), ag.getMedicalAppointments(), ag));
    }

    private AgendaDto getAgenda(String AppointmentDate) {
        List<AgendaDto> agendas = doctorBuffer.getAgendas();
        AgendaDto agenda = new AgendaDto();

        for (AgendaDto a : agendas) {
            if (a.getAgendaDate().equals(AppointmentDate)) {
                agenda = a;
            }
        }

        return (agenda != null) ? agenda : null;
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

    private boolean checkOverlap(String startTime, String endTime, MedicalAppointmentDto mA, AgendaDto a) {
        LocalTime newStartTimeAppointment = LocalTime.parse(startTime);
        LocalTime newEndTimeAppointment = LocalTime.parse(endTime);
        String s = mA.getScheduledStartTime();
        String e = mA.getScheduledEndTime();
        String end = a.getShiftEndTime();
        s = (s.charAt(1) == ':') ? "0" + s : s;
        e = (e.charAt(1) == ':') ? "0" + e : e;
        end = (end.charAt(1) == ':') ? "0" + end : end;
        LocalTime startExist = LocalTime.parse(s);
        LocalTime endExist = LocalTime.parse(e);

        if ((newStartTimeAppointment.isBefore(endExist) && newEndTimeAppointment.isAfter(startExist))
                || (newEndTimeAppointment.equals(startExist))
                || (newStartTimeAppointment.isBefore(startExist) && newEndTimeAppointment.isAfter(LocalTime.parse(end)))) {
            return true;
        }

        if (newEndTimeAppointment.isAfter(LocalTime.parse(end))) {
            return true;
        }
        return false; 
    }

    private String getEndTime(String startTime, Long nSlots, int nAppointMentSlots) {
        long intervalMillis = TimeUnit.HOURS.toMillis(1) / nSlots;
        long intervalMinutes = TimeUnit.MILLISECONDS.toMinutes(intervalMillis);
        LocalTime horaInicioLocal = LocalTime.parse(startTime);
        LocalTime horaFin = horaInicioLocal.plusMinutes(intervalMinutes * nAppointMentSlots);
        return horaFin.toString();
    }

    private List<String> getAvailableHoursForAppointment(List<String> horasDisponibles, Long nSlots, int nAppSlots, List<MedicalAppointmentDto> appointments, AgendaDto a) {
        List<String> hoursAvailable = new ArrayList<>();

        for (String hour : horasDisponibles) {
            boolean disponible = true;

            for (MedicalAppointmentDto mA : appointments) {
                if (checkOverlap(hour, getEndTime(hour, nSlots, nAppSlots), mA, a)) {
                    disponible = false;
                }
            }

            if (disponible) {
                hoursAvailable.add(hour);
            }
        }
        return hoursAvailable;
    }

    private void loadAgendas(DoctorDto doctorDto) {
        for (AgendaDto i : doctorDto.getAgendas()) {
            AgendaDto agenda = (AgendaDto) aService.getAgendaById(i.getId()).getData();
            if (agenda != null) {
                agendaDtos.put(agenda.getAgendaDate(), agenda);
            }
        }
    }

    private void addAllHoursInCb(List<String> horasDisp) {//restringir hora fin
        cbHoursAvailable.getItems().addAll(FXCollections.observableArrayList(horasDisp));
    }

    @FXML
    private void cbSelectPatient(ActionEvent event) {
        cbIdentification.setEditable(true);
        pat = cbIdentification.getValue();
        cbIdentification.setEditable(false);
    }

}
