package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.DoctorDto;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.regex.Pattern;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class DoctorRegisterController implements Initializable {

    @FXML
    private HBox parent;
    @FXML
    private VBox mainView;
    @FXML
    private ImageView imgPhotoProfile;
    @FXML
    private JFXTextField txfCode;
    @FXML
    private JFXTextField txfInvoice;
    @FXML
    private JFXPasswordField txfCarne;
    @FXML
    private ComboBox<String> cbState;
    @FXML
    private JFXTextField txfHourlySlots;
    @FXML
    private Spinner<Integer> spStartingHours;
    @FXML
    private Spinner<Integer> spStartingMinutes;
    @FXML
    private Spinner<Integer> spEndingHours;
    @FXML
    private Spinner<Integer> spEndingMinutes;

    private UserDto userBuffer;

    private DoctorDto doctorBuffer = new DoctorDto();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userBuffer = (UserDto) Data.getData("userBuffer");
            initializeSpinners();
            bindDoctor();
        } catch (Exception e) {
            System.out.println(e.toString());
            backFromRegister(null);
        }

    }

    @FXML
    private void backFromRegister(MouseEvent event) {
        Animation.MakeDefaultFadeTransition(mainView, "UserRegister");
    }

    @FXML
    private void btnRegisterDoctorAction(ActionEvent event) {
        System.out.println("Starting hours");
        parseTimeToString(spStartingHours, spStartingMinutes);
        System.out.println("Ending hours");
        parseTimeToString(spEndingHours, spEndingMinutes);
    }

    private String parseTimeToString(Spinner spHour, Spinner spMinutes) {
        String time = spHour.getEditor().getText() + ":" + spMinutes.getEditor().getText();
        System.out.println(time);
        if (verifyTime(time)) {
            return time;
        }
        Message.showNotification("Invalid format", MessageType.WARNING, "The time is invalid");
        return "";
    }

    private boolean verifyTime(String time) {
        String timeRegex = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        return Pattern.matches(timeRegex, time);
    }

    private void bindDoctor() {
        txfCode.textProperty().bindBidirectional(doctorBuffer.code);
        txfCarne.textProperty().bindBidirectional(doctorBuffer.idCard);
        txfHourlySlots.textProperty().bindBidirectional(doctorBuffer.hourlySlots);
        if (doctorBuffer.getShiftStartTime() != null) {
            String[] startingHour = doctorBuffer.getShiftStartTime().split(":");
            if (startingHour.length == 2) {
                spStartingHours.getEditor().setText(startingHour[0]);
                spStartingMinutes.getEditor().setText(startingHour[1]);
            }
        }
        if (doctorBuffer.getShiftEndTime() != null) {
            String[] endingHour = doctorBuffer.getShiftEndTime().split(":");
            if (endingHour.length == 2) {
                spEndingHours.getEditor().setText(endingHour[0]);
                spEndingMinutes.getEditor().setText(endingHour[1]);
            }
        }
    }

    private void unbindDoctor() {
        txfCode.textProperty().unbindBidirectional(doctorBuffer.code);
        txfCarne.textProperty().unbindBidirectional(doctorBuffer.idCard);
        txfHourlySlots.textProperty().unbindBidirectional(doctorBuffer.hourlySlots);
        spStartingHours.getEditor().setText("");
        spStartingMinutes.getEditor().setText("");
        spEndingMinutes.getEditor().setText("");
        spEndingHours.getEditor().setText("");
    }

    private void initializeSpinners() {
        spStartingHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 00));
        spEndingHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 00));
        spStartingMinutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 00));
        spEndingMinutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 00));
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
        spStartingHours.getValueFactory().setConverter(formatter);
        spStartingMinutes.getValueFactory().setConverter(formatter);
        spEndingHours.getValueFactory().setConverter(formatter);
        spEndingMinutes.getValueFactory().setConverter(formatter);

    }

}
