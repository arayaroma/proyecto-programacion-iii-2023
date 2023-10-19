package cr.ac.una.clinicaunaws.controller;

import java.util.List;
import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.SlotsDto;
import cr.ac.una.clinicaunaws.security.Secure;
import cr.ac.una.clinicaunaws.services.SlotsService;
import cr.ac.una.clinicaunaws.util.ResponseCode;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

/**
 * 
 * @author arayaroma
 */
@Secure
@Path("/SlotsController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "jwt-auth")
@Tag(name = "SlotsController", description = "Manage endpoints related to the Slots.")
public class SlotsController {
    private static final Logger logger = Logger.getLogger(SlotsController.class.getName());

    @Context
    SecurityContext securityContext;

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
    @Operation(summary = "Create a new Slots", description = "Create a new Slots", tags = { "SlotsController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Slots created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Slots already exists")
    })
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
    @Operation(summary = "Get a Slots by id", description = "Get a Slots by id", tags = { "SlotsController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Slots found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Slots not found")
    })
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
    @SuppressWarnings("unchecked")
    @Operation(summary = "Get all Slots", description = "Get all Slots", tags = { "SlotsController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Slots found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Slots not found")
    })
    public Response getAllSlots() {
        try {
            ResponseWrapper response = slotsService.getAllSlots();
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(
                    new GenericEntity<List<SlotsDto>>((List<SlotsDto>) response.getData()) {
                    }).build();
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
    @Operation(summary = "Update a Slots", description = "Update a Slots", tags = { "SlotsController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Slots updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Slots not found"),
            @ApiResponse(responseCode = "409", description = "Slots already exists")
    })
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
    @Operation(summary = "Delete a Slots by id", description = "Delete a Slots by id", tags = { "SlotsController" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Slots deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Slots not found")
    })
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
