/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.clinicaunaws.services;

import cr.ac.una.clinicaunaws.dto.UserDto;
import cr.ac.una.clinicaunaws.util.ResponseCode;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author varga
 */
@Stateless
@LocalBean
public class ReportService {

    public ResponseWrapper createReport(Long id) throws IOException {
        try {
            // Cargar el diseño del informe desde un archivo Jasper en el directorio resources
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject( getClass().getResource(/*"/cr/ac/una/clinicaunaws/reports/medicalRecord.jrxml"*/"C:\\Users\\varga\\Desktop\\ClinicaUna\\proyecto-programacion-iii-2023\\clinicaunaws\\src\\main\\resources\\cr\\ac\\una\\clinicaunaws\\reports\\medicalRecord.jrxml"));
            
            if(jasperReport==null){
                System.out.println("ERROR NO EXISTE EL JASPER");
            }

            // Crear un HashMap para el parámetro
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idPatientCare", id);

            // Configurar la conexión a la base de datos Oracle
            String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String usuario = "ClinicaUNA";
            String contraseña = "una";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, usuario, contraseña)) {
                // Llenar el informe con datos y la conexión a la base de datos
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, connection);

                // Convertir el informe a bytes (PDF en este caso)
                return new ResponseWrapper(
                        ResponseCode.OK.getCode(),
                        ResponseCode.OK,
                        "User created successfully.",
                        JasperExportManager.exportReportToPdf(jasperPrint));
            }
        } catch (JRException | SQLException e) {
            // Manejar la excepción (log, enviar alerta, etc.)
            e.printStackTrace();
            throw new RuntimeException("Error al generar el informe", e);
        }

    }

}
