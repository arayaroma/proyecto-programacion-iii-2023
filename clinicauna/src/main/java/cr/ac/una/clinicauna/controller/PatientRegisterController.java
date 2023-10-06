package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private JFXPasswordField txfLastName;
    @FXML
    private ComboBox<String> cbGender;
    @FXML
    private JFXTextField txfSecondLastName;
    @FXML
    private JFXTextField txfEmail;
    @FXML
    private JFXTextField txfPhoneNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void backFromRegister(MouseEvent event) {
    }
    
}
