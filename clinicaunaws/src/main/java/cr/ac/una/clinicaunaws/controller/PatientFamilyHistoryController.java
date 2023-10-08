package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.PatientFamilyHistoryDto;
import cr.ac.una.clinicaunaws.services.PatientFamilyHistoryService;
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
@Path("/PatientFamilyHistoryController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "PatientFamilyHistoryController", description = "Manage endpoints related to the PatientFamilyHistory.")
public class PatientFamilyHistoryController {

    private static final Logger logger = Logger.getLogger(PatientFamilyHistoryController.class.getName());

    @EJB
    PatientFamilyHistoryService patientFamilyHistoryService;

    /**
     * Create a new PatientFamilyHistory
     * 
     * @param patientFamilyHistoryDto to be created
     * @return Response with the created PatientFamilyHistory
     */
    @POST
    @Path("/create")
    public Response createPatientFamilyHistory(PatientFamilyHistoryDto patientFamilyHistoryDto) {
        try {
            ResponseWrapper response = patientFamilyHistoryService
                    .createPatientFamilyHistory(patientFamilyHistoryDto);
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
     * get a PatientFamilyHistory by id
     * 
     * @param id of the PatientFamilyHistory to be retrieved
     * @return Response with the PatientFamilyHistory
     */
    @GET
    @Path("/patientFamilyHistory/{id}")
    public Response getPatientFamilyHistoryById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientFamilyHistoryService.getPatientFamilyHistoryById(id);
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
     * get all PatientFamilyHistory
     * 
     * @return Response with the PatientFamilyHistory
     */
    @GET
    @Path("/patientFamilyHistory")
    public Response getAllPatientFamilyHistory() {
        try {
            ResponseWrapper response = patientFamilyHistoryService.getAllPatientFamilyHistory();
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
     * update a PatientFamilyHistory
     * 
     * @param patientFamilyHistoryDto to be updated
     * @return Response with the updated PatientFamilyHistory
     */
    @PUT
    @Path("/update")
    public Response updatePatientFamilyHistory(PatientFamilyHistoryDto patientFamilyHistoryDto) {
        try {
            ResponseWrapper response = patientFamilyHistoryService
                    .updatePatientFamilyHistory(patientFamilyHistoryDto);
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
     * delete a PatientFamilyHistory by id
     * 
     * @param id of the PatientFamilyHistory to be deleted
     * @return Response informing if the PatientFamilyHistory was deleted
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deletePatientFamilyHistory(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientFamilyHistoryService.deletePatientFamilyHistory(id);
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
