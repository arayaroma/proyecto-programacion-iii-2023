package cr.ac.una.clinicaunaws.controller;

import java.util.List;
import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.GeneralInformationDto;
import cr.ac.una.clinicaunaws.services.GeneralInformationService;
import cr.ac.una.clinicaunaws.util.ResponseCode;
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
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 * @author arayaroma
 */
@Path("/GeneralInformationController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "GeneralInformationController", description = "Manage endpoints related to the General Information.")
public class GeneralInformationController {

    private static final Logger logger = Logger.getLogger(GeneralInformationController.class.getName());

    @EJB
    GeneralInformationService generalInformationService;

    /**
     * Create a new General Information
     *
     * @param generalInformationDto to be created
     * @return Response with the created General Information
     */
    @POST
    @Path("/create")
    public Response createGeneralInformation(GeneralInformationDto generalInformationDto) {
        try {
            ResponseWrapper response = generalInformationService.createGeneralInformation(generalInformationDto);
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
     * Get a General Information by id
     * 
     * @param id of the General Information to be retrieved
     * @return Response with the retrieved General Information
     */
    @GET
    @Path("/generalInformation/{id}")
    public Response getGeneralInformationById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = generalInformationService.getGeneralInformationById(id);
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
     * Get all General Information
     * 
     * @return Response with the retrieved General Information
     */
    @GET
    @Path("/generalInformation")
    @SuppressWarnings("unchecked")
    public Response getAllGeneralInformation() {
        try {
            ResponseWrapper response = generalInformationService.getAllGeneralInformation();
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(
                    new GenericEntity<List<GeneralInformationDto>>((List<GeneralInformationDto>) response.getData()) {
                    }).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Update a General Information
     * 
     * @param generalInformationDto to be updated
     * @return Response with the updated General Information
     */
    @PUT
    @Path("/update")
    public Response updateGeneralInformation(GeneralInformationDto generalInformationDto) {
        try {
            ResponseWrapper response = generalInformationService.updateGeneralInformation(generalInformationDto);
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
     * Delete a General Information
     * 
     * @param id of the General Information to be deleted
     * @return Response with the deleted General Information
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteGeneralInformation(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = generalInformationService.deleteGeneralInformation(id);
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
