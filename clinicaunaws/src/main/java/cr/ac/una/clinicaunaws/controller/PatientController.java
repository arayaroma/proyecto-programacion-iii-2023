/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.clinicaunaws.controller;

import cr.ac.una.clinicaunaws.dto.PatientDto;
import cr.ac.una.clinicaunaws.services.PatientService;
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

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @EJB
    PatientService patientService;

    /**
     * Create a new user
     *
     * @param patientDto to be created
     * @return Response with the created user
     */
    @POST
    @Path("/create")
    public Response createUser(PatientDto patientDto) {
        try {
            ResponseWrapper response = patientService.createPatient(patientDto);
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Get a user by id
     *
     * @param id to be fetched
     * @return Response with the user
     */
    @GET
    @Path("/patient/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientService.getPatientById(id);
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Get all the doctors
     *
     * @return Response with the list of users
     */
    @GET
    @Path("/patients")
    public Response getUsers() {
        try {
            ResponseWrapper response = patientService.getPatients();
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Update a doctor
     *
     * @param patientDto to be updated
     * @return Response with the updated user
     */
    @PUT
    @Path("/update")
    public Response updateUser(PatientDto patientDto) {
        try {
            ResponseWrapper response = patientService.updatePatient(patientDto);
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * delete a user by id
     *
     * @param id to be deleted
     * @return Response with the deleted user
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteUserById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = patientService.deletePatientById(id);
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
