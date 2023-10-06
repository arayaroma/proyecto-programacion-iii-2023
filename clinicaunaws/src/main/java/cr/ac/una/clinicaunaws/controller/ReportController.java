/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.clinicaunaws.controller;

import cr.ac.una.clinicaunaws.services.ReportService;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author varga
 */
@Path("/ReportController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportController {
    
    @EJB
    ReportService reportService;

    @Path("/createReport")
    @POST
    @Produces("application/pdf") 
    public Response generarInforme(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = reportService.createReport(id);

            // Devolver el informe en la respuesta
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            // Manejar la excepción (log, enviar alerta, etc.)
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al generar el informe").type(MediaType.TEXT_PLAIN).build();
        }
    }

}
