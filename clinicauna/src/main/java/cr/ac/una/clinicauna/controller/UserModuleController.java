package cr.ac.una.clinicauna.controller;

import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.UserDto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbSearchParameter.getItems().addAll("Name", "Last Name", "Second Last Name", "Identification", "Role");
    }

    @FXML
    private void btnNewUserAction(ActionEvent event) throws IOException {
        Animation.fadeTransition(parent, Duration.seconds(0.5), 0, 1, 0, (t) -> {
            try {
                App.setRoot("UserRegister");

            } catch (IOException ex) {
            }
        }).play();
    }

    @FXML
    private void btnEditUserAction(ActionEvent event) {
    }

    @FXML
    private void btnDeleteUserAction(ActionEvent event) {
    }

    private void initializeList() {
        tcIdentification.setCellValueFactory(new PropertyValueFactory<>("identification"));
        tcUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
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
