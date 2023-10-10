package cr.ac.una.clinicaunaws.services;

import cr.ac.una.clinicaunaws.util.ResponseCode;
import net.sf.jasperreports.engine.*;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import cr.ac.una.clinicaunaws.util.Constants;

/**
 *
 * @author varga
 */
@Stateless
@LocalBean
public class ReportService {
    
    
    /**FIXME: add returns null with errors
     * @param id patient id to be retrieved
     * @return ResponseWrapper with the response and report from database, or null if an
     *         exception occurred
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     */
    public ResponseWrapper createReport(Long id) throws IOException, JRException {
        //FALTA HACER REFRACTOR A ESTE CODIGO
        try {
            // Ruta al archivo JRXML CAMBIAR ESTO 
            String jasperPath = "C:\\Users\\varga\\Desktop\\ClinicaUna\\proyecto-programacion-iii-2023\\clinicaunaws\\src\\main\\resources\\cr\\ac\\una\\clinicaunaws\\reports\\medicalRecord.jrxml";
            JasperReport jReport = JasperCompileManager.compileReport(jasperPath);

            if (jReport == null) {
                System.out.println("ERROR: NO EXISTE EL JASPER");
            }

            Map<String, Object> par = new HashMap<>();
            par.put("idPatientCare", id);

            try (Connection connection = DriverManager.getConnection(Constants.URL_DB, Constants.USER_DB, Constants.PASS_DB)) {
                // Llenar el informe con datos y la conexión a la base de datos
                JasperPrint jasperPrint = JasperFillManager.fillReport(jReport, par, connection);
                byte[] pdfbytes = JasperExportManager.exportReportToPdf(jasperPrint);
                return new ResponseWrapper(
                        ResponseCode.OK.getCode(),
                        ResponseCode.OK,
                        "Report created successfully.",
                        pdfbytes);
            } catch (SQLException e) {
                return new ResponseWrapper(
                        ResponseCode.CONFLICT.getCode(),
                        ResponseCode.CONFLICT,
                        "No conected.",
                        null);
            }
        } catch (JRException e) {
            // Manejar la excepción (log, enviar alerta, etc.)
            e.printStackTrace();
            throw new RuntimeException("Error al generar el informe", e);
        }
    }

}
