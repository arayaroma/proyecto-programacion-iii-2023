package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.model.ReportDto;
import cr.ac.una.clinicauna.model.ReportParametersDto;
import cr.ac.una.clinicauna.model.ReportRecipientsDto;
import cr.ac.una.clinicauna.services.ReportParametersService;
import cr.ac.una.clinicauna.services.ReportRecipientsService;
import cr.ac.una.clinicauna.services.ReportService;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author arayaroma
 */
public class ReportGeneratorController implements Initializable {

    @FXML
    private DatePicker dpReportDate;
    @FXML
    private ComboBox<String> cbReportFrequency;
    @FXML
    private TableView<ReportParametersDto> tbParameters;
    @FXML
    private TableColumn<ReportParametersDto, String> tcParameter;
    @FXML
    private TableColumn<ReportParametersDto, String> tcValue;
    @FXML
    private JFXTextField txfParameterValue;
    @FXML
    private JFXTextField txfRecipientEmail;
    @FXML
    private JFXTextField txfReportName;
    @FXML
    private JFXTextField txfReportDescription;
    @FXML
    private JFXTextArea txfReportQuery;
    @FXML
    private TableView<ReportRecipientsDto> tbEmails;
    @FXML
    private TableColumn<ReportRecipientsDto, String> tcEmail;

    private ReportService reportService = new ReportService();
    private List<ReportParametersDto> reportParametersDtos = new ArrayList<>();
    private ReportRecipientsService reportRecipientService = new ReportRecipientsService();
    private ReportParametersService reportParametersService = new ReportParametersService();
    private ReportDto reportBuffer;
    private ReportParametersDto reportParameterBuffer;
    private ReportRecipientsDto reportRecipientBuffer;
    private boolean isEditing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbReportFrequency.getItems().addAll("ONCE", "DAILY", "WEEKLY", "MONTHLY", "ANNUALLY");
        List<ReportDto> reportDtos = (List<ReportDto>) reportService.getAllReports().getData();
        reportBuffer = reportDtos != null && !reportDtos.isEmpty() ? reportDtos.get(0) : new ReportDto();
        isEditing = reportBuffer.getId() != null;
        bindReport();
        initializeList();
    }

    @FXML
    private void btnLoadParametersAction(ActionEvent event) {
        if (!reportBuffer.getQuery().isBlank()) {
            List<String> parameter = extractParameters(reportBuffer.getQuery());
            List<String> alias = extractAlias(reportBuffer.getQuery());
            for (String i : parameter) {
                ReportParametersDto reportParametersDto = new ReportParametersDto();
                reportParametersDto.setName(i);
                reportParametersDtos.add(reportParametersDto);
            }
            loadParameters();
        }
    }

    @FXML
    private void btnSaveAction(ActionEvent event) {
        if (!verifyFields()) {
            Message.showNotification("Error", MessageType.INFO, "fieldsEmpty");
            return;
        }
        ResponseWrapper response = isEditing ? reportService.updateReport(reportBuffer) : reportService.createReport(reportBuffer);
        if (response.getCode() == ResponseCode.OK) {
            reportBuffer = (ReportDto) response.getData();
            Message.showNotification("Success", MessageType.INFO, response.getMessage());
            return;
        }
        Message.showNotification("ERROR", MessageType.ERROR, response.getMessage());

    }

    @FXML
    private void btnAddEmailAction(ActionEvent event) {
        String email = txfRecipientEmail.getText();
        if (!email.isBlank()) {
            ReportRecipientsDto recipientsDto = new ReportRecipientsDto();
            recipientsDto.setEmail(email);
            if (reportRecipientBuffer == null) {
                reportBuffer.getReportRecipients().add(recipientsDto);
            } else {
                reportRecipientBuffer.setEmail(email);
                reportBuffer.getReportRecipients().remove(reportRecipientBuffer);
                reportBuffer.getReportRecipients().add(reportRecipientBuffer);
            }
            loadEmails();
            txfRecipientEmail.setText("");
            reportRecipientBuffer = null;
        }
    }

    @FXML
    private void btnDeleteEmailAction(ActionEvent event) {
        if (reportRecipientBuffer != null) {
            if (reportRecipientBuffer.getId() != null) {
                ResponseWrapper response = reportRecipientService.deleteReportRecipients(reportRecipientBuffer.getId());
                if (response.getCode() == ResponseCode.OK) {
                    reportBuffer.getReportRecipients().remove(reportRecipientBuffer);
                    loadEmails();
                    return;
                }
                Message.showNotification("ERROR", MessageType.ERROR, response.getMessage());
            } else {
                reportBuffer.getReportRecipients().remove(reportRecipientBuffer);
                loadEmails();
            }
        }
    }

    @FXML
    private void btnAddValueParameterAction(ActionEvent event) {
        String value = txfParameterValue.getText();
        if (reportParameterBuffer != null && !value.isBlank()) {
            reportParameterBuffer.setValue(value);
            reportParametersDtos.remove(reportParameterBuffer);
            reportParametersDtos.add(reportParameterBuffer);
            loadParameters();
        }
    }

    private void loadParameters() {
        tbParameters.setItems(FXCollections.observableArrayList(reportParametersDtos));
    }

    private void loadEmails() {
        tbEmails.setItems(FXCollections.observableArrayList(reportBuffer.getReportRecipients()));
    }

    private void initializeList() {
        tcParameter.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbEmails.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    reportRecipientBuffer = newValue;
                    if (reportRecipientBuffer != null) {
                        txfRecipientEmail.setText(reportRecipientBuffer.getEmail());
                    }
                });
        tbParameters.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    reportParameterBuffer = newValue;
                    if (reportParameterBuffer != null) {
                        txfParameterValue.setText(reportParameterBuffer.getValue());
                    }
                });
    }

    private List<String> extractParameters(String query) {
        Pattern pattern = Pattern.compile(":\\w+");
        Matcher matcher = pattern.matcher(query);
        List<String> parameters = new ArrayList<>();
        while (matcher.find()) {
            parameters.add(matcher.group().substring(1));
        }
        return parameters;
    }

    public static List<String> extractAlias(String query) {
        List<String> parameters = new ArrayList<>();
        Pattern pattern = Pattern.compile("SELECT\\s+(.*?)\\s+FROM", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        if (matcher.find()) {
            String selectedValues = matcher.group(1);
            String[] valueArray = selectedValues.split(",");
            for (String value : valueArray) {
                String[] parts = value.trim().split("(?i)\\s+(?i)as\\s+");
                if (parts.length > 1) {
                    parameters.add(parts[1]);
                } else {
                    parameters.add(parts[0]);
                }
            }
        }
        return parameters;
    }

    private void bindReport() {
        txfReportDescription.textProperty().bindBidirectional(reportBuffer.description);
        txfReportName.textProperty().bindBidirectional(reportBuffer.name);
        txfReportQuery.textProperty().bindBidirectional(reportBuffer.query);
        dpReportDate.valueProperty().bindBidirectional(reportBuffer.reportDate);
        cbReportFrequency.valueProperty().bindBidirectional(reportBuffer.frequency);
    }

    private boolean verifyFields() {
        List<Node> fields = Arrays.asList(txfReportDescription, txfReportName, txfReportQuery, cbReportFrequency, dpReportDate);
        for (Node i : fields) {
            if (i instanceof JFXTextField && ((JFXTextField) i).getText() != null
                    && ((JFXTextField) i).getText().isBlank()) {
                return false;
            } else if (i instanceof ComboBox && ((ComboBox) i).getValue() == null) {
                return false;
            } else if (i instanceof DatePicker && ((DatePicker) i).getValue() == null) {
                return false;
            }
        }
        return true;
    }

    //    // @SuppressWarnings("unchecked")
    //    private void sendReport(ReportDto report) {
    //        
    ////            addQueryResultToTableView(hbQueryResult, users);
    //        }
    //    }
    //    private void addQueryResultToTableView(HBox container, List<?> queryResult) {
    //        ObservableList<ObservableList<Object>> data = FXCollections.observableArrayList();
    //
    //        for (Object innerList : queryResult) {
    //            if (innerList instanceof List<?>) {
    //                List<?> rowList = (List<?>) innerList;
    //                ObservableList<Object> row = FXCollections.observableArrayList(rowList);
    //                data.add(row);
    //            }
    //        }
    //        tvQueryResult.getColumns().clear();
    //
    //        int numColumns = data.isEmpty() ? 0 : data.get(0).size();
    //        for (int columnIndex = 0; columnIndex < numColumns; columnIndex++) {
    //            TableColumn<ObservableList<Object>, Object> column = new TableColumn<>("Column " + (columnIndex + 1));
    //            final int index = columnIndex;
    //            column.setCellValueFactory(param -> {
    //                ObservableList<Object> row = param.getValue();
    //                return new SimpleObjectProperty<>(row.get(index));
    //            });
    //            tvQueryResult.getColumns().add(column);
    //        }
    //
    //        ScrollPane scrollPane = new ScrollPane(tvQueryResult);
    //        scrollPane.setPrefViewportHeight(600);
    //        scrollPane.setPrefViewportWidth(400);
    //        container.getChildren().add(scrollPane);
    //
    //        tvQueryResult.setItems(data);
    //        System.out.println("Response\n" + queryResult.toString());
    //    }
//    private ReportDto loadReport() {
//        ReportRecipientsDto recipient = new ReportRecipientsDto();
//        // recipient.setReport(report);
//        recipient.setEmail(getRecipientEmail());
//
//        report.setName(getReportName());
//        report.setDescription(getReportDescription());
//        report.setQuery(getReportQuery());
//        report.getQueryManager().setQuery(getReportQuery());
//
//        report.setReportDate(getReportDate());
//        report.setFrequency(getReportFrequency());
//
//        report.getReportRecipients().add(recipient);
//        return report;
//    }
//
////    private void clearFields() {
////        tfReportName.clear();
////        tfReportDescription.clear();
////        tfReportQuery.clear();
////        tfRecipientEmail.clear();
////        cbReportFrequency.getSelectionModel().clearSelection();
////        dpReportDate.getEditor().clear();
////    }
//    private void loadFrequencies() {
//        cbReportFrequency.getItems().addAll(frequencies);
//    }
//
//    private String getReportName() {
//        return tfReportName.getText();
//    }
//
//    private String getReportDescription() {
//        return tfReportDescription.getText();
//    }
//
//    private String getReportDate() {
//        return dpReportDate.getValue().toString();
//    }
//
//    private String getReportFrequency() {
//        return cbReportFrequency.getValue();
//    }
//
//    private String getRecipientEmail() {
//        return tfRecipientEmail.getText();
//    }
//
//    private String getReportQuery() {
//        return tfReportQuery.getText();
//    }
}
