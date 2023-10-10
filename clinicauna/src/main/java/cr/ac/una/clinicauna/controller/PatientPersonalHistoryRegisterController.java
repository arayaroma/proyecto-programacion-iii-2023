package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class PatientPersonalHistoryRegisterController implements Initializable {

    @FXML
    private HBox parent;
    @FXML
    private VBox mainView;
    @FXML
    private ImageView imgPhotoProfile;
    @FXML
    private JFXTextArea txfPathological;
    @FXML
    private JFXTextArea txfHospitalizations;
    @FXML
    private JFXTextArea txfSurgical;
    @FXML
    private JFXTextArea txfAlergies;
    @FXML
    private JFXTextArea txfTreatments;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backAction(MouseEvent event) {
    }

    @FXML
    private void btnRegisterPatientPersonalHistoryAction(ActionEvent event) {
    }
    
}
