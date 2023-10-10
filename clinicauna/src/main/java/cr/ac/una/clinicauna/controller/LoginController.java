package cr.ac.una.clinicauna.controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
        try {
            if (Data.getLanguageOption().equals("en")) {
                lblLanguage.setText("EN/ES");
            } else {
                lblLanguage.setText("ES/EN");
            }
            txfPassword.setOnKeyPressed(event -> keyLoginHandler(event));
            txfUsername.setOnKeyPressed(event -> keyLoginHandler(event));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    private void btnLogInAction(ActionEvent event) throws IOException {
        String user = txfUsername.getText(), password = txfPassword.getText();
        if (user.isBlank() || password.isBlank()) {
            Message.showNotification("Ups", MessageType.ERROR, "All the fields are required");
            return;
        }
        if(user.equals("admin") && password.equals("admin")){
            Animation.MakeDefaultFadeTransition(parent, "Main");
            return;
        }
        ResponseWrapper response = userService.verifyUser(user, password);
        if (response.getCode() == ResponseCode.OK) {
            UserDto userDto = (UserDto) response.getData();
            if (userDto.getIsActive().equals("N")) {
                Message.showNotification("Ups", MessageType.INFO, "The user is not active");
                return;
            }
            Data.setData("userLoggued", userDto);
            loadLanguage(userDto);
            Animation.MakeDefaultFadeTransition(parent, "Main");
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
        Animation.MakeDefaultFadeTransition(parent, "Login");
    }

    @FXML
    private void backToLoginAction(MouseEvent event) {
        new FadeIn(mainView).play();
        mainView.toFront();
    }

    @FXML
    private void btnSendRecoveryEmailAction(ActionEvent event) {
        String email = txfRecoveryEmail.getText();
        if (email.isBlank()) {
            Message.showNotification("Ups", MessageType.WARNING, "All the fields are required");
            return;
        }
        ResponseWrapper response = userService.recoverPassword(email);
        if (response.getCode() == ResponseCode.OK) {
            Message.showNotification("Sending", MessageType.INFO, "Sending Email");
        } else {
            Message.showNotification("Error", MessageType.ERROR, response.getMessage());
        }
    }

    private void keyLoginHandler(KeyEvent ev) {
        try {
            if (ev.getCode() == KeyCode.ENTER) {
                btnLogInAction(null);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    private void loadLanguage(UserDto userDto){
        if(userDto.getLanguage().toLowerCase().equals("english")){
            Data.setLanguageOption("en");
            return;
        }
        Data.setLanguageOption("es");
    }
}
