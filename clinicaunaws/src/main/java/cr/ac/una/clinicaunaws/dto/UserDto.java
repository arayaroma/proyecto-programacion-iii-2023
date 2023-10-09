package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.User;
import cr.ac.una.clinicaunaws.util.DtoMapper;
import java.util.ArrayList;
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
    private List<MedicalAppointmentDto> medicalAppointments;
    private Long version;

    /**
     * @param entity Entity to be converted
     * @param dto    DTO to be updated
     * @return DTO with the updated information
     */
    @Override
    public UserDto convertFromEntityToDTO(User entity, UserDto dto) {
        dto.setMedicalAppointments(DtoMapper.fromEntityList(entity.getMedicalAppointments(), MedicalAppointmentDto.class));
        return dto;
    }

    /**
     * @param dto    DTO to be converted
     * @param entity Entity to be updated
     * @return Entity with the updated information
     */
    @Override
    public User convertFromDTOToEntity(UserDto dto, User entity) {
        return entity;
    }

    /**
     * @param user constructor from entity to dto
     */
    public UserDto(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.name = entity.getName();
        this.firstLastname = entity.getFirstLastname();
        this.secondLastname = entity.getSecondLastname();
        this.identification = entity.getIdentification();
        this.email = entity.getEmail();
        this.role = entity.getRole();
        this.phoneNumber = entity.getPhoneNumber();
        this.isActive = entity.getIsActive();
        this.isAdmin = entity.getIsAdmin();
        this.passwordChanged = entity.getPasswordChanged();
        this.activationCode = entity.getActivationCode();
        this.language = entity.getLanguage();
        this.profilePhoto = entity.getProfilePhoto();
        this.version = entity.getVersion();
        this.medicalAppointments = new ArrayList<>();
    }
}
