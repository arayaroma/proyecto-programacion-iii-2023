package cr.ac.una.clinicauna;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static final String VIEW_PATH = "/cr/ac/una/clinicauna/view/";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getFXMLLoader("Login").load());
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Quicksand:wght@400;700&display=swap");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFXMLLoader(fxml).load());
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(VIEW_PATH + fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }

}
