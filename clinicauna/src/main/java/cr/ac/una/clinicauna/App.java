package cr.ac.una.clinicauna;

import cr.ac.una.clinicauna.controller.UserRegisterController;
import cr.ac.una.clinicauna.util.Data;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.scene.Parent;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static final String DOMAIN_PATH = "/cr/ac/una/clinicauna/";

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = getFXMLLoader("Login").load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFXMLLoader(fxml).load());
    }

    public static void setRoot(FXMLLoader loader) throws IOException {
        scene.setRoot(loader.load());
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        if (Data.languageOption.equals("en")) {
            return new FXMLLoader(App.class.getResource(DOMAIN_PATH + "view/" + fxml + ".fxml"), Data.getEnglishBundle());
        }
        return new FXMLLoader(App.class.getResource(DOMAIN_PATH + "view/" + fxml + ".fxml"), Data.getSpanishBundle());

    }

    public static void main(String[] args) {
        launch();
    }

}
