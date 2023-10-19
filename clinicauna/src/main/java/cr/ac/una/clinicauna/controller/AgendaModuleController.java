package cr.ac.una.clinicauna.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * 
 * @author arayaroma
 */
public class AgendaModuleController implements Initializable {

    @FXML
    private VBox vbAgenda;
    @FXML
    private HBox hbDoctor;
    @FXML
    private GridPane gpAgenda;

    Pane[] days = new Pane[7];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDays();
    }

    private void setDays() {
        String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        for (int i = 0; i < days.length; i++) {
            days[i] = new Pane();
            Label dayLabel = new Label(weekDays[i]);
            dayLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            dayLabel.setAlignment(Pos.CENTER);

            days[i].setStyle("-fx-background-color: black;");
            days[i].getChildren().add(dayLabel);

            ColumnConstraints column = new ColumnConstraints(130);
            column.setHalignment(HPos.CENTER);

            gpAgenda.add(days[i], i, 0);
            gpAgenda.getColumnConstraints().add(column);
        }
    }

};