package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.services.ReportService;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author varga
 */
@Path("/ReportController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "ReportController", description = "Manage endpoints related to the Report.")
public class ReportController {

    private static final Logger logger = Logger.getLogger(ReportController.class.getName());

    @EJB
    ReportService reportService;

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
                    .entity(response.getData()) //cambio
                    .type(contentType) //cambio
                    .build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al generar el informe").type(MediaType.TEXT_PLAIN).build();
        }
    }

}
