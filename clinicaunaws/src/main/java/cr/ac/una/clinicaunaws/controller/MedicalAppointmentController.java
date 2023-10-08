package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.MedicalAppointmentDto;
import cr.ac.una.clinicaunaws.services.MedicalAppointmentService;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 * @author arayaroma
 */
@Path("/MedicalAppointmentController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "MedicalAppointmentController", description = "Manage endpoints related to the MedicalAppointment.")
public class MedicalAppointmentController {

    private static final Logger logger = Logger.getLogger(MedicalAppointmentController.class.getName());

    @EJB
    MedicalAppointmentService medicalAppointmentService;

    /**
     * Create a new MedicalAppointment
     * 
     * @param medicalAppointmentDto to be created
     * @return Response with the created MedicalAppointment
     */
    @POST
    @Path("/create")
    public Response createMedicalAppointment(MedicalAppointmentDto medicalAppointmentDto) {
        try {
            ResponseWrapper response = medicalAppointmentService.createMedicalAppointment(medicalAppointmentDto);
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
     * get a MedicalAppointment by id
     * 
     * @param id of the MedicalAppointment to be retrieved
     * @return Response with the requested MedicalAppointment
     */
    @GET
    @Path("/medicalAppointment/{id}")
    public Response getMedicalAppointmentById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = medicalAppointmentService.getMedicalAppointmentById(id);
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
     * get all MedicalAppointments
     * 
     * @return Response with all MedicalAppointments
     */
    @GET
    @Path("/medicalAppointments")
    public Response getAllMedicalAppointments() {
        try {
            ResponseWrapper response = medicalAppointmentService.getAllMedicalAppointments();
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
     * update a MedicalAppointment
     * 
     * @param medicalAppointmentDto to be updated
     * @return Response with the updated MedicalAppointment
     */
    @PUT
    @Path("/update")
    public Response updateMedicalAppointment(MedicalAppointmentDto medicalAppointmentDto) {
        try {
            ResponseWrapper response = medicalAppointmentService.updateMedicalAppointment(medicalAppointmentDto);
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
     * delete a MedicalAppointment by id
     * 
     * @param id of the MedicalAppointment to be deleted
     * @return Response with the deleted MedicalAppointment
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteMedicalAppointment(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = medicalAppointmentService.deleteMedicalAppointment(id);
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
