package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextArea;
import cr.ac.una.clinicauna.components.Animation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author estebannajera
 */
public class PatientCareRegisterController implements Initializable {

    @FXML
    private HBox parent;
    @FXML
    private VBox mainView;
    @FXML
    private ImageView imgPhotoProfile;
    @FXML
    private Spinner<Double> spBloodPresure;
    @FXML
    private Spinner<Double> spHeartRate;
    @FXML
    private Spinner<Double> spWeight;
    @FXML
    private Spinner<Double> spHeight;
    @FXML
    private Spinner<Double> spTemperature;
    @FXML
    private Label lblBodyMassIndex;
    @FXML
    private JFXTextArea txfCarePlan;
    @FXML
    private JFXTextArea txfPhysicalExam;
    @FXML
    private JFXTextArea txfObservations;
    @FXML
    private JFXTextArea txaTreatmen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initializeSpinners();
        } catch (Exception e) {
            System.out.println(e.toString());
            backFromRegister(null);
        }
    }

    @FXML
    private void backFromRegister(MouseEvent event) {
        Animation.MakeDefaultFadeTransition(mainView, "PatientHistory");
    }

    @FXML
    private void btnRegisterPatientCareAction(ActionEvent event) {
    }

    private void initializeSpinners() {
        spBloodPresure.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(50.0, 250.0, 0.0, 0.1));
        spHeartRate.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(30.0, 250.0, 0.0, 0.1));
        spHeight.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 4.0, 0.0, 0.1));
        spWeight.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 400.0, 0.0, 0.1));
        spTemperature.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(20.0, 50.0, 0.1));
        StringConverter<Double> formatter = new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return String.format("%.2f", object);
            }

            @Override
            public Double fromString(String string) {
                try {
                    return Double.valueOf(string);
                } catch (NumberFormatException e) {
                    return 0D;
                }
            }
        };
        spBloodPresure.getValueFactory().setConverter(formatter);
        spHeartRate.getValueFactory().setConverter(formatter);
        spHeight.getValueFactory().setConverter(formatter);
        spWeight.getValueFactory().setConverter(formatter);
        spTemperature.getValueFactory().setConverter(formatter);
        spHeight.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                calculateIMC();
            }

        });
        spWeight.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                calculateIMC();
            }

        });
    }

    private void calculateIMC() {
        double weight = spWeight.getValue();
        double height = spHeight.getValue();
        double imc = weight / (height * height);
        lblBodyMassIndex.setText(String.format("%.2f", imc));
    }

}
