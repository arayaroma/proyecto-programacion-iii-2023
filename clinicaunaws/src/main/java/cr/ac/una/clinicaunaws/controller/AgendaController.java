package cr.ac.una.clinicaunaws.controller;

import java.util.List;
import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.AgendaDto;
import cr.ac.una.clinicaunaws.services.AgendaService;
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
@Path("/AgendaController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "AgendaController", description = "Manage endpoints related to the Agenda.")
public class AgendaController {

    private static final Logger logger = Logger.getLogger(AgendaController.class.getName());

    @EJB
    AgendaService agendaService;

    /**
     * Create a new Agenda
     * 
     * @param agendaDto to be created
     * @return Response with the created Agenda
     */
    @POST
    @Path("/create")
    public Response createAgenda(AgendaDto agendaDto) {
        try {
            ResponseWrapper response = agendaService.createAgenda(agendaDto);
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
     * get a Agenda by id
     * 
     * @param id of the Agenda to be retrieved
     * @return Response with the retrieved Agenda
     */
    @GET
    @Path("/agenda/{id}")
    public Response getAgendaById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = agendaService.getAgendaById(id);
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
     * get all Agenda
     * 
     * @return Response with the retrieved Agenda
     */
    @GET
    @Path("/agendas")
    @SuppressWarnings("unchecked")
    public Response getAllAgenda() {
        try {
            ResponseWrapper response = agendaService.getAllAgenda();
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<AgendaDto>>((List<AgendaDto>) response.getData()) {
            }).build();

        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * update an Agenda
     * 
     * @param agendaDto to be updated
     * @return Response with the updated Agenda
     */
    @PUT
    @Path("/update")
    public Response updateAgenda(AgendaDto agendaDto) {
        try {
            ResponseWrapper response = agendaService.updateAgenda(agendaDto);
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
     * delete an Agenda
     * 
     * @param id to be deleted
     * @return Response with the deleted Agenda
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteAgenda(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = agendaService.deleteAgenda(id);
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(response.getStatus()).entity(response.getMessage()).build();

        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
