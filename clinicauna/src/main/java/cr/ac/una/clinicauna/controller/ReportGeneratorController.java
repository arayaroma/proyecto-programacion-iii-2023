package cr.ac.una.clinicauna.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.model.ReportDto;
import cr.ac.una.clinicauna.model.ReportRecipientsDto;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.ReportService;
import cr.ac.una.clinicauna.util.QueryManager;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * 
 * @author arayaroma
 */
public class ReportGeneratorController implements Initializable {

    @FXML
    private Label lbReportName;
    @FXML
    private JFXTextField tfReportName;
    @FXML
    private Label lbReportDescription;
    @FXML
    private JFXTextField tfReportDescription;
    @FXML
    private Label lbReportQuery;
    @FXML
    private JFXTextField tfReportQuery;
    @FXML
    private DatePicker dpReportDate;
    @FXML
    private ComboBox<String> cbReportFrequency;
    @FXML
    private Label lbRecipientName;
    @FXML
    private JFXTextField tfRecipientEmail;
    @FXML
    private JFXButton btGenerateReport;
    @FXML
    private Label lbRecipientEmail;

    private final String[] frequencies = { "ONCE", "DAILY", "WEEKLY", "MONTHLY", "ANNUALLY" };
    private ReportService reportService = new ReportService();
    private ReportDto report = new ReportDto();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFrequencies();
    }

    @FXML
    private void generateReport(ActionEvent event) {
        report = loadReport();
        sendReport(report);
    }

    @SuppressWarnings("unchecked")
    private void sendReport(ReportDto report) {
        ResponseWrapper response = reportService.createReport(report);
        if (response.getCode() == ResponseCode.CREATED) {
            report = (ReportDto) response.getData();
            List<UserDto> users = (List<UserDto>) report.getQueryManager().getResult();

            report.setQueryManager(new QueryManager<UserDto>());
            report.getQueryManager().setResult(users);
            report.getQueryManager().setStatus(response.getMessage());
            System.out.println(report.toString());
        }
    }

    private ReportDto loadReport() {
        ReportRecipientsDto recipient = new ReportRecipientsDto();
        // recipient.setReport(report);
        recipient.setEmail(getRecipientEmail());

        report.setName(getReportName());
        report.setDescription(getReportDescription());
        report.setQuery(getReportQuery());
        report.getQueryManager().setQuery(getReportQuery());

        report.setReportDate(getReportDate());
        report.setFrequency(getReportFrequency());

        report.getReportRecipients().add(recipient);
        System.out.println(report.toString());
        return report;
    }

    private void clearFields() {
        tfReportName.clear();
        tfReportDescription.clear();
        tfReportQuery.clear();
        tfRecipientEmail.clear();
        cbReportFrequency.getSelectionModel().clearSelection();
        dpReportDate.getEditor().clear();
    }

    private void loadFrequencies() {
        cbReportFrequency.getItems().addAll(frequencies);
    }

    private String getReportName() {
        return tfReportName.getText();
    }

    private String getReportDescription() {
        return tfReportDescription.getText();
    }

    private String getReportDate() {
        return dpReportDate.getValue().toString();
    }

    private String getReportFrequency() {
        return cbReportFrequency.getValue();
    }

    private String getRecipientEmail() {
        return tfRecipientEmail.getText();
    }

    private String getReportQuery() {
        return tfReportQuery.getText();
    }

}
