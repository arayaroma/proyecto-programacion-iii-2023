package cr.ac.una.clinicauna.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import cr.ac.una.clinicauna.util.AgendaBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * 
 * @author arayaroma
 */
public class AgendaModuleController implements Initializable {

    @FXML
    private VBox vbAgenda;
    @FXML
    private HBox hbAgendaHeader;
    @FXML
    private GridPane gpAgenda;
    @FXML
    private JFXButton btnAgendaLeft;
    @FXML
    private JFXButton btnAgendaRight;
    @FXML
    private Label lbDoctorName;
    @FXML
    private Label lbDoctorCode;
    @FXML
    private Label lbDoctorIdCard;

    Pane[] days = new Pane[7];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCrossAxis();
        setDays(0);
        setMockedHours();
        makeAgendaScrollable();
    }

    private void makeAgendaScrollable() {
        ScrollPane scrollPane = new ScrollPane(gpAgenda);
        scrollPane.setPrefSize(1300, 800);
        scrollPane.setFitToWidth(true);
        vbAgenda.getChildren().add(scrollPane);
        vbAgenda.setStyle("-fx-padding: 1em 0em 0em 0em;");
    }

    private void setCrossAxis() {
        ColumnConstraints column = new ColumnConstraints(125);
        column.setHalignment(HPos.CENTER);

        Label agendaHours = new Label("Hours");
        agendaHours.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        gpAgenda.add(new Pane(agendaHours), 0, 0);
        gpAgenda.getColumnConstraints().add(column);
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

        for (int i = 0; i < days.length; i++) {
            days[i] = new Pane();
            LocalDate currentDate = agenda.getActualWeekDates()[i];
            String dayName = agenda.getWeekDays()[agenda.getCurrentDayOfTheWeekIndex(currentDate)];
            Label dayLabel = new Label(dayName + "  " + currentDate.getDayOfMonth());

            dayLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            dayLabel.setAlignment(Pos.CENTER);

            days[i].setStyle("-fx-background-color: black;");
            days[i].getChildren().add(dayLabel);

            ColumnConstraints column = new ColumnConstraints(165);
            column.setHalignment(HPos.CENTER);

            gpAgenda.getColumnConstraints().add(column);
            gpAgenda.getRowConstraints().add(new RowConstraints(50));
            gpAgenda.add(days[i], i + 1, 0);
        }
    }

    private void setMockedHours() {
        for (int i = 0; i < 24; i++) {
            Label hour = new Label(i + ":00");
            hour.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            hour.setAlignment(Pos.CENTER);
            gpAgenda.addRow(i + 1, hour);
            RowConstraints row = new RowConstraints(50);
            gpAgenda.getRowConstraints().add(row);
        }
    }

    @FXML
    private void onActionLeftButton(ActionEvent event) {
        clearGridPane();
        setDays(-1);
    }

    @FXML
    private void onActionRightButton(ActionEvent event) {
        clearGridPane();
        setDays(1);
    }

    private void clearGridPane() {
        gpAgenda.getChildren().clear();
        gpAgenda.getColumnConstraints().clear();
        gpAgenda.getRowConstraints().clear();

        setCrossAxis();
        setMockedHours();
    }

};