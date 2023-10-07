package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.SlotsDto;
import cr.ac.una.clinicaunaws.services.SlotsService;
import cr.ac.una.clinicaunaws.util.ResponseCode;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 * @author arayaroma
 */
@Path("/SlotsController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "SlotsController", description = "Manage endpoints related to the Slots.")
public class SlotsController {

    private static final Logger logger = Logger.getLogger(SlotsController.class.getName());

    @EJB
    SlotsService slotsService;

    /**
     * Create a new Slots
     * 
     * @param slotsDto to be created
     * @return Response with the created Slots
     */
    @POST
    @Path("/create")
    public Response createSlots(SlotsDto slotsDto) {
        try {
            ResponseWrapper response = slotsService.createSlots(slotsDto);
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
     * get a Slots by id
     * 
     * @param id of the Slots to be retrieved
     * @return Response with the requested Slots
     */
    @GET
    @Path("/slots/{id}")
    public Response getSlotsById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = slotsService.getSlotsById(id);
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
     * get all Slots
     * 
     * @return Response with all Slots
     */
    @GET
    @Path("/slots")
    public Response getAllSlots() {
        try {
            ResponseWrapper response = slotsService.getAllSlots();
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
     * update a Slots
     * 
     * @param slotsDto to be updated
     * @return Response with the updated Slots
     */
    @PUT
    @Path("/update")
    public Response updateSlots(SlotsDto slotsDto) {
        try {
            ResponseWrapper response = slotsService.updateSlots(slotsDto);
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
     * delete a Slots by id
     * 
     * @param id of the Slots to be deleted
     * @return Response with the deleted Slots
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteSlots(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = slotsService.deleteSlots(id);
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
