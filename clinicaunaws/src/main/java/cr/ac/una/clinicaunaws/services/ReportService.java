package cr.ac.una.clinicaunaws.services;

import cr.ac.una.clinicaunaws.util.ResponseCode;
import net.sf.jasperreports.engine.*;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static cr.ac.una.clinicaunaws.util.PersistenceContext.PERSISTENCE_UNIT_NAME;
import cr.ac.una.clinicaunaws.dto.ReportDto;
import cr.ac.una.clinicaunaws.entities.Report;
import cr.ac.una.clinicaunaws.util.Constants;
import java.net.URL;
import java.util.Date;

/**
 *
 * @author varga
 * @author arayaroma
 */
@Stateless
@LocalBean
public class ReportService {

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    /**
     * Create a new Report
     *
     * @param reportDto to be created
     * @return ResponseWrapper with the created Report
     */
    public ResponseWrapper createReport(ReportDto reportDto) {
        try {
            Report report = reportDto.convertFromDTOToEntity(reportDto, new Report(reportDto));
            em.persist(report);
            em.flush();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Report created.",
                    reportDto.convertFromEntityToDTO(report, reportDto));
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not create the Report: " + e.getMessage(),
                    null);
        }
    }

    /**
     * get a Report by id
     *
     * @param id of the Report to be retrieved
     * @return ResponseWrapper with the retrieved Report
     */
    public ResponseWrapper getReportById(Long id) {
        try {
            Report report = em.find(Report.class, id);
            if (report == null) {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "Report not found.",
                        null);
            }
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Report retrieved.",
                    new ReportDto(report));
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not retrieve the Report: " + e.getMessage(),
                    null);
        }
    }

    /**
     * get all Reports
     *
     * @return ResponseWrapper with the retrieved Reports
     */
    @SuppressWarnings("unchecked")
    public ResponseWrapper getAllReports() {
        try {

            Query query = em.createNamedQuery("Report.findAll", Report.class);
            List<Report> reportList = (List<Report>) query.getResultList();
            List<ReportDto> reportDtoList = new ArrayList<>();

            for (Report report : reportList) {
                ReportDto reportDto = new ReportDto(report);
                reportDtoList.add(reportDto.convertFromEntityToDTO(report, reportDto));
            }

            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Reports retrieved.",
                    reportDtoList);
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not retrieve the Reports: " + e.getMessage(),
                    null);
        }
    }

    /**
     * update a Report
     *
     * @param reportDto to be updated
     * @return ResponseWrapper with the updated Report
     */
    public ResponseWrapper updateReport(ReportDto reportDto) {
        try {
            Report report = em.find(Report.class, reportDto.getId());
            if (report == null) {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "Report with id: " + reportDto.getId() + " not found",
                        null);
            }
            report.updateReport(reportDto);
            em.merge(report);
            em.flush();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Report updated.",
                    reportDto.convertFromEntityToDTO(report, reportDto));
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not update the Report: " + e.getMessage(),
                    null);
        }
    }

    /**
     * delete a Report by id
     *
     * @param id of the Report to be deleted
     * @return ResponseWrapper with the deleted Report
     */
    public ResponseWrapper deleteReport(Long id) {
        try {
            Report report = em.find(Report.class, id);
            if (report == null) {
                return new ResponseWrapper(
                        ResponseCode.NOT_FOUND.getCode(),
                        ResponseCode.NOT_FOUND,
                        "Report with id: " + id + " not found",
                        null);
            }
            em.remove(report);
            em.flush();
            return new ResponseWrapper(
                    ResponseCode.OK.getCode(),
                    ResponseCode.OK,
                    "Report deleted.",
                    null);
        } catch (Exception e) {
            return new ResponseWrapper(
                    ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR,
                    "Could not delete the Report: " + e.getMessage(),
                    null);
        }
    }

    /**
     * FIXME: add returns null with errors
     *
     * @param id patient id to be retrieved
     * @return ResponseWrapper with the response and report from database, or
     * null if an exception occurred
     * @throws java.io.IOException
     * @throws net.sf.jasperreports.engine.JRException
     */
    public ResponseWrapper createPatientReport(Long id) throws IOException, JRException {
        // FALTA HACER REFRACTOR A ESTE CODIGO
        try {
            String absolutePath = "";
            String jasperPath = "jrxml/medicalRecord.jrxml";
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(jasperPath);

            if (resource == null) {
                System.out.println("No se pudo encontrar el archivo: " + jasperPath);
            } else {
                absolutePath = resource.getFile();
                System.out.println("Ruta absoluta del archivo: " + absolutePath);
            }
                JasperReport jReport = JasperCompileManager.compileReport(absolutePath);
                if (jReport == null) {
                    System.out.println("ERROR: NO EXISTE EL JASPER");
                }

                Map<String, Object> par = new HashMap<>();
                par.put("idPatientCare", id);

                try (Connection connection = DriverManager.getConnection(Constants.URL_DB, Constants.USER_DB,
                        Constants.PASS_DB)) {
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
                            "No connected: " + e.getMessage(),
                            null);
                }
            }catch (JRException e) {
            // Manejar la excepción (log, enviar alerta, etc.)
            e.printStackTrace();
            throw new RuntimeException("Error al generar el informe", e);
        }
        }
    
    

    public ResponseWrapper createAgendaReport(Long doctorId, String startDate, String endDate) throws IOException, JRException {
        // FALTA HACER REFRACTOR A ESTE CODIGO
        try {
            
            String absolutePath = "";
            String jasperPath = "jrxml/agendaReport.jrxml";
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(jasperPath);

            if (resource == null) {
                System.out.println("No se pudo encontrar el archivo: " + jasperPath);
            } else {
                absolutePath = resource.getFile();
                System.out.println("Ruta absoluta del archivo: " + absolutePath);
            }
                JasperReport jReport = JasperCompileManager.compileReport(absolutePath);

            if (jReport == null) {
                System.out.println("ERROR: NO EXISTE EL JASPER");
            }

            Map<String, Object> par = new HashMap<>();
            par.put("doctorId", doctorId);
            par.put("startDate", startDate);
            par.put("endDate", endDate);

            try (Connection connection = DriverManager.getConnection(Constants.URL_DB, Constants.USER_DB,
                    Constants.PASS_DB)) {
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
                        "No connected: " + e.getMessage(),
                        null);
            }
        } catch (JRException e) {
            // Manejar la excepción (log, enviar alerta, etc.)
            e.printStackTrace();
            throw new RuntimeException("Error al generar el informe", e);
        }
    }

}
