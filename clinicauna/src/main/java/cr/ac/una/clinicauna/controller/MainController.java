package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.UserService;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.ImageLoader;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author estebannajera
 * @author arayaroma
 */
public class MainController implements Initializable {

    @FXML
    private JFXHamburger hamburguerMenu;
    @FXML
    private VBox menuView;
    @FXML
    private JFXDrawer sliderMenu;
    @FXML
    private Label lblUserLoggued;
    @FXML
    private ImageView imgProfilePhoto;
    @FXML
    private StackPane menuLateral;
    @FXML
    private BorderPane parent;
    @FXML
    private StackPane container;
    @FXML
    private StackPane stack;
    @FXML
    private HBox profileContainer;
    @FXML
    private VBox changePasswordView;
    @FXML
    private Label lblChangePasswordInfo;
    @FXML
    private JFXPasswordField txfNewPassword;
    @FXML
    private JFXPasswordField txfConfirmPassword;

    private UserService userService = new UserService();
    private UserDto userLoggued;
    private Data data = Data.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            data.setData("mainController", this);
            intializeSliderMenu();
            userLoggued = (UserDto) data.getData("userLoggued");
            if (userLoggued != null) {
                lblUserLoggued.setText(userLoggued.getName());

                imgProfilePhoto
                        .setClip(new Circle(imgProfilePhoto.getFitWidth() / 2, imgProfilePhoto.getFitHeight() / 2, 30));
                if (userLoggued.getProfilePhoto() != null) {
                    imgProfilePhoto.setImage(ImageLoader.setImage(userLoggued.getProfilePhoto()));
                }
            }
            loadPrivileges();
        } catch (Exception e) {
            try {
                Animation.MakeDefaultFadeTransition(parent, App.getFXMLLoader("Login").load());
                System.out.println(e.toString());
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void btnUserModuleAction(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("UserModule");
        container.getChildren().clear();
        container.getChildren().add(loader.load());
    }

    @FXML
    private void btnPatientModuleAction(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("PatientModule");
        container.getChildren().clear();
        container.getChildren().add(loader.load());
    }

    /**
     * FIXME: Add catch block
     */
    @FXML
    private void btnLogOutAction(ActionEvent event) {
        try {
            Animation.MakeDefaultFadeTransition(parent, App.getFXMLLoader("Login").load());
        } catch (IOException e) {
        }
    }

    @FXML
    private void btnDoctorModuleAction(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("DoctorModule");
        container.getChildren().clear();
        container.getChildren().add(loader.load());
    }

    @FXML
    private void editUserLogguedAction(MouseEvent event) throws IOException {
        userLoggued = (UserDto) userService.findUserById(userLoggued.getId()).getData();
        data.setData("userBuffer", userLoggued);
        Animation.MakeDefaultFadeTransition(parent, App.getFXMLLoader("UserRegister").load());
    }

    @FXML
    private void discardChangesAction(ActionEvent event) throws IOException {
        Animation.MakeDefaultFadeTransition(parent, App.getFXMLLoader("Login").load());
    }

    @FXML
    private void submitChangesAction(ActionEvent event) {
        String password = txfNewPassword.getText(), confirmPassword = txfConfirmPassword.getText();
        if (password.isBlank() || !password.equals(confirmPassword)) {
            Message.showNotification("Warning", MessageType.WARNING, "You must to write a same password");
            return;
        }
        ResponseWrapper response = userService.changePassword(userLoggued.getId(), userLoggued.getPassword(), password);
        if (response.getCode() == ResponseCode.OK) {
            Message.showNotification("Succeed", MessageType.INFO, "Your password have been changed succesfully");
            changePasswordView.setVisible(false);
            menuLateral.setDisable(false);
            return;
        }
        Message.showNotification("Internal Error", MessageType.ERROR, response.getMessage());
    }

    @FXML
    private void passwordsEquals(KeyEvent event) {
        if (!txfNewPassword.getText().equals(txfConfirmPassword.getText())) {
            lblChangePasswordInfo.setText("New password and confirm is not equals");
            lblChangePasswordInfo.getStyleClass().add("red-color");
        } else {
            lblChangePasswordInfo.setText("");
        }
    }

    @FXML
    private void btnGeneralInformationModuleAction(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("GeneralInformationModule");
        container.getChildren().clear();
        container.getChildren().add(loader.load());
    }
    @FXML
    private void btnAgendaModuleAction(ActionEvent event) {

    }

    @FXML
    private void btnMedicalAppointmentModuleAction(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("MedicalAppointmentModule");
        container.getChildren().clear();
        container.getChildren().add(loader.load());
    }

    @FXML
    private void btnReportModuleAction(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("ReportModule");
        container.getChildren().clear();
        container.getChildren().add(loader.load());

    }

    private void intializeSliderMenu() {
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburguerMenu);
        sliderMenu.setSidePane(menuLateral);
        sliderMenu.open();
        transition.setRate(1);
        // transition.play();
        hamburguerMenu.setOnMouseClicked(t -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (sliderMenu.isOpened()) {
                sliderMenu.close();
            } else {
                sliderMenu.open();
            }
        });
    }

    /**
     * FIXME: Add catch block
     */
    public void loadView(String option) {
        try {
            if (option.toLowerCase().equals("usermodule")) {
                btnUserModuleAction(null);
            }
            if (option.toLowerCase().equals("doctormodule")) {
                btnDoctorModuleAction(null);
            }
            if (option.toLowerCase().equals("patientmodule")) {
                btnPatientModuleAction(null);
            }
        } catch (IOException e) {
        }
    }

    private void loadPrivileges() {
        if (userLoggued != null && userLoggued.getPasswordChanged().equals("Y")) {
            changePasswordView.setVisible(true);
            menuLateral.setDisable(true);
            hamburguerMenu.setDisable(true);
            profileContainer.setDisable(true);
        } else {
            changePasswordView.setVisible(false);
            menuLateral.setDisable(false);
            hamburguerMenu.setDisable(false);
        }
    }

    

}
