package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;

import cr.ac.una.clinicaunaws.dto.UserDto;
import cr.ac.una.clinicaunaws.services.UserService;
import cr.ac.una.clinicaunaws.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Path;

/**
 * 
 * @author arayaroma
 */
@Path("/UserController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "UserController", description = "Manage endpoints related to the User.")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @EJB
    UserService userService;

    @GET
    @Path("/ping")
    public Response ping() {
        try {
            ResponseWrapper response = userService.ping();
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/create")
    public Response createUser(UserDto userDto) {
        try {
            ResponseWrapper response = userService.createUser(userDto);
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/user/{id}")
    public Response getUserById(Long id) {
        try {
            ResponseWrapper response = userService.getUserById(id);
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/users")
    public Response getUsers() {
        try {
            ResponseWrapper response = userService.getUsers();
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/update")
    public Response updateUser(UserDto userDto) {
        try {
            ResponseWrapper response = userService.updateUser(userDto);
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/user/{id}")
    public Response deleteUserById(Long id) {
        try {
            ResponseWrapper response = userService.deleteUserById(id);
            return Response.status(response.getStatus()).entity(response).build();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
