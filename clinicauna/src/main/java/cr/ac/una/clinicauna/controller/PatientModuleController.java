package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.components.Animation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class PatientModuleController implements Initializable {

    @FXML
    private VBox parent;
    @FXML
    private JFXTextField txfSearchPatient;
    @FXML
    private ComboBox<String> cbSearchParameter;
    @FXML
    private TableView<?> tblPatientsView;
    @FXML
    private TableColumn<?, ?> tcIdentification;
    @FXML
    private TableColumn<?, ?> tcName;
    @FXML
    private TableColumn<?, ?> tcLastName;
    @FXML
    private TableColumn<?, ?> tcPhone;
    @FXML
    private TableColumn<?, ?> tcRole;
    @FXML
    private Button btnEdit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void btnNewPatientAction(ActionEvent event) {
        Animation.MakeDefaultFadeTransition(parent, "PatientRegister");
    }

    @FXML
    private void btnEditPatientAction(ActionEvent event) {
    }

    @FXML
    private void btnDeletePatientAction(ActionEvent event) {
    }
    
}
