package cr.ac.una.clinicauna.controller;

import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.components.AppointmentNode;
import cr.ac.una.clinicauna.components.Header;
import cr.ac.una.clinicauna.model.AgendaDto;
import cr.ac.una.clinicauna.model.DoctorDto;
import cr.ac.una.clinicauna.model.MedicalAppointmentDto;
import cr.ac.una.clinicauna.model.PatientDto;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.AgendaService;
import cr.ac.una.clinicauna.services.DoctorService;
import cr.ac.una.clinicauna.services.MedicalAppointmentService;
import cr.ac.una.clinicauna.services.UserService;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import cr.ac.una.clinicauna.util.AgendaBuilder;
import cr.ac.una.clinicauna.util.Data;

import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 *
 * @author arayaroma
 */
public class AgendaModuleController implements Initializable {

    @FXML
    private HBox hbAgendaHeader;
    @FXML
    private GridPane gpAgenda;
    @FXML
    private Label lbDoctorName;
    @FXML
    private Label lbDoctorCode;
    @FXML
    private Label lbDoctorIdCard;
    @FXML
    private ComboBox<UserDto> cbDoctor;
    @FXML
    private VBox parent;
    @FXML
    private Label lblMonth;
    @FXML
    private Label lblYear;
    private int countWeeks = 0;

    private DoctorService doctorService = new DoctorService();
    private UserService userService = new UserService();
    private DoctorDto doctorBuffer;
    private DoctorService DoctorService = new DoctorService();
    private AgendaService agendaService = new AgendaService();
    private Map<String, AgendaDto> agendaDtos = new HashMap<>();
    private Map<String, Integer> days = new HashMap();
    private Map<String, Integer> medicalAppointmentsHours = new HashMap();
    private List<String> hoursCalculated = new ArrayList<>();
    private Data data = Data.getInstance();
    private List<Header> headers = new ArrayList<>();
    private MedicalAppointmentDto medicalAppointentBuffer;
    private MedicalAppointmentService medicalAppointmentService = new MedicalAppointmentService();
    private UserDto userLoggued;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLoggued = (UserDto) data.getData("userLoggued");
        setDays(countWeeks);
        initializeComboBox();
        loadDoctors();

    }

    @FXML
    private void leftArrowAction(MouseEvent event) {
        setDays(countWeeks -= 1);
        if (doctorBuffer != null) {
            loadGrid();
        }

    }

    @FXML
    private void rigthArrowAction(MouseEvent event) {
        setDays(countWeeks += 1);
        if (doctorBuffer != null) {
            loadGrid();
        }
    }

    @FXML
    private void cbSelectDoctor(ActionEvent event) {
        UserDto user = cbDoctor.getValue();
        if (user != null) {
            doctorBuffer = (DoctorDto) DoctorService.getDoctorById(user.getId()).getData();
            if (doctorBuffer != null) {
                lbDoctorCode.setText(doctorBuffer.getCode());
                lbDoctorIdCard.setText(String.valueOf(doctorBuffer.getIdCard()));
                hoursCalculated = calculateHours(doctorBuffer.getShiftStartTime(), doctorBuffer.getShiftEndTime(), doctorBuffer.getHourlySlots());
                loadHours(hoursCalculated);
                loadGrid();
            }
            lbDoctorName.setText(user.getName());
        }
    }

    @FXML
    private void btnSetActualWeekAction(ActionEvent event) {
        setDays(0);
        countWeeks = 0;
        if (doctorBuffer != null) {
            loadGrid();
        }
    }

    private void loadGrid() {
        cleanAgenda();
        loadPanes();
        loadAgendas(doctorBuffer);
    }

    private void updateMedicalAppointment(MedicalAppointmentDto medicalAppointmentDto, String newTime, LocalDate newDate) {
        try {
            if (medicalAppointmentDto != null) {
                medicalAppointmentDto.setScheduledDate(newDate.toString());
//                medicalAppointmentDto.setScheduledTime(newTime);
                ResponseWrapper response = medicalAppointmentService.updateMedicalAppointments(medicalAppointmentDto);
                if (response.getCode() != ResponseCode.OK) {
                    Message.showNotification("Error", MessageType.ERROR, response.getMessage());
                    return;
                }
                medicalAppointentBuffer = (MedicalAppointmentDto) response.getData();

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void loadAgendas(DoctorDto doctorDto) {
        for (AgendaDto i : doctorDto.getAgendas()) {
            AgendaDto agenda = (AgendaDto) agendaService.getAgendaById(i.getId()).getData();
            if (agenda != null) {
                agendaDtos.put(agenda.getAgendaDate(), agenda);
                loadMedicalAppointments(agenda.getMedicalAppointments());
            }
        }
    }

    private void loadMedicalAppointments(List<MedicalAppointmentDto> medicalAppointments) {
        for (int i = 0; i < medicalAppointments.size(); i++) {
            MedicalAppointmentDto medicalAppointmentDto = medicalAppointments.get(i);
            String date = medicalAppointmentDto.getScheduledDate();
            String time = medicalAppointmentDto.getScheduledStartTime();
            Integer slotsNumber = medicalAppointmentDto.getSlotsNumber().intValue();
            Integer column = days.get(date);
            Integer row = medicalAppointmentsHours.get(time);
            if (column != null && row != null) {
                AppointmentNode node = createMedicalAppointmentCard(medicalAppointmentDto);
                gpAgenda.add(node, column, row);
                GridPane.setRowSpan(node, slotsNumber);
            }
        }
    }

    private void cleanAgenda() {
        for (int i = 1; i < gpAgenda.getRowCount(); i++) {
            for (int j = 1; j < gpAgenda.getColumnCount(); j++) {
                removeNodeInGrid(i, j);
            }
        }
    }

    private void loadDoctors() {
        List<UserDto> userDtos = (List<UserDto>) userService.getUsers().getData();
        if (userDtos != null) {
            userDtos = userDtos.stream().filter(user -> user.getDoctor() != null).collect(Collectors.toList());
            userDtos.stream().forEach(user -> cbDoctor.getItems().add(user));

        }
    }

    private void initializeComboBox() {
        cbDoctor.setCellFactory(param -> new ListCell<UserDto>() {
            @Override
            protected void updateItem(UserDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " " + item.getFirstLastname());
                }
            }
        });

        cbDoctor.setConverter(new StringConverter<UserDto>() {
            @Override
            public String toString(UserDto user) {
                return user == null ? null : user.getName();
            }

            @Override
            public UserDto fromString(String string) {
                return null;
            }
        });
    }

    private void setDays(int weekOffset) {
        LocalDate date;
        if (weekOffset == 0) {
            date = LocalDate.now();
        } else if (weekOffset > 0) {
            date = LocalDate.now().plusWeeks(weekOffset);
        } else {
            date = LocalDate.now().minusWeeks(-weekOffset);
        }

        AgendaBuilder agenda = AgendaBuilder
                .builder()
                .withActualDate(date)
                .build();

        List<LocalDate> localDays = agenda.calculateWeekDays(date);
        days.clear();
        for (int i = 0; i < localDays.size(); i++) {
            removeNodeInGrid(0, i + 1);
            LocalDate actualDay = localDays.get(i);
            String day = actualDay.getDayOfWeek().name().substring(0, 3);
            Integer number = localDays.get(i).getDayOfMonth();
            Header nodeDay = createAgendaHeader(day + " " + number, actualDay);
            if (LocalDate.now().equals(actualDay)) {
                nodeDay.getStyleClass().add("actual-day");
            }
            days.put(localDays.get(i).toString(), i + 1);
            gpAgenda.add(nodeDay, i + 1, 0);
        }
        lblMonth.setText(String.valueOf(date.getMonthValue()));
        lblYear.setText(String.valueOf(date.getYear()));

    }

    private void removeNodeInGrid(Integer row, Integer column) {
        Integer column2, row2;
        List<Node> nodeToRemove = new ArrayList<>();
        for (Node node : gpAgenda.getChildren()) {
            column2 = GridPane.getColumnIndex(node);
            row2 = GridPane.getRowIndex(node);
            if (Objects.equals(column2, column) && Objects.equals(row, row2)) {
                nodeToRemove.add(node);

            }
        }
        gpAgenda.getChildren().removeAll(nodeToRemove);
    }

    private Header createAgendaHeader(String info, LocalDate date) {
        Header header = new Header(info, date);
        header.getStyleClass().add("bg-black");
        header.getChildren().add(new Label(info));
        header.setAlignment(Pos.CENTER);
        return header;
    }

    private List<String> calculateHours(String startTime, String endTime, Long fieldsPerHour) {
        List<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        try {
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            long intervalMillis = TimeUnit.HOURS.toMillis(1) / fieldsPerHour;
            for (long time = start.getTime(); time < end.getTime(); time += intervalMillis) {
                Date newTime = new Date(time);
                result.add(sdf.format(newTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void loadHours(List<String> hours) {
        for (int i = 0; i < hours.size(); i++) {
            medicalAppointmentsHours.put(hours.get(i), i + 1);
            removeNodeInGrid(i + 1, 0);
            gpAgenda.add(createAgendaHeader(hours.get(i), null), 0, i + 1);
            RowConstraints row = new RowConstraints(USE_COMPUTED_SIZE);
            gpAgenda.getRowConstraints().add(row);
        }
    }

    private AppointmentNode createMedicalAppointmentCard(MedicalAppointmentDto medicalAppointmentDto) {
        AppointmentNode appointmentNode = new AppointmentNode(medicalAppointmentDto);
        appointmentNode.setAlignment(Pos.CENTER);
        PatientDto patientDto = medicalAppointmentDto.getPatient();
        if (patientDto != null) {
            Label name = new Label(patientDto.getName());
            Label numberPhone = new Label("Tel: " + patientDto.getPhoneNumber());
            appointmentNode.getChildren().addAll(name, numberPhone);
            appointmentNode.getStyleClass().add("cardMedicalAppointment");
        }
        switch (medicalAppointmentDto.getState().toLowerCase()) {
            case "attended":
                appointmentNode.getStyleClass().add("attended");
                break;
            case "scheduled":
                appointmentNode.getStyleClass().add("scheduled");
                break;
            case "absent":
                appointmentNode.getStyleClass().add("absent");
                break;
            case "cancelled":
                appointmentNode.getStyleClass().add("cancelled");
                break;
        }
        appointmentNode.setOnMouseClicked(e -> createMedicalAppointment(e, appointmentNode.getMedicalAppointmentDto()));
        intializeDragAndDrop(appointmentNode);
        return appointmentNode;

    }
    Node nodeBuffer;

    private void intializeDragAndDrop(Node node) {
        node.setOnDragDetected((event) -> {
            nodeBuffer = node;
            Dragboard dragboard = node.startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(node.snapshot(null, null));
            ClipboardContent content = new ClipboardContent();
            content.putString("check");
            dragboard.setContent(content);
        });
        gpAgenda.setOnDragOver(event -> {
            if (event.getGestureSource() != gpAgenda && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        gpAgenda.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                Integer row = GridPane.getRowIndex((Node) event.getTarget());
                Integer col = GridPane.getColumnIndex((Node) event.getTarget());
                if (row != null && col != null && row > 0 && col > 0) {
                    gpAgenda.getChildren().remove(nodeBuffer);
                    gpAgenda.add(nodeBuffer, col, row);

                    if (nodeBuffer instanceof AppointmentNode) {
                        medicalAppointentBuffer = ((AppointmentNode) nodeBuffer).getMedicalAppointmentDto();
                        System.out.println(medicalAppointentBuffer.getVersion());
                        updateMedicalAppointment(medicalAppointentBuffer, getHourInGrid(row), LocalDate.parse(getDayInGrid(col)));
                        ((AppointmentNode) nodeBuffer).setMedicalAppointmentDto(medicalAppointentBuffer);
                        System.out.println(medicalAppointentBuffer.getVersion());
                    }

                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

    }

    private String getHourInGrid(int pos) {
        Integer row, column;
        for (Node i : gpAgenda.getChildren()) {
            row = GridPane.getRowIndex(i);
            column = GridPane.getColumnIndex(i);
            if (row != null && row == pos && column != null && column == 0 && i instanceof Header) {
                return ((Header) i).getMessage();
            }
        }
        return "";
    }

    private String getDayInGrid(int pos) {
        Integer row, column;
        for (Node i : gpAgenda.getChildren()) {
            row = GridPane.getRowIndex(i);
            column = GridPane.getColumnIndex(i);
            if (row != null && row == 0 && column != null && column == pos && i instanceof Header) {
                LocalDate date = ((Header) i).getDay();
                if (date != null) {
                    return date.toString();
                }
                return "";
            }
        }
        return "";
    }

    private void loadPanes() {
        for (int i = 1; i < gpAgenda.getRowCount(); i++) {
            for (int j = 1; j < gpAgenda.getColumnCount(); j++) {
                HBox hBox = new HBox();
                hBox.getStyleClass().add("paneContainer");
                hBox.setOnMouseClicked(event -> {
                    createMedicalAppointment(event, null);
                });
                gpAgenda.add(hBox, j, i);
            }
        }
    }

    private void createMedicalAppointment(MouseEvent event, MedicalAppointmentDto medicalAppointmentDto) {
        try {
            Integer column = GridPane.getColumnIndex((Node) event.getSource());
            if (column != null) {
                String day = getDayInGrid(column);
                data.setData("agendaBuffer", agendaDtos.get(day));
                data.setData("fechaAppointment", day);
            }
            data.setData("doctorBuffer", doctorBuffer);
            data.setData("scheduledBy", userLoggued);
            data.setData("medicalAppointmentBuffer", medicalAppointmentDto);
            Animation.MakeDefaultFadeTransition(parent, App.getFXMLLoader("MedicalAppointmentRegister").load());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

};
