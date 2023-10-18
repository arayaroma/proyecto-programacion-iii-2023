package cr.ac.una.clinicauna.services;

import cr.ac.una.clinicauna.util.Request;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.awt.Desktop;
import cr.ac.una.clinicauna.util.FileLoader;

/**
 *
 * @author vargas
 */
public class ReportService {

    public ResponseWrapper createPatientReport(Long id) {
        try {
            String path = FileLoader.chooseSavePath();
            if (path == null || path.isBlank()) {
                return new ResponseWrapper(ResponseCode.CONFLICT.getCode(), ResponseCode.CONFLICT, "Path is required",
                        null);
            }
            HashMap params = new HashMap();
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
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }

    public ResponseWrapper createAgendaReport(Long DoctorId, String sDate, String eDate) {
        try {
            HashMap params = new HashMap();
            params.put("doctorId", DoctorId);
            params.put("startDate", sDate);
            params.put("endDate", eDate);
            Request request = new Request("ReportController/createAgendaReport", "/{doctorId}/{startDate}/{endDate}", params);
            request.get();

            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: "
                        + request.getError(),
                        null);
            }

            byte[] pdf = (byte[]) request.readEntity(byte[].class);
            //ESTA CREANDO EL PDF EN EL DIRECTORIO RAIZ
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
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }

}
