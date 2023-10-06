package cr.ac.una.clinicaunaws.controller;

import java.util.logging.Logger;
import cr.ac.una.clinicaunaws.dto.UserDto;
import cr.ac.una.clinicaunaws.services.UserService;
import cr.ac.una.clinicaunaws.util.ResponseCode;
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
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.GenericEntity;
import java.util.List;

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

    /**
     * Create a new user
     *
     * @param userDto to be created
     * @return Response with the created user
     */
    @POST
    @Path("/create")
    public Response createUser(UserDto userDto) {
        try {
            ResponseWrapper response = userService.createUser(userDto);
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
     * Activate a user by hash
     *
     * @param hash of the user
     * @return Response with the activated user
     */
    @POST
    @Path("/activate/{hash}")
    public Response activateUser(@PathParam("hash") String hash) {
        try {
            ResponseWrapper response = userService.activateUser(hash);
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
     * Recover the password of a user by email
     *
     * @param email of the user
     * @return Response with the user with the new password
     */
    @POST
    @Path("/recoverPassword")
    public Response recoverPassword(String email) {
        try {
            ResponseWrapper response = userService.recoverPassword(email);
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
     * Change the password of a user
     *
     * @param id of the user
     * @param oldPassword to be changed
     * @param newPassword to be set
     * @return Response with the updated user
     */
    @PUT
    @Path("/changePassword/{id}/{oldPassword}/{newPassword}")
    public Response changePassword(
            @PathParam("id") Long id,
            @PathParam("oldPassword") String oldPassword,
            @PathParam("newPassword") String newPassword) {
        try {
            ResponseWrapper response = userService.changePassword(id, oldPassword, newPassword);
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
     * Get a user by id
     *
     * @param id to be fetched
     * @return Response with the user
     */
    @GET
    @Path("/user/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = userService.getUserById(id);
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
     * Get a user by username and password
     *
     * @param username to be fetched
     * @param password to be fetched
     * @return Response with the user
     */
    @GET
    @Path("/user/{username}/{password}")
    public Response getUserByUsernameAndPassword(
            @PathParam("username") String username,
            @PathParam("password") String password) {
        try {
            ResponseWrapper response = userService.getUserByUsernameAndPassword(username, password);
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
     * Get all the users
     *
     * @return Response with the list of users
     */
    @GET
    @Path("/users")
    public Response getUsers() {
        try {
            ResponseWrapper response = userService.getUsers();
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
     * Update a user
     *
     * @param userDto to be updated
     * @return Response with the updated user
     */
    @PUT
    @Path("/update")
    public Response updateUser(UserDto userDto) {
        try {
            ResponseWrapper response = userService.updateUser(userDto);
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
     * delete a user by id
     *
     * @param id to be deleted
     * @return Response with the deleted user
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteUserById(@PathParam("id") Long id) {
        try {
            ResponseWrapper response = userService.deleteUserById(id);
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
