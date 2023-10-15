/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.clinicauna.services;

import cr.ac.una.clinicauna.util.Request;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author vargas
 */
public class ReportService {
    //FALTA AGREGAR QUE SE ABRA EL REPORTE
    public ResponseWrapper createPatientReport(Long id) {
        try {
            HashMap params = new HashMap();
            params.put("id", id);
            Request request = new Request("ReportController/createPatientReport", "/{id}", params);
            request.get();

            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: "
                        + request.getError(),
                        null);
            }

            byte[] pdf = (byte[]) request.readEntity(byte[].class);
            //ESTA CREANDO EL PDF EN EL DIRECTORIO RAIZ
            try (InputStream b = new ByteArrayInputStream(pdf); OutputStream out = new FileOutputStream("new.pdf" )) {

                int tamInput;
                byte[] datosPdf = new byte[1024]; 

                while ((tamInput = b.read(datosPdf)) != -1) {
                    out.write(datosPdf, 0, tamInput);
                }

                System.out.println("Archivo PDF creado exitosamente.");
            } catch (IOException e) {
                e.printStackTrace(); 
            }

            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "Report created successfully: ",
                    pdf);

        } catch (Exception ex) {
            System.out.println("Error in the service: " + ex.toString());
            return null;
        }
    }
    
        public ResponseWrapper createAgendaReport(Long DoctorId, String sDate, String eDate) {
        try {
            HashMap params = new HashMap();
            params.put("id", DoctorId);
            params.put("startDate", sDate);
            params.put("endDate", eDate);
            Request request = new Request("ReportController/createAgendaReport", "/{doctorId},{startDate},{endDate}", params);
            request.get();

            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                        ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: "
                        + request.getError(),
                        null);
            }

            byte[] pdf = (byte[]) request.readEntity(byte[].class);
            //ESTA CREANDO EL PDF EN EL DIRECTORIO RAIZ
            try (InputStream b = new ByteArrayInputStream(pdf); OutputStream out = new FileOutputStream("new.pdf" )) {

                int tamInput;
                byte[] datosPdf = new byte[1024]; 

                while ((tamInput = b.read(datosPdf)) != -1) {
                    out.write(datosPdf, 0, tamInput);
                }

                System.out.println("Archivo PDF creado exitosamente.");
            } catch (IOException e) {
                e.printStackTrace(); 
            }

            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "Report created successfully: ",
                    pdf);

        } catch (Exception ex) {
            System.out.println("Error in the service: " + ex.toString());
            return null;
        }
    }


}
