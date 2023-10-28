package cr.ac.una.clinicauna.services;

import cr.ac.una.clinicauna.util.Request;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import jakarta.ws.rs.core.GenericType;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.awt.Desktop;
import java.util.List;
import cr.ac.una.clinicauna.model.ReportDto;
import cr.ac.una.clinicauna.util.FileLoader;

/**
 *
 * @author vargas
 * @author arayaroma
 */
public class ReportService {

    public ResponseWrapper createReport(ReportDto reportDto) {
        try {
            Request request = new Request("ReportController/create");
            request.post(reportDto);
            if (request.isError()) {
                return new ResponseWrapper(
                        ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR,
                        "Error in the request: " + request.getError(),
                        null);
            }
            ReportDto report = (ReportDto) request.readEntity(ReportDto.class);
            return new ResponseWrapper(
                    ResponseCode.CREATED.getCode(),
                    ResponseCode.CREATED,
                    "Report created successfully: ",
                    report);
        } catch (Exception ex) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Error in the service: " + ex.toString(),
                    null);
        }
    }

    public ResponseWrapper getReport(Long id) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);
            Request request = new Request("ReportController/report", "/{id}", params);
            request.get();
            if (request.isError()) {
                return new ResponseWrapper(
                        ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR,
                        "Error in the request: " + request.getError(),
                        null);
            }
            ReportDto report = (ReportDto) request.readEntity(ReportDto.class);
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Report retrieved successfully: ",
                    report);
        } catch (Exception ex) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Error in the service: " + ex.toString(),
                    null);
        }
    }

    @SuppressWarnings("unchecked")
    public ResponseWrapper getAllReports() {
        try {
            Request request = new Request("ReportController/report");
            request.get();
            if (request.isError()) {
                return new ResponseWrapper(
                        ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR,
                        "Error in the request: " + request.getError(),
                        null);
            }
            List<ReportDto> reportDtos = (List<ReportDto>) request.readEntity(new GenericType<List<ReportDto>>() {
            });
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Reports retrieved successfully: ",
                    reportDtos);
        } catch (Exception ex) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Error in the service: " + ex.toString(),
                    null);
        }
    }

    public ResponseWrapper updateReport(ReportDto reportDto) {
        try {
            Request request = new Request("ReportController/update");
            request.put(reportDto);
            if (request.isError()) {
                return new ResponseWrapper(
                        ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR,
                        "Error in the request: " + request.getError(),
                        null);
            }
            ReportDto report = (ReportDto) request.readEntity(ReportDto.class);
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Report updated successfully: ",
                    report);
        } catch (Exception ex) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Error in the service: " + ex.toString(),
                    null);
        }
    }

    public ResponseWrapper deleteReport(Long id) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);
            Request request = new Request("ReportController/delete", "/{id}", params);
            request.delete();
            if (request.isError()) {
                return new ResponseWrapper(
                        ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR,
                        "Error in the request: " + request.getError(),
                        null);
            }
            return new ResponseWrapper(
                    ResponseCode.NO_CONTENT.getCode(),
                    ResponseCode.NO_CONTENT,
                    "Report deleted successfully: ",
                    null);
        } catch (Exception ex) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Error in the service: " + ex.toString(),
                    null);
        }
    }

    public ResponseWrapper createPatientReport(Long id) {
        try {
            String path = FileLoader.chooseSavePath();
            if (path == null || path.isBlank()) {
                return new ResponseWrapper(ResponseCode.CONFLICT.getCode(), ResponseCode.CONFLICT, "Path is required",
                        null);
            }
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);
            Request request = new Request("ReportController/createPatientReport", "/{id}", params);
            request.get();
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            byte[] pdf = (byte[]) request.readEntity(byte[].class);
            if (FileLoader.createFile(path, pdf)) {
                Desktop.getDesktop().open(new File(path));
                return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "Report created successfully: ",
                        pdf);
            }
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR, "Error creating the PDF",
                    null);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR,
                    "Error in the service: " + ex.toString(), null);
        }
    }

    public ResponseWrapper createAgendaReport(Long DoctorId, String sDate, String eDate) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("doctorId", DoctorId);
            params.put("startDate", sDate);
            params.put("endDate", eDate);
            Request request = new Request("ReportController/createAgendaReport", "/{doctorId}/{startDate}/{endDate}",
                    params);
            request.get();

            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: "
                                + request.getError(),
                        null);
            }

            byte[] pdf = (byte[]) request.readEntity(byte[].class);
            // ESTA CREANDO EL PDF EN EL DIRECTORIO RAIZ
            try (InputStream b = new ByteArrayInputStream(pdf); OutputStream out = new FileOutputStream("new.pdf")) {

                int tamInput;
                byte[] datosPdf = new byte[1024];

                while ((tamInput = b.read(datosPdf)) != -1) {
                    out.write(datosPdf, 0, tamInput);
                }

                System.out.println("Archivo PDF creado exitosamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Desktop.getDesktop().open(new File("new.pdf"));

            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "Report created successfully: ",
                    pdf);

        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR,
                    "Error in the service: " + ex.toString(), null);
        }
    }

}
