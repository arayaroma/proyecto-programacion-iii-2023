package cr.ac.una.clinicauna.services;

import cr.ac.una.clinicauna.model.UserDto;
import cr.ac.una.clinicauna.util.Request;
import cr.ac.una.clinicauna.util.ResponseCode;
import cr.ac.una.clinicauna.util.ResponseWrapper;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author estebannajera
 */
public class UserService {

    public ResponseWrapper getUsers() {
        try {
            Request request = new Request("UserController/users");
            request.get();
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            List<UserDto> userDtos = (List<UserDto>) request.readEntity(new GenericType<List<UserDto>>() {
            });
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "Users retrieved successfully: ", userDtos);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }

    public ResponseWrapper createUser(UserDto userDto) {
        try {
            Request request = new Request("UserController/create");
            request.post(userDto);
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            userDto = (UserDto) request.readEntity(UserDto.class);
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "User created successfully: ", userDto);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }

    public ResponseWrapper updateUser(UserDto userDto) {
        try {
            Request request = new Request("UserController/update");
            request.put(userDto);
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            userDto = (UserDto) request.readEntity(UserDto.class);
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "User updated successfully: ", userDto);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }

    public ResponseWrapper deleteUser(UserDto userDto) {
        try {
            HashMap params = new HashMap();
            params.put("id", userDto.getId());
            Request request = new Request("UserController/delete", "/{id}", params);
            request.delete();
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "User removed successfully: ", null);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }

    public ResponseWrapper findUserById(Long id) {
        try {
            HashMap params = new HashMap();
            params.put("id", id);
            Request request = new Request("UserController/user", "/{id}", params);
            request.get();
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            UserDto userDto = (UserDto) request.readEntity(UserDto.class);
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "User retrieved successfully: ", userDto);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }

    public ResponseWrapper changePassword(Long id, String oldPassword, String newPassword){
        try {
            HashMap params = new HashMap();
            params.put("id", id);
            params.put("oldPassword", oldPassword);
            params.put("newPassword", newPassword);
            Request request = new Request("UserController/changePassword", "/{id}/{oldPassword}/{newPassword}", params);
            request.put(params);
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            UserDto userDto = (UserDto) request.readEntity(UserDto.class);
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "User retrieved successfully: ", userDto);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }
    public ResponseWrapper recoverPassword(String email){
        try {
            HashMap params = new HashMap();
            params.put("email", email);
            Request request = new Request("UserController/recoverPassword", "/{email}", params);
            request.get();
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            UserDto userDto = (UserDto) request.readEntity(UserDto.class);
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "User retrieved successfully: ", userDto);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }        
    }
    public ResponseWrapper verifyUser(String user, String password) {
        try {
            HashMap params = new HashMap();
            params.put("username", user);
            params.put("password", password);
            Request request = new Request("UserController/user", "/{username}/{password}", params);
            request.get();
            if (request.isError()) {
                return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the request: " + request.getError(), null);
            }
            UserDto userDto = (UserDto) request.readEntity(UserDto.class);
            return new ResponseWrapper(ResponseCode.OK.getCode(), ResponseCode.OK, "User retrieved successfully: ", userDto);
        } catch (Exception ex) {
            return new ResponseWrapper(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR, "Error in the service: " + ex.toString(), null);
        }
    }

}
