package cr.ac.una.clinicauna.controller;

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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

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
    @FXML
    private JFXTextField txfSearchUser;
    private UserDto userBuffer;
    private UserService userService = new UserService();
    private List<UserDto> userDtos = new ArrayList<>();

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
        userDtos = (List<UserDto>) userService.getUsers().getData();
        loadUsers(userDtos);
        txfSearchUser.setOnKeyPressed(e -> searchUserAction(e));
    }

    @FXML
    private void btnNewUserAction(ActionEvent event) throws IOException {
        Animation.MakeDefaultFadeTransition(parent, "UserRegister");
    }

    @FXML
    private void btnEditUserAction(ActionEvent event) {
        Data.setData("userBuffer", userBuffer);
        Animation.MakeDefaultFadeTransition(parent, "UserRegister");
    }

    private void searchUserAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String key = txfSearchUser.getText(), parameterKey = cbSearchParameter.getValue();
            if (key.isBlank() || parameterKey == null) {
                loadUsers(userDtos);
                return;
            }
            loadUsers(filterUsers(userDtos, parameterKey, key));
        }
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

    private void loadUsers(List<UserDto> users) {
        if (users != null) {
            tblUsersView.setItems(FXCollections.observableArrayList(users));
        }
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

    private List<UserDto> filterUsers(List<UserDto> users, String parameter, String key) {
        List<UserDto> usersFiltered = new ArrayList<>();
        if (users != null) {
            if (parameter.equals("Name") || parameter.equals("Nombre")) {
                usersFiltered = users
                        .stream()
                        .filter(user -> user.getName().toLowerCase().contains(key.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (parameter.equals("Last Name") || parameter.equals("Apellido")) {
                usersFiltered = users
                        .stream()
                        .filter(user -> user.getFirstLastname().toLowerCase().contains(key.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (parameter.equals("Second Last Name") || parameter.equals("Segundo Apellido")) {
                usersFiltered = users
                        .stream()
                        .filter(user -> user.getSecondLastname().toLowerCase().contains(key.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (parameter.equals("Identification") || parameter.equals("Cédula")) {
                usersFiltered = users
                        .stream()
                        .filter(user -> user.getIdentification().contains(key))
                        .collect(Collectors.toList());
            } else if (parameter.equals("Role") || parameter.equals("Rol")) {
                usersFiltered = users
                        .stream()
                        .filter(user -> user.getRole().toLowerCase().contains(key.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        return usersFiltered;
    }

}
