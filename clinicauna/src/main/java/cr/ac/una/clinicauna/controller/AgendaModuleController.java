package cr.ac.una.clinicauna.controller;

import cr.ac.una.clinicauna.model.DoctorDto;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.DoctorService;
import cr.ac.una.clinicauna.services.UserService;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import cr.ac.una.clinicauna.util.AgendaBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setDays(countWeeks);
        initializeComboBox();
        loadDoctors();

    }

    @FXML
    private void leftArrowAction(MouseEvent event) {
        setDays(countWeeks -= 1);
    }

    @FXML
    private void rigthArrowAction(MouseEvent event) {
        setDays(countWeeks += 1);
    }

    @FXML
    private void cbSelectDoctor(ActionEvent event) {
        UserDto user = cbDoctor.getValue();
        if (user != null) {
            DoctorDto doctor = user.getDoctor();
            if (doctor != null) {
                lbDoctorCode.setText(doctor.getCode());
                lbDoctorIdCard.setText(String.valueOf(doctor.getIdCard()));
                loadHours(calculateHours(doctor.getShiftStartTime(), doctor.getShiftEndTime(), doctor.getHourlySlots()));
                loadPanes();
            }
            lbDoctorName.setText(user.getName());
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

        List<LocalDate> days = agenda.calculateWeekDays(date);
        for (int i = 0; i < days.size(); i++) {
            removeNodeInGrid(0, i + 1);
            String day = days.get(i).getDayOfWeek().name();
            Integer number = days.get(i).getDayOfMonth();
            gpAgenda.add(createAgendaHeader(day + " " + number), i + 1, 0);
        }
        lblMonth.setText(String.valueOf(date.getMonthValue()));
        lblYear.setText(String.valueOf(date.getYear()));

    }

    private void removeNodeInGrid(Integer row, Integer column) {
        Integer column2, row2;
        for (Node node : gpAgenda.getChildren()) {
            column2 = GridPane.getColumnIndex(node);
            row2 = GridPane.getRowIndex(node);
            if (Objects.equals(column2, column) && Objects.equals(row, row2)) {
                gpAgenda.getChildren().remove(node);
                return;
            }
        }
    }

    private Node createAgendaHeader(String info) {
        HBox hBox = new HBox();
        hBox.getStyleClass().add("bg-black");
        hBox.getChildren().add(new Label(info));
        hBox.setAlignment(Pos.CENTER);
        return hBox;
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
            gpAgenda.add(createAgendaHeader(hours.get(i)), 0, i + 1);
            RowConstraints row = new RowConstraints(50);
            gpAgenda.getRowConstraints().add(row);
        }
    }

    private void loadPanes() {

        for (int i = 1; i < gpAgenda.getRowCount(); i++) {
            for (int j = 1; j < gpAgenda.getColumnCount(); j++) {
                HBox hBox = new HBox();
                hBox.getStyleClass().add("paneContainer");
                gpAgenda.add(hBox, j, i);
            }
        }
    }
};
