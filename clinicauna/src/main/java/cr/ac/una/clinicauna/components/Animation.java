package cr.ac.una.clinicauna.components;

import cr.ac.una.clinicauna.App;
import cr.ac.una.clinicauna.controller.LoginController;
import java.lang.ModuleLayer.Controller;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;

/**
 *
 * @author estebannajera
 */
public class Animation {

    public static TranslateTransition translateTransition(Node node, Duration duration, int cicleCount, double byX, double byY, EventHandler<ActionEvent> event) {
        TranslateTransition transition = new TranslateTransition(duration, node);
        transition.setByX(byX);
        transition.setByY(byY);
        transition.setCycleCount(cicleCount);
        transition.setOnFinished(event);
        return transition;
    }

    public static FadeTransition fadeTransition(Node node, Duration duration, int cicleCount, double formValue, double toValue, EventHandler<ActionEvent> event) {
        FadeTransition transition = new FadeTransition(duration, node);
        transition.setFromValue(formValue);
        transition.setToValue(toValue);
        transition.setCycleCount(cicleCount);
        transition.setOnFinished(event);
        return transition;
    }

    /**
     * Transitions to a specific view
     *
     * @param viewName
     */
    public static void MakeDefaultFadeTransition(Node transitionNode, Parent parent) {
        Animation.fadeTransition(transitionNode, Duration.seconds(0.5), 0, 1, 0, (t) -> {
            try {
                App.setRoot(parent);
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).play();
    }
}
