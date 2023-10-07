package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.PatientPersonalHistoryDto;
import cr.ac.una.clinicaunaws.services.PatientPersonalHistoryService;
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
@Path("/PatientPersonalHistoryController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "PatientPersonalHistoryController", description = "Manage endpoints related to the PatientPersonalHistory.")
public class PatientPersonalHistoryController {

    private static final Logger logger = Logger.getLogger(PatientPersonalHistoryController.class.getName());

    @EJB
    PatientPersonalHistoryService patientPersonalHistoryService;

    /**
     * Create a new PatientPersonalHistory
     * 
     * @param patientPersonalHistoryDto to be created
     * @return Response with the created PatientPersonalHistory
     */
    @POST
    @Path("/create")
    public Response createPatientPersonalHistory(PatientPersonalHistoryDto patientPersonalHistoryDto) {
        try {
            ResponseWrapper response = patientPersonalHistoryService
                    .createPatientPersonalHistory(patientPersonalHistoryDto);
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
     * get a PatientPersonalHistory by id
     * 
     * @param id of the PatientPersonalHistory to be retrieved
     * @return Response with the retrieved PatientPersonalHistory
     */
    @GET
    @Path("/patientPersonalHistory/{id}")
    public Response getPatientPersonalHistoryById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientPersonalHistoryService.getPatientPersonalHistoryById(id);
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
     * get all PatientPersonalHistory
     * 
     * @return Response with the retrieved PatientPersonalHistory
     */
    @GET
    @Path("/patientPersonalHistory")
    public Response getAllPatientPersonalHistory() {
        try {
            ResponseWrapper response = patientPersonalHistoryService.getAllPatientPersonalHistory();
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
     * update a PatientPersonalHistory
     * 
     * @param patientPersonalHistoryDto to be updated
     * @return Response with the updated PatientPersonalHistory
     */
    @PUT
    @Path("/update")
    public Response updatePatientPersonalHistory(PatientPersonalHistoryDto patientPersonalHistoryDto) {
        try {
            ResponseWrapper response = patientPersonalHistoryService
                    .updatePatientPersonalHistory(patientPersonalHistoryDto);
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
     * delete a PatientPersonalHistory by id
     * 
     * @param id to be deleted
     * @return Response with the deleted PatientPersonalHistory
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deletePatientPersonalHistory(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientPersonalHistoryService.deletePatientPersonalHistory(id);
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
