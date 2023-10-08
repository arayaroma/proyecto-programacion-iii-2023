package cr.ac.una.clinicaunaws.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.ReportRecipientsDto;
import cr.ac.una.clinicaunaws.services.ReportRecipientsService;
import cr.ac.una.clinicaunaws.util.ResponseCode;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 * @author arayaroma
 */
@Path("/ReportRecipientsController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "ReportRecipientsController", description = "Manage endpoints related to the ReportRecipients.")
public class ReportRecipientsController {

    private static final Logger logger = Logger.getLogger(ReportRecipientsController.class.getName());

    @EJB
    ReportRecipientsService reportRecipientsService;

    /**
     * Create a new ReportRecipients
     * 
     * @param reportRecipientsDto to be created
     * @return Response with the created ReportRecipients
     */
    @POST
    @Path("/create")
    public Response createReportRecipients(ReportRecipientsDto reportRecipientsDto) {
        try {
            ResponseWrapper response = reportRecipientsService.createReportRecipients(reportRecipientsDto);
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(response.getStatus()).entity(response.getData()).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * get a ReportRecipients by id
     * 
     * @param id of the ReportRecipients to be fetched
     * @return Response with the fetched ReportRecipients
     */
    @GET
    @Path("/reportRecipients/{id}")
    public Response getReportRecipientsById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = reportRecipientsService.getReportRecipientsById(id);
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(response.getStatus()).entity(response.getData()).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * get all ReportRecipients
     * 
     * @return Response with all the ReportRecipients
     */
    @GET
    @Path("/reportRecipients")
    public Response getAllReportRecipients() {
        try {
            ResponseWrapper response = reportRecipientsService.getAllReportRecipients();
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(response.getStatus()).entity(response.getData()).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Update a ReportRecipients
     * 
     * @param reportRecipientsDto to be updated
     * @return Response with the updated ReportRecipients
     */
    @PUT
    @Path("/update")
    public Response updateReportRecipients(ReportRecipientsDto reportRecipientsDto) {
        try {
            ResponseWrapper response = reportRecipientsService.updateReportRecipients(reportRecipientsDto);
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(response.getStatus()).entity(response.getData()).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Delete a ReportRecipients
     * 
     * @param id of the ReportRecipients to be deleted
     * @return Response with the deleted ReportRecipients
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteReportRecipients(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = reportRecipientsService.deleteReportRecipients(id);
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.status(response.getStatus()).entity(response.getData()).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
