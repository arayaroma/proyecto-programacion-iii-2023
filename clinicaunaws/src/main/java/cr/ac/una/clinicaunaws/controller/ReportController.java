package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.ReportDto;
import cr.ac.una.clinicaunaws.services.ReportService;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author varga
 * @author arayaroma
 */
@Path("/ReportController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "ReportController", description = "Manage endpoints related to the Report.")
public class ReportController {

    private static final Logger logger = Logger.getLogger(ReportController.class.getName());

    @EJB
    ReportService reportService;

    @POST
    @Path("/create")
    public Response createReport(ReportDto reportDto) {
        try {
            ResponseWrapper response = reportService.createReport(reportDto);
            return Response.status(response.getStatus())
                    .entity(response.getData())
                    .build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating the report: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/report/{id}")
    public Response getReport(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = reportService.getReportById(id);
            return Response.status(response.getStatus())
                    .entity(response.getData())
                    .build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error getting the report: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/report")
    public Response getAllReports() {
        try {
            ResponseWrapper response = reportService.getAllReports();
            return Response.status(response.getStatus())
                    .entity(response.getData())
                    .build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error getting the reports: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/update")
    public Response updateReport(ReportDto reportDto) {
        try {
            ResponseWrapper response = reportService.updateReport(reportDto);
            return Response.status(response.getStatus())
                    .entity(response.getData())
                    .build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating the report: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteReport(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = reportService.deleteReport(id);
            return Response.status(response.getStatus())
                    .entity(response.getData())
                    .build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting the report: " + e.getMessage()).build();
        }
    }

    /**
     * FIXME: Finish implementation
     *
     * @param id
     * @return
     */
    @GET
    @Path("/createReport/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response generarInforme(@PathParam("id") Long id) {
        try {
            MediaType contentType;
            ResponseWrapper response = reportService.createReport(id);
            if (response.getData() instanceof byte[]) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_TYPE;
            } else {
                contentType = MediaType.APPLICATION_JSON_TYPE;
            }
            return Response.status(response.getStatus())
                    .entity(response.getData()) // cambio
                    .type(contentType) // cambio
                    .build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al generar el informe").type(MediaType.TEXT_PLAIN).build();
        }
    }

}
