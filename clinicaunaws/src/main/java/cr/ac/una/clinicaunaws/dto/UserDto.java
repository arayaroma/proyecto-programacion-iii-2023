package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.User;
import cr.ac.una.clinicaunaws.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author arayaroma
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements DtoMapper<User, UserDto> {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String firstLastname;
    private String secondLastname;
    private String identification;
    private String email;
    private String role;
    private String phoneNumber;
    private String isActive;
    private String isAdmin;
    private String passwordChanged;
    private String activationCode;
    private String language;
    private byte[] profilePhoto;
    private Long version;

    /**
     * @param entity Entity to be converted
     * @param dto    DTO to be updated
     * @return DTO with the updated information
     */
    @Override
    public UserDto convertFromEntityToDTO(User entity, UserDto dto) {
        throw new UnsupportedOperationException("Unimplemented method 'convertFromEntityToDTO'");
    }

    /**
     * @param dto    DTO to be converted
     * @param entity Entity to be updated
     * @return Entity with the updated information
     */
    @Override
    public User convertFromDTOToEntity(UserDto dto, User entity) {
        throw new UnsupportedOperationException("Unimplemented method 'convertFromDTOToEntity'");
    }

    /**
     * @param user constructor from entity to dto
     */
    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.firstLastname = user.getFirstLastname();
        this.secondLastname = user.getSecondLastname();
        this.identification = user.getIdentification();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.phoneNumber = user.getPhoneNumber();
        this.isActive = user.getIsActive();
        this.isAdmin = user.getIsAdmin();
        this.passwordChanged = user.getPasswordChanged();
        this.activationCode = user.getActivationCode();
        this.profilePhoto = user.getProfilePhoto();
        this.version = user.getVersion();
    }
}
