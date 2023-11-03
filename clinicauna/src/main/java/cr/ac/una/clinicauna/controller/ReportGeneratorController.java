package cr.ac.una.clinicauna.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.model.ReportDto;
import cr.ac.una.clinicauna.model.ReportRecipientsDto;
import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.services.ReportService;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

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
    @FXML
    private TableView<ObservableList<Object>> tvQueryResult;
    @FXML
    private HBox hbQueryResult;

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

    // @SuppressWarnings("unchecked")
    private void sendReport(ReportDto report) {
        ResponseWrapper response = reportService.createReport(report);
        if (response.getCode() == ResponseCode.CREATED) {
            report = (ReportDto) response.getData();
            List<?> users = report.getQueryManager().getResult();

            report.getQueryManager().setResult(users);
            report.getQueryManager().setStatus(response.getMessage());
            System.out.println(report.toString());
            addQueryResultToTableView(hbQueryResult, users);
        }
    }

    private void addQueryResultToTableView(HBox container, List<?> queryResult) {
        ObservableList<ObservableList<Object>> data = FXCollections.observableArrayList();

        for (Object innerList : queryResult) {
            if (innerList instanceof List<?>) {
                List<?> rowList = (List<?>) innerList;
                ObservableList<Object> row = FXCollections.observableArrayList(rowList);
                data.add(row);
            }
        }
        tvQueryResult.getColumns().clear();

        int numColumns = data.isEmpty() ? 0 : data.get(0).size();
        for (int columnIndex = 0; columnIndex < numColumns; columnIndex++) {
            TableColumn<ObservableList<Object>, Object> column = new TableColumn<>("Column " + (columnIndex + 1));
            final int index = columnIndex;
            column.setCellValueFactory(param -> {
                ObservableList<Object> row = param.getValue();
                return new SimpleObjectProperty<>(row.get(index));
            });
            tvQueryResult.getColumns().add(column);
        }

        ScrollPane scrollPane = new ScrollPane(tvQueryResult);
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPrefViewportWidth(400);
        container.getChildren().add(scrollPane);

        tvQueryResult.setItems(data);
        System.out.println("Response\n" + queryResult.toString());
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
