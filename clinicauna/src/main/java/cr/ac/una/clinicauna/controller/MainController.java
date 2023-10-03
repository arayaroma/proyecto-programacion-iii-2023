package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.components.Animation;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            intializeSliderMenu();
            imgProfilePhoto.setClip(new Circle(imgProfilePhoto.getFitWidth() / 2, imgProfilePhoto.getFitHeight() / 2, 40));
        } catch (Exception e) {

            try {
                App.setRoot("Login");
                System.out.println(e.toString());
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void intializeSliderMenu() {
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburguerMenu);
        sliderMenu.setSidePane(menuLateral);
        sliderMenu.open();
        transition.setRate(-1);
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

    @FXML
    private void btnLogOutAction(ActionEvent event) {
        Animation.fadeTransition(parent, Duration.seconds(0.5), 0, 1, 0, (t) -> {
            try {
                App.setRoot("Login");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).play();
    }

    @FXML
    private void btnRegisterUserAction(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("UserModule");
        container.getChildren().clear();
        container.getChildren().add(loader.load());
    }

}
