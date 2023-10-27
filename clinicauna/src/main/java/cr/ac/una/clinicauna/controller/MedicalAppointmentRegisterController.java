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
import cr.ac.una.clinicauna.services.PatientService;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.control.SpinnerValueFactory;
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

    private Data data = Data.getInstance();

    private PatientService pService = new PatientService();
    private AgendaService aService = new AgendaService();
    private List<PatientDto> patients = new ArrayList();
    private MedicalAppointmentDto medicalAppointmentBuffer;
    private PatientDto patientBuffer;
    private AgendaDto agendaBuffer = new AgendaDto();
    private DoctorDto doctorBuffer;
    private UserDto scheduledBy;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            doctorBuffer = (DoctorDto) data.getData("doctorBuffer");
//        patientBuffer = (PatientDto) data.getData("patientBuffer");//Este buffer viene de la pantalla de registro de paciente en caso de no existir el paciente
            String fechaAppointment = (String) data.getData("fechaAppointment");
            if (agendaBuffer.getId() == null) {//Crear al dar click a crear
                createAgenda(fechaAppointment);
            }
            patients = (List<PatientDto>) pService.getPatients().getData();
            addPatientsInCb(patients);
            initializeSpinners();
            initializeComboBox();
            bindMedicalAppointment();
        } catch (Exception e) {
            System.out.println(e.toString());
            backAction(null);
        }
    }

    @FXML
    private void backAction(MouseEvent event) {
        try {
            data.removeData("doctor");
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
        bindMedicalAppointment();
        if (patientBuffer == null) {
            //alerta
        } else {

        }
    }

    private void addPatientsInCb(List<PatientDto> patientsDtos) {
        cbIdentification.getItems().addAll(FXCollections.observableArrayList(patientsDtos));
    }

//    private void addSlotsInCb(AgendaDto agenda) {//No es necesario parsear la lista si carga el paciente como tal
//        ObservableList<String> slots = FXCollections.observableArrayList();
//        cbHoursAvailable.getItems().addAll(mapListToObsevableStringS(agendaBuffer.getSlots()));
//    }
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
    }

//    private ObservableList<String> mapListToObsevableStringP(List<PatientDto> p) {//No es necesario parsear la lista
//        ObservableList<String> patientsId = FXCollections.observableArrayList();
//        for (PatientDto pp : p) {
//            patientsId.add(pp.getIdentification());
//        }
//        return patientsId;
//    }
//    private ObservableList<String> mapListToObsevableStringS(List<SlotsDto> s) {//Ya no se usa los slots
//        ObservableList<String> slotsA = FXCollections.observableArrayList();
//        for (SlotsDto ss : s) {
//            if (ss.getAvailable().equals("AVAILABLE")) {
//                slotsA.add(ss.getTimeSlot());
//            }
//        }
//        return slotsA;
//    }
//    private PatientDto getPatientByIdentification(String id) {//No debe buscar por id el paciente ya que el combobox carga el objeto paciente
//        return patients.stream()
//                .filter(p -> p.getIdentification().equals(id))
//                .findFirst()
//                .orElse(null);
//    }
//    public void setBuffers(AgendaDto agenda, PatientDto patient, MedicalAppointmentDto meiPatient) {//No lo usa
//        this.agendaBuffer = agenda;
//        this.patientBuffer = patient;
//        this.medicalAppointmentBuffer = mPatient;
//    }
//    private void setDataInAppointment(MedicalAppointmentDto medAppointment) {//Si hace el bind no es necesario
//        medAppointment.setPatient(patientBuffer);
//        medAppointment.setAgenda(agendaBuffer);
//        medAppointment.setScheduledBy((UserDto) data.getData("userLoggued"));
////        if () {
//        medAppointment.setPatientEmail(txfEmail.getText());
////        }
//        medAppointment.setPatientPhoneNumber(txfPhoneNumber.getText());
//        medAppointment.setReason(txfReason.getText());
//        medAppointment.setScheduledDate(dpAppoinmentDate.getValue().toString());
////        medAppointment.setScheduledTime();
//    }
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
    }

    public void initializeSpinners() {
        spNSlots.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
        StringConverter<Integer> formatter = new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
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

//    private List<SlotsDto> createSlots(String startTime, String endTime, Long fieldsPerHour, String fechaAppointment) {//Ya no se usa slots
//        List<SlotsDto> result = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
//        try {
//            Date start = sdf.parse(startTime);
//            Date end = sdf.parse(endTime);
//            long intervalMillis = TimeUnit.HOURS.toMillis(1) / fieldsPerHour;
//            for (long time = start.getTime(); time < end.getTime(); time += intervalMillis) {
//                Date newTime = new Date(time);
//                SlotsDto slot = new SlotsDto();
//                slot.setAgenda(agendaBuffer);
//                slot.setAvailable("AVAILABLE");
//                String formattedTime = outputFormat.format(newTime);
//                slot.setTimeSlot(formattedTime);
//                slot.setSlotDate(fechaAppointment);
//                result.add(slot);
////                sService.createSlot(slot); ERROR
//            }
//            System.out.println("Test: " + agendaBuffer.getId() + " " + agendaBuffer.getDoctor());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
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

    private void setSlotsAvailable(ActionEvent event) { //falta
        getAvailableSlots(spNSlots.getValue());
    }
    
    private List<String> getAvailableSlots(int nSlots){
        List<String> Availables = new ArrayList();
        List<MedicalAppointmentDto> Appointments = agendaBuffer.getMedicalAppointments();
        /* for (long time = start.getTime(); time < end.getTime(); time += intervalMillis) {
//                Date newTime = new Date(time);
//                SlotsDto slot = new SlotsDto();
//                slot.setAgenda(agendaBuffer);
//                slot.setAvailable("AVAILABLE");
//                String formattedTime = outputFormat.format(newTime);
//                slot.setTimeSlot(formattedTime);
//                slot.setSlotDate(fechaAppointment);
//                result.add(slot);
//                sService.createSlot(slot); //ERROR
//            }*/
//        for()
        
        return Availables;
    }
    
    public void createAgenda(String fechaAppointment) {
        AgendaDto a = new AgendaDto();
        a.setDoctor(doctorBuffer);
        a.setAgendaDate(fechaAppointment);
        a.setHourlySlots(doctorBuffer.getHourlySlots());
        a.setShiftStartTime(doctorBuffer.getShiftStartTime());
        a.setShiftEndTime(doctorBuffer.getShiftEndTime());
        if (safeAgenda(a)) {
//            MostrarTodosLosCamposDisponibles
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
    private void setSlotsAvailable(MouseEvent event) {
    }

}
