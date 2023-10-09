package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.components.Animation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class PatientFamilyHistoryRegisterController implements Initializable {

    @FXML
    private HBox parent;
    @FXML
    private HBox familyHistoryView;
    @FXML
    private JFXTextField txfDisease;
    @FXML
    private JFXTextField txfRelationship;
    @FXML
    private TableView<?> tblFamilyHistory;
    @FXML
    private TableColumn<?, ?> tcDisease;
    @FXML
    private TableColumn<?, ?> tcRelationship;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btnBackAction(MouseEvent event) {
        Animation.MakeDefaultFadeTransition(familyHistoryView, "PatientHistory");
    }

    @FXML
    private void btnSaveHistory(ActionEvent event) {
    }

    @FXML
    private void btnDeleteHistory(ActionEvent event) {
    }
    
}
