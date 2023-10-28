package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.DoctorService;
import cr.ac.una.clinicauna.services.UserService;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.FileLoader;
import cr.ac.una.clinicauna.util.ImageLoader;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.io.File;
import java.util.Objects;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author estebannajera
 * @author arayaroma
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
    private ToggleGroup roleGroup;
    @FXML
    private ComboBox<String> cbLanguage;
    @FXML
    private HBox parent;
    @FXML
    private ImageView imgPhotoProfile;
    private UserService userService = new UserService();
    private UserDto userModified = new UserDto();
    private DoctorService doctorService = new DoctorService();
    private boolean isFromDoctorModule = false;
    private boolean isEditing = false;
    private Data data = Data.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserDto userDto = (UserDto) data.getData("userBuffer");
        userModified = userDto != null ? userDto : new UserDto();
        

        isEditing = userModified.getId() != null;
        if (Data.languageOption.equals("en")) {
            cbLanguage.getItems().addAll("English", "Spanish");
        } else {
            cbLanguage.getItems().addAll("Español", "Inglés");
        }
        bindUser();
    }

    @FXML
    private void backFromRegister(MouseEvent event) throws IOException {
        try {
            data.removeData("userBuffer");
            updateUserLoggued();
            FXMLLoader loader = App.getFXMLLoader("Main");
            Animation.MakeDefaultFadeTransition(mainView, loader.load());
            MainController controller = loader.getController();
            if (controller != null) {
                if (isFromDoctorModule) {
                    controller.loadView("doctormodule");
                    return;

                }
                controller.loadView("usermodule");
            }
        } catch (IOException e) {
        }

    }

    @FXML
    private void btnRegisterUserAction(ActionEvent event) throws IOException {
        if (verifyFields()) {
            setPrivilegesUser(userModified);

            if (userModified.getRole().toLowerCase().equals("doctor")) {// Verifiy if is a Doctor
                data.setData("userBuffer", userModified);
                Animation.MakeDefaultFadeTransition(mainView, App.getFXMLLoader("DoctorRegister").load());
                return;
            } else {
                saveUser(userModified);
                doctorService.deleteDoctor(userModified.getId());
            }
            backFromRegister(null);

        } else {
            Message.showNotification("Ups", MessageType.ERROR, "fieldsEmpty");
        }
    }

    @FXML
    private void btnSelectPhotoProfileAction(ActionEvent event) {
        File file = FileLoader.selectFile("Image", "*.png", "*.jpeg", "*.jpeg");
        if (file != null) {
            userModified.setProfilePhoto(ImageLoader.imageToByteArray(file));
            imgPhotoProfile.setImage(ImageLoader.setImage(file));
        }
    }

    private void updateUserLoggued() {
        UserDto userLoggued = (UserDto) data.getData("userLoggued");
        if (userLoggued != null) {
            if (Objects.equals(userModified.getId(), userLoggued.getId())) {
                data.removeData("userLoggued");
                userModified = (UserDto) userService.findUserById(userModified.getId()).getData();
                data.setData("userLoggued", userModified);
            }
        }
    }

    public void saveUser(UserDto user) throws IOException {
        ResponseWrapper response = user.getId() == null ? userService.createUser(user)
                : userService.updateUser(user);
        if (response.getCode() == ResponseCode.OK) {
            Message.showNotification("Success", MessageType.CONFIRMATION, response.getMessage());
            unbindUser();
            user = (UserDto) response.getData();
            return;
        }
        Message.showNotification("Ups", MessageType.ERROR, response.getMessage());
        System.out.println(response.getMessage());
    }

    private void setPrivilegesUser(UserDto userDto) {
        userDto.setLanguage(userModified.parseLanguage(userModified.getLanguage()));
        userDto.setRole(userModified.parseRole(userModified.getRole().toLowerCase()));
        if (userDto.getRole().toLowerCase().equals("administrator")) {
            userDto.setIsAdmin("Y");
        } else {
            userDto.setIsAdmin("N");
        }
        if (userDto.getId() == null) {
            userDto.setIsActive("N");
            userDto.setPasswordChanged("N");
        }

    }

    private void bindUser() {
        try {
            txfName.textProperty().bindBidirectional(userModified.name);
            txfLastName.textProperty().bindBidirectional(userModified.firstLastname);
            txfSencondLastName.textProperty().bindBidirectional(userModified.secondLastname);
            txfEmail.textProperty().bindBidirectional(userModified.email);
            txfIdentification.textProperty().bindBidirectional(userModified.identification);
            txfPassword.textProperty().bindBidirectional(userModified.password);
            txfUsername.textProperty().bindBidirectional(userModified.username);
            txfPhoneNumber.textProperty().bindBidirectional(userModified.phoneNumber);
            cbLanguage.valueProperty().bindBidirectional(userModified.language);
            roleGroup.selectedToggleProperty()
                    .addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
                        if (newValue != null && userModified != null) {
                            userModified.setRole(((RadioButton) newValue).getText());
                        }
                    });

            if (isEditing) {
                roleGroup.getToggles().forEach(t -> {
                    if (t instanceof RadioButton) {
                        if (userModified.getRole().toLowerCase().equals("administrator")) {
                            if (((RadioButton) t).getText().toLowerCase().equals("admin")
                                    || ((RadioButton) t).getText().toLowerCase().equals("administrador")) {
                                t.setSelected(true);
                            }
                        } else if (userModified.getRole().toLowerCase().equals("recepcionist")) {
                            if (((RadioButton) t).getText().toLowerCase().equals("recepcionist")
                                    || ((RadioButton) t).getText().toLowerCase().equals("recepcionista")) {
                                t.setSelected(true);
                            }
                        } else if (userModified.getRole().toLowerCase().equals("doctor")) {
                            if (((RadioButton) t).getText().toLowerCase().equals("doctor")) {
                                t.setSelected(true);
                            }
                        }
                    }
                });
                if (userModified.getProfilePhoto() != null) {
                    imgPhotoProfile.setImage(ImageLoader.setImage(userModified.getProfilePhoto()));
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void unbindUser() {
        try {
            txfName.textProperty().unbindBidirectional(userModified.name);
            txfLastName.textProperty().unbindBidirectional(userModified.firstLastname);
            txfSencondLastName.textProperty().unbindBidirectional(userModified.secondLastname);
            txfEmail.textProperty().unbindBidirectional(userModified.email);
            txfIdentification.textProperty().unbindBidirectional(userModified.identification);
            txfPassword.textProperty().unbindBidirectional(userModified.password);
            txfUsername.textProperty().unbindBidirectional(userModified.username);
            txfPhoneNumber.textProperty().unbindBidirectional(userModified.phoneNumber);
            cbLanguage.valueProperty().unbindBidirectional(userModified.language);
            roleGroup.selectedToggleProperty().addListener((t) -> {
            });
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private boolean verifyFields() {
        List<Node> fields = Arrays.asList(txfName, txfLastName, txfSencondLastName, txfEmail, txfIdentification,
                txfPassword, txfUsername, txfPhoneNumber, cbLanguage);
        for (Node i : fields) {
            if (i instanceof JFXTextField && ((JFXTextField) i).getText() != null
                    && ((JFXTextField) i).getText().isBlank()) {
                return false;
            } else if (i instanceof ComboBox && ((ComboBox) i).getValue() == null) {
                return false;
            } else if (i instanceof JFXPasswordField && ((JFXPasswordField) i).getText() != null
                    && ((JFXPasswordField) i).getText().isBlank()) {
                return false;
            }
        }
        return roleGroup.getSelectedToggle() != null;
    }

    public void loadFlags(boolean isFromDoctorModule) {
        this.isFromDoctorModule = isFromDoctorModule;
    }
}
