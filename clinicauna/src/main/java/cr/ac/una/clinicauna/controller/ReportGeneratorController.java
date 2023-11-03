package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.clinicauna.model.ReportDto;
import cr.ac.una.clinicauna.model.ReportParametersDto;
import cr.ac.una.clinicauna.model.ReportRecipientsDto;
import cr.ac.una.clinicauna.services.ReportService;
import cr.ac.una.clinicauna.util.Message;
import cr.ac.una.clinicauna.util.MessageType;
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
    private ReportDto reportBuffer;
    private ReportParametersDto reportParameterBuffer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbReportFrequency.getItems().addAll("ONCE", "DAILY", "WEEKLY", "MONTHLY", "ANNUALLY");
        reportBuffer = new ReportDto();
        bindReport();
        initializeList();
//        txfParameterValue.setOnKeyPressed(t -> txfParameterValueEvent(t));
    }

    @FXML
    private void btnLoadParametersAction(ActionEvent event) {
        List<String> parameter = extractParameters(reportBuffer.getQuery());
        reportParametersDtos.clear();
        for (String i : parameter) {
            ReportParametersDto reportParametersDto = new ReportParametersDto();
            reportParametersDto.setName(i);
            reportParametersDtos.add(reportParametersDto);
        }
        loadParameters();
    }

    @FXML
    private void btnSaveAction(ActionEvent event) {
        if (!verifyFields()) {
            Message.showNotification("Error", MessageType.INFO, "fieldsEmpty");
            return;
        }

//        ResponseWrapper response = reportService.createReport(report);
//        if (response.getCode() == ResponseCode.CREATED) {
//            report = (ReportDto) response.getData();
//            List<?> users = report.getQueryManager().getResult();
//
//            report.getQueryManager().setResult(users);
//            report.getQueryManager().setStatus(response.getMessage());
//            System.out.println(report.toString());
//        }
    }

    @FXML
    private void btnAddEmailAction(ActionEvent event) {
    }

    @FXML
    private void btnDeleteEmailAction(ActionEvent event) {
    }

    @FXML
    private void btnAddValueParameterAction(ActionEvent event) {
        if (reportParameterBuffer != null) {
            reportParameterBuffer.setValue(txfParameterValue.getText());
//            Integer index = reportParametersDtos.indexOf(reportParameterBuffer);
            reportParametersDtos.remove(reportParameterBuffer);
            reportParametersDtos.add(reportParameterBuffer);
            loadParameters();
        }
    }
//    private void txfParameterValueEvent(KeyEvent event) {

//    }
    private void loadParameters() {
        tbParameters.setItems(FXCollections.observableArrayList(reportParametersDtos));
    }

    private void initializeList() {
        tcParameter.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcValue.setCellValueFactory(new PropertyValueFactory<>("value"));

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

    private void bindReport() {
        txfReportDescription.textProperty().bindBidirectional(reportBuffer.description);
        txfReportName.textProperty().bindBidirectional(reportBuffer.name);
        txfReportQuery.textProperty().bindBidirectional(reportBuffer.query);
        cbReportFrequency.valueProperty().bindBidirectional(reportBuffer.frequency);
    }

    private boolean verifyFields() {
        List<Node> fields = Arrays.asList(txfRecipientEmail, txfReportDescription, txfReportName, txfReportQuery, cbReportFrequency, dpReportDate);
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
