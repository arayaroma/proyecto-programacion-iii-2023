package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import cr.ac.una.clinicauna.components.Animation;
import cr.ac.una.clinicauna.model.PatientCareDto;
import cr.ac.una.clinicauna.model.PatientPersonalHistoryDto;
import cr.ac.una.clinicauna.services.PatientCareService;
import cr.ac.una.clinicauna.services.PatientPersonalHistoryService;
import cr.ac.una.clinicauna.util.Data;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private JFXTextArea txfTreatment;
    
    private PatientCareDto patientCareBuffer;
    private PatientPersonalHistoryDto patientPersonalHistoryDto;
    private PatientCareService patientCareService = new PatientCareService();
    private PatientPersonalHistoryService personalHistoryService = new PatientPersonalHistoryService();
    private boolean isEditing;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            patientCareBuffer = (PatientCareDto) Data.getData("patientCareBuffer");
            patientPersonalHistoryDto = (PatientPersonalHistoryDto) Data.getData("patientPersonalHistoryBuffer");
            patientPersonalHistoryDto = (PatientPersonalHistoryDto) personalHistoryService.getPatientPersonalHistoryById(patientPersonalHistoryDto.getId()).getData();
            isEditing = patientCareBuffer != null;
            if (patientCareBuffer == null) {
                patientCareBuffer = new PatientCareDto();
            }
            initializeSpinners();
            bindPatientCare();
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
        if (!verifyFields()) {
            Message.showNotification("Ups", MessageType.INFO, "All the fields are required");
            return;
        }
        setParameters(patientCareBuffer);
        patientCareBuffer.setPatientHistory(patientPersonalHistoryDto.convertFromDTOToGenerated(patientPersonalHistoryDto, patientPersonalHistoryDto));
        patientCareBuffer.setPatientCareDate(LocalDate.now().toString());
        ResponseWrapper response = isEditing ? patientCareService.updatePatientCare(patientCareBuffer)
                : patientCareService.createPatientCare(patientCareBuffer);
        if (response.getCode() != ResponseCode.OK) {
            Message.showNotification(response.getCode().name(), MessageType.ERROR, response.getMessage());
        }
        Message.showNotification(response.getCode().name(), MessageType.INFO, response.getMessage());
        
    }
    
    private void setParameters(PatientCareDto patientCareDto) {
        patientCareDto.setBloodPressure(spBloodPresure.getEditor().getText());
        patientCareDto.setBodyMassIndex(lblBodyMassIndex.getText());
        patientCareDto.setHeartRate(spHeartRate.getEditor().getText());
        patientCareDto.setHeight(spHeight.getEditor().getText());
        patientCareDto.setWeight(spWeight.getEditor().getText());
        patientCareDto.setTemperature(spTemperature.getEditor().getText());
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
    
    private boolean verifyFields() {
        List<Node> fields = Arrays.asList(txfCarePlan, txfObservations, txfPhysicalExam, txfTreatment, spBloodPresure,
                spHeartRate, spHeight, spTemperature, spWeight);
        for (Node i : fields) {
            if (i instanceof JFXTextField && ((JFXTextField) i).getText() != null
                    && ((JFXTextField) i).getText().isBlank()) {
                return false;
            } else if (i instanceof JFXTextArea && ((JFXTextArea) i).getText() != null
                    && ((JFXTextArea) i).getText().isBlank()) {
                return false;
            } else if (i instanceof Spinner && ((Spinner) i).getValue() == null) {
                return false;
            }
        }
        return true;
    }
    
    private void bindPatientCare() {
        String bloodPressure = patientCareBuffer.getBloodPressure(), heartRate = patientCareBuffer.getHeartRate(),
                temperature = patientCareBuffer.getTemperature(), weight = patientCareBuffer.getWeight(),
                height = patientCareBuffer.getHeight();
        if (bloodPressure != null) {
            spBloodPresure.getEditor().setText(patientCareBuffer.getBloodPressure());
        }
        if (heartRate != null) {
            spHeartRate.getEditor().setText(patientCareBuffer.getHeartRate());
        }
        if (height != null) {
            spHeight.getEditor().setText(patientCareBuffer.getHeight());
        }
        if (temperature != null) {
            spTemperature.getEditor().setText(patientCareBuffer.getTemperature());
        }
        if (weight != null) {
            spWeight.getEditor().setText(patientCareBuffer.getWeight());
        }
        txfTreatment.textProperty().bindBidirectional(patientCareBuffer.treatment);
        txfCarePlan.textProperty().bindBidirectional(patientCareBuffer.carePlan);
        txfObservations.textProperty().bindBidirectional(patientCareBuffer.observations);
        txfPhysicalExam.textProperty().bindBidirectional(patientCareBuffer.physicalExam);
    }
    
    private void calculateIMC() {
        double weight = spWeight.getValue();
        double height = spHeight.getValue();
        
        double imc = weight / (height * height);
        if (height == 0) {
            imc = 0d;
        }
        lblBodyMassIndex.setText(String.format("%.2f", imc));
    }
    
}