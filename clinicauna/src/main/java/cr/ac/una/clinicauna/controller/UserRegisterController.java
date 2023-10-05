package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.util.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class UserRegisterController implements Initializable {

    @FXML
    private VBox mainView;
    @FXML
    private JFXTextField txfIdentification;
    @FXML
    private JFXTextField txfUsername;
    @FXML
    private JFXPasswordField txfPassword;
    @FXML
    private JFXTextField txfName;
    @FXML
    private JFXTextField txfLastName;
    @FXML
    private JFXTextField txfSencondLastName;
    @FXML
    private JFXTextField txfEmail;
    @FXML
    private JFXTextField txfPhoneNumber;
    @FXML
    private JFXTextField txfLandlineNumber;
    @FXML
    private ToggleGroup roleGroup;
    @FXML
    private ComboBox<String> cbLanguage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Data.languageOption.equals("en")) {
            cbLanguage.getItems().addAll("Admin", "Doctor", "Receptionist");
        } else {
            cbLanguage.getItems().addAll("Administrador", "Doctor", "Recepcionista");
        }
    }

    @FXML
    private void backFromRegister(MouseEvent event) throws IOException {
        Animation.fadeTransition(mainView, Duration.seconds(0.5), 0, 1, 0, (t) -> {
            try {
                App.setRoot("Main");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).play();
    }

    @FXML
    private void selectCbLanguage(ActionEvent event) {
    }

    @FXML
    private void btnRegisterUserAction(ActionEvent event) {
    }

    @FXML
    private void changeLanguajeAction(MouseEvent event) {
    }

}
