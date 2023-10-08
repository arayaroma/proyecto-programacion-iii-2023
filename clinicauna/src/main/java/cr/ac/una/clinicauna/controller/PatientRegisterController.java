package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.PatientDto;
import cr.ac.una.clinicauna.services.PatientService;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class PatientRegisterController implements Initializable {

    @FXML
    private HBox parent;
    @FXML
    private VBox mainView;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private JFXTextField txfIdentification;
    @FXML
    private JFXTextField txfName;
    @FXML
    private JFXTextField txfLastName;
    @FXML
    private ComboBox<String> cbGender;
    @FXML
    private JFXTextField txfSecondLastName;
    @FXML
    private JFXTextField txfEmail;
    @FXML
    private JFXTextField txfPhoneNumber;
    private PatientDto patientBuffer = new PatientDto();
    private PatientService patientService = new PatientService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbGender.getItems().addAll("MALE", "FEMALE");
        PatientDto patientDto = (PatientDto) Data.getData("patientBuffer");
        if (patientDto != null) {
            patientBuffer = patientDto;
        }
        bindPatient();
    }

    @FXML
    private void backFromRegister(MouseEvent event) {
        Data.removeData("patientBuffer");
        Animation.MakeDefaultFadeTransition(mainView, "Main");
    }

    @FXML
    private void btnRegisterAction(ActionEvent event) {
        if (!verifyFields()) {
            Message.showNotification("Ups", MessageType.INFO, "All the fields are required");
            return;
        }
        ResponseWrapper response = patientBuffer.getId() == null ? patientService.createPatient(patientBuffer) : patientService.updatePatient(patientBuffer);
        Message.showNotification(response.getCode().name(), MessageType.INFO, response.getMessage());
        if (response.getCode() == ResponseCode.OK) {
            backFromRegister(null);
        }
    }

    private void bindPatient() {
        txfEmail.textProperty().bindBidirectional(patientBuffer.email);
        txfName.textProperty().bindBidirectional(patientBuffer.name);
        txfLastName.textProperty().bindBidirectional(patientBuffer.firstLastname);
        txfIdentification.textProperty().bindBidirectional(patientBuffer.identification);
        txfPhoneNumber.textProperty().bindBidirectional(patientBuffer.phoneNumber);
        txfSecondLastName.textProperty().bindBidirectional(patientBuffer.secondLastname);
        dpBirthDate.valueProperty().bindBidirectional(patientBuffer.birthDate);
        cbGender.valueProperty().bindBidirectional(patientBuffer.gender);
    }

    private void unbindPatient() {
        txfEmail.textProperty().unbindBidirectional(patientBuffer.email);
        txfName.textProperty().unbindBidirectional(patientBuffer.name);
        txfLastName.textProperty().unbindBidirectional(patientBuffer.firstLastname);
        txfIdentification.textProperty().unbindBidirectional(patientBuffer.identification);
        txfPhoneNumber.textProperty().unbindBidirectional(patientBuffer.phoneNumber);
        txfSecondLastName.textProperty().unbindBidirectional(patientBuffer.secondLastname);
        dpBirthDate.valueProperty().unbindBidirectional(patientBuffer.birthDate);
        cbGender.valueProperty().unbindBidirectional(patientBuffer.gender);

    }

    private boolean verifyFields() {
        List<Node> fields = Arrays.asList(txfEmail, txfIdentification, txfLastName, txfName, txfSecondLastName, txfPhoneNumber);
        for (Node i : fields) {
            if (i instanceof JFXTextField && ((JFXTextField) i).getText() != null
                    && ((JFXTextField) i).getText().isBlank()) {
                return false;
            }
        }
        return true;
    }

}
