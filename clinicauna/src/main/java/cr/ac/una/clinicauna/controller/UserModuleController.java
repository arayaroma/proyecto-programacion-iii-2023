package cr.ac.una.clinicauna.controller;

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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class UserModuleController implements Initializable {

    @FXML
    private VBox parent;
    @FXML
    private ComboBox<String> cbSearchParameter;
    @FXML
    private TableView<UserDto> tblUsersView;
    @FXML
    private TableColumn<UserDto, String> tcIdentification;
    @FXML
    private TableColumn<UserDto, String> tcUser;
    @FXML
    private TableColumn<UserDto, String> tcName;
    @FXML
    private TableColumn<UserDto, String> tcLastName;
    @FXML
    private TableColumn<UserDto, String> tcPhone;
    @FXML
    private TableColumn<UserDto, String> tcRole;
    @FXML
    private Button btnEdit;

    private UserDto userBuffer;
    private UserService userService = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Data.getLanguageOption().equals("en")) {
            cbSearchParameter.getItems().addAll("Name", "Last Name", "Second Last Name", "Identification", "Role");
        } else {
            cbSearchParameter.getItems().addAll("Nombre", "Apellido", "Segundo Apellido", "Cédula", "Rol");
        }
        btnEdit.setDisable(true);
        initializeList();
        loadUsers();
    }

    @FXML
    private void btnNewUserAction(ActionEvent event) throws IOException {
        loadRegisterView();
    }

    @FXML
    private void btnEditUserAction(ActionEvent event) {
        loadRegisterView();
    }

    private void loadRegisterView() {
        Animation.fadeTransition(parent, Duration.seconds(0.5), 0, 1, 0, (t) -> {
            try {
                Data.setData("userBuffer", userBuffer);
                MainController mainController = (MainController) Data.getData("mainController");
                mainController.loadRegisterView(App.getFXMLLoader("UserRegister").load());

            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }).play();
    }

    @FXML
    private void btnDeleteUserAction(ActionEvent event) {
        if (userBuffer != null) {
            ResponseWrapper response = userService.deleteUser(userBuffer);
            if (response.getCode() == ResponseCode.OK) {
                tblUsersView.getItems().remove(userBuffer);
            } else {
                Message.showNotification("Error", MessageType.ERROR, response.getMessage());
            }
        }
    }

    private void loadUsers() {
        List<UserDto> userDtos = (List<UserDto>) userService.getUsers().getData();
        tblUsersView.setItems(FXCollections.observableArrayList(userDtos));
    }

    private void initializeList() {
        tcIdentification.setCellValueFactory(new PropertyValueFactory<>("identification"));
        tcUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("firstLastname"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tcRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        tblUsersView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    userBuffer = newValue;
                    if (userBuffer != null) {
                        btnEdit.setDisable(false);
                        return;
                    }
                    btnEdit.setDisable(true);

                });
    }

}
