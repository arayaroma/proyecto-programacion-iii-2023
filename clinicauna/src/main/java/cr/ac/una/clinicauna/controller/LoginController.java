package cr.ac.una.clinicauna.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField txfUsername;
    @FXML
    private JFXPasswordField txfPassword;
    @FXML
    private VBox mainView;
    @FXML
    private VBox recoveryPasswordView;
    @FXML
    private JFXTextField txfRecoveryEmail;
    @FXML
    private StackPane parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void btnLogInAction(ActionEvent event) throws IOException {
        Animation.fadeTransition(parent, Duration.seconds(0.5), 0, 1, 0, (t) -> {
            try {
                App.setRoot("Main");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).play();

    }

    @FXML
    private void forgotYourPasswordAction(MouseEvent event) {
        new FadeIn(recoveryPasswordView).play();
        recoveryPasswordView.toFront();
    }

    @FXML
    private void changeLanguajeAction(MouseEvent event) {
    }

    @FXML
    private void backToLoginAction(MouseEvent event) {
        new FadeIn(mainView).play();
        mainView.toFront();
    }

    @FXML
    private void btnSendRecoveryEmailAction(ActionEvent event) {
        Message.showNotification("Sending", MessageType.INFO, "Sending Email");
    }

}
