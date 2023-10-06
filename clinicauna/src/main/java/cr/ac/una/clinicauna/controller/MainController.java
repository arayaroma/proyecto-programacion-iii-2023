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
    private UserDto userLoggued;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Data.setData("mainController", this);
            userLoggued = (UserDto) Data.getData("userLoggued");
            lblUserLoggued.setText(userLoggued.getName());
            intializeSliderMenu();
            imgProfilePhoto.setClip(new Circle(imgProfilePhoto.getFitWidth() / 2, imgProfilePhoto.getFitHeight() / 2, 30));
            imgProfilePhoto.setImage(ImageLoader.setImage(userLoggued.getProfilePhoto()));
            if (userLoggued.getPasswordChanged().equals("Y")) {
                changePasswordView.setVisible(true);
                menuLateral.setDisable(true);
                hamburguerMenu.setDisable(true);
            } else {
                changePasswordView.setVisible(false);
                menuLateral.setDisable(false);
                hamburguerMenu.setDisable(false);
            }
        } catch (Exception e) {
            Animation.MakeDefaultFadeTransition(parent, "Login");
            System.out.println(e.toString());

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

    @FXML
    private void btnLogOutAction(ActionEvent event) {
        Animation.MakeDefaultFadeTransition(parent, "Login");
    }

    @FXML
    private void editUserLogguedAction(MouseEvent event) {
        Data.setData("userBuffer", userLoggued);
        Animation.MakeDefaultFadeTransition(parent, "UserRegister");
    }

    @FXML
    private void discardChangesAction(ActionEvent event) {
        Animation.MakeDefaultFadeTransition(parent, "Login");
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

    private void intializeSliderMenu() {
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburguerMenu);
        sliderMenu.setSidePane(menuLateral);
        sliderMenu.open();
        transition.setRate(1);
        transition.play();
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

}
