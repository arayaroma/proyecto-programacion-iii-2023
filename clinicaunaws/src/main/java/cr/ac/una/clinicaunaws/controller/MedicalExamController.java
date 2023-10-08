package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.MedicalExamDto;
import cr.ac.una.clinicaunaws.services.MedicalExamService;
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
@Path("/MedicalExamController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "MedicalExamController", description = "Manage endpoints related to the MedicalExam.")
public class MedicalExamController {

    private static final Logger logger = Logger.getLogger(MedicalExamController.class.getName());

    @EJB
    MedicalExamService medicalExamService;

    /**
     * Create a new MedicalExam
     * 
     * @param medicalExamDto to be created
     * @return Response with the created MedicalExam
     */
    @POST
    @Path("/create")
    public Response createMedicalExam(MedicalExamDto medicalExamDto) {
        try {
            ResponseWrapper response = medicalExamService.createMedicalExam(medicalExamDto);
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
     * get a MedicalExam by id
     * 
     * @param id of the MedicalExam to be retrieved
     * @return Response with the MedicalExamDto
     */
    @GET
    @Path("/medicalExam/{id}")
    public Response getMedicalExamById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = medicalExamService.getMedicalExamById(id);
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
     * get all MedicalExams
     * 
     * @return Response with all MedicalExams
     */
    @GET
    @Path("/medicalExams")
    public Response getAllMedicalExams() {
        try {
            ResponseWrapper response = medicalExamService.getAllMedicalExams();
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
     * update a MedicalExam
     * 
     * @param medicalExamDto to be updated
     * @return Response with the updated MedicalExam
     */
    @PUT
    @Path("/update")
    public Response updateMedicalExam(MedicalExamDto medicalExamDto) {
        try {
            ResponseWrapper response = medicalExamService.updateMedicalExam(medicalExamDto);
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
     * delete a MedicalExam by id
     * 
     * @param id to be deleted
     * @return Response with the deleted MedicalExam
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteMedicalExam(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = medicalExamService.deleteMedicalExam(id);
            if (response.getCode() != ResponseCode.OK) {
                return Response.status(response.getStatus()).entity(response.getMessage()).build();
            }
            return Response.ok(response.getStatus()).build();

        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

}
