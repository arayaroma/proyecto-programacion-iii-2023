package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.PatientCareDto;
import cr.ac.una.clinicaunaws.services.PatientCareService;
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
@Path("/PatientCareController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "PatientCareController", description = "Manage endpoints related to the PatientCare.")
public class PatientCareController {

    private static final Logger logger = Logger.getLogger(PatientCareController.class.getName());

    @EJB
    PatientCareService patientCareService;

    /**
     * Create a new PatientCare
     * 
     * @param patientCareDto to be created
     * @return Response with the created PatientCare
     */
    @POST
    @Path("/create")
    public Response createPatientCare(PatientCareDto patientCareDto) {
        try {
            ResponseWrapper response = patientCareService.createPatientCare(patientCareDto);
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
     * get a PatientCare by id
     *
     * @param id of the PatientCare to be retrieved
     * @return Response with the retrieved PatientCare
     */
    @GET
    @Path("/patientCare/{id}")
    public Response getPatientCareById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientCareService.getPatientCareById(id);
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
     * get all PatientCare
     * 
     * @return Response with the retrieved PatientCare
     */
    @GET
    @Path("/patientCare")
    public Response getAllPatientCare() {
        try {
            ResponseWrapper response = patientCareService.getAllPatientCare();
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
     * update a PatientCare
     * 
     * @param patientCareDto to be updated
     * @return Response with the updated PatientCare
     */
    @PUT
    @Path("/update")
    public Response updatePatientCare(PatientCareDto patientCareDto) {
        try {
            ResponseWrapper response = patientCareService.updatePatientCare(patientCareDto);
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
     * delete a PatientCare by id
     * 
     * @param id to be deleted
     * @return Response with the deleted PatientCare
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deletePatientCare(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientCareService.deletePatientCare(id);
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
