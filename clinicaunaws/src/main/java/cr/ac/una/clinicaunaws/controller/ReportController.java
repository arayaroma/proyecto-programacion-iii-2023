package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.services.ReportService;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
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
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al generar el informe").type(MediaType.TEXT_PLAIN).build();
        }
    }

}
