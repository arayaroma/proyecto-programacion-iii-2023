package cr.ac.una.clinicaunaws.controller;

import cr.ac.una.clinicaunaws.dto.PatientDto;
import cr.ac.una.clinicaunaws.dto.UserDto;
import cr.ac.una.clinicaunaws.services.PatientService;
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
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author vargas
 */
@Path("/PatientController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "PatientController", description = "Manage endpoints related to the Patient.")
public class PatientController {

    private static final Logger logger = Logger.getLogger(PatientController.class.getName());

    @EJB
    PatientService patientService;

    /**
     * Create a new Patient
     *
     * @param patientDto to be created
     * @return Response with the created Patient
     */
    @POST
    @Path("/create")
    public Response createPatient(PatientDto patientDto) {
        try {
            ResponseWrapper response = patientService.createPatient(patientDto);
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
     * Get a Patient by id
     *
     * @param id to be fetched
     * @return Response with the Patient
     */
    @GET
    @Path("/patient/{id}")
    public Response getPatientById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientService.getPatientById(id);
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
     * Get all the doctors
     *
     * @return Response with the list of Patients
     */
    @GET
    @Path("/patients")
    @SuppressWarnings("unchecked")
    public Response getPatients() {
        try {
            ResponseWrapper response = patientService.getPatients();
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<UserDto>>((List<UserDto>) response.getData()) {
            }).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Update a doctor
     *
     * @param patientDto to be updated
     * @return Response with the updated Patient
     */
    @PUT
    @Path("/update")
    public Response updatePatient(PatientDto patientDto) {
        try {
            ResponseWrapper response = patientService.updatePatient(patientDto);
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
     * delete a Patient by id
     *
     * @param id to be deleted
     * @return Response with the deleted Patient
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deletePatientById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientService.deletePatientById(id);
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
