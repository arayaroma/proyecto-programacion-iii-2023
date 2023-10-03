package cr.ac.una.clinicauna;

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
        Parent root = getFXMLLoaderWithLanguage("Login", ResourceBundle.getBundle(DOMAIN_PATH + "language/lang_es")).load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        //Load English default
        scene.setRoot(getFXMLLoaderWithLanguage(fxml, Data.getEnglishBundle()).load());
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(DOMAIN_PATH + "view/" + fxml + ".fxml"));
    }

    public static FXMLLoader getFXMLLoaderWithLanguage(String fxml, ResourceBundle rb) {
        return new FXMLLoader(App.class.getResource(DOMAIN_PATH + "view/" + fxml + ".fxml"), rb);
    }

    public static void main(String[] args) {
        launch();
    }

}
