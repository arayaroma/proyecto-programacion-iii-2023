package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.ReportValuesDto;
import cr.ac.una.clinicaunaws.services.ReportValuesService;
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
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 * @author arayaroma
 */
@Path("/ReportValuesController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "ReportValuesController", description = "Manage endpoints related to the ReportValues.")
public class ReportValuesController {

    private static final Logger logger = Logger.getLogger(ReportValuesController.class.getName());

    @EJB
    ReportValuesService reportValuesService;

    /**
     * Create a new ReportValues
     * 
     * @param reportValuesDto to be created
     * @return Response with the created ReportValues
     */
    @POST
    @Path("/create")
    public Response createReportValues(ReportValuesDto reportValuesDto) {
        try {
            ResponseWrapper response = reportValuesService.createReportValues(reportValuesDto);
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
     * get a ReportValues by id
     * 
     * @param id of the ReportValues to be fetched
     * @return Response with the fetched ReportValues
     */
    @GET
    @Path("/reportValues/{id}")
    public Response getReportValuesById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = reportValuesService.getReportValuesById(id);
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
     * get all ReportValues
     * 
     * @return Response with all the ReportValues
     */
    @GET
    @Path("/reportValues")
    public Response getAllReportValues() {
        try {
            ResponseWrapper response = reportValuesService.getAllReportValues();
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
     * Update a ReportValues
     * 
     * @param reportValuesDto to be updated
     * @return Response with the updated ReportValues
     */
    @PUT
    @Path("/update")
    public Response updateReportValues(ReportValuesDto reportValuesDto) {
        try {
            ResponseWrapper response = reportValuesService.updateReportValues(reportValuesDto);
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
     * Delete a ReportValues
     * 
     * @param id of the ReportValues to be deleted
     * @return Response with the deleted ReportValues
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteReportValues(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = reportValuesService.deleteReportValues(id);
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(response.getStatus()).entity(response.getData()).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
