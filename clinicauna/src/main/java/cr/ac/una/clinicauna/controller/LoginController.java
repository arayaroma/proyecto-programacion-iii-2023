package cr.ac.una.clinicauna.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.UserService;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    @FXML
    private Label lblLanguage;

    private UserService userService = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Data.getLanguageOption().equals("en")) {
            lblLanguage.setText("EN/ES");
        } else {
            lblLanguage.setText("ES/EN");
        }
    }

    @FXML
    private void btnLogInAction(ActionEvent event) throws IOException {
        String user = txfUsername.getText(), password = txfPassword.getText();
        if (user.isBlank() || password.isBlank()) {
            Message.showNotification("Ups", MessageType.ERROR, "All the fields are required");
            return;
        }
        ResponseWrapper response = userService.verifyUser(user, password);
        if (response.getCode() == ResponseCode.OK) {
            UserDto userDto = (UserDto) response.getData();
            Data.setData("userLoggued", userDto);
            loadMain();
            return;
        }
        Message.showNotification(response.getCode().name(), MessageType.ERROR, response.getMessage());
    }

    @FXML
    private void forgotYourPasswordAction(MouseEvent event) {
        new FadeIn(recoveryPasswordView).play();
        recoveryPasswordView.toFront();
    }

    @FXML
    private void changeLanguajeAction(MouseEvent event) throws IOException {
        String option = Data.getLanguageOption();
        switch (option) {
            case "en":
                Data.setLanguageOption("es");
                break;
            case "es":
                Data.setLanguageOption("en");

                break;
        }
        Animation.fadeTransition(parent, Duration.seconds(0.5), 0, 1, 0, (t) -> {
            try {
                App.setRoot("Login");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).play();
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

    private void loadMain() {
        Animation.fadeTransition(parent, Duration.seconds(0.5), 0, 1, 0, (t) -> {
            try {
                App.setRoot("Main");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).play();
    }
}
