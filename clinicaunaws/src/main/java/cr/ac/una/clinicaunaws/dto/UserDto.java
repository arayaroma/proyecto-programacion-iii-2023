package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.Doctor;
import java.util.List;
import cr.ac.una.clinicaunaws.entities.User;
import cr.ac.una.clinicaunaws.util.DtoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
@Schema(name = "UserDto", description = "DTO for User entity", requiredProperties = { "id", "username", "password",
        "name", "firstLastname", "secondLastname", "identification", "email", "role", "phoneNumber", "isActive",
        "isAdmin", "passwordChanged", "activationCode", "language", "profilePhoto", "medicalAppointments", "token",
        "version" })
public class UserDto implements DtoMapper<User, UserDto> {

    @NotNull(message = "The id can't be null")
    @NotEmpty(message = "The id can't be empty")
    @Schema(name = "id", example = "1", required = true)
    private Long id;

    @Schema(name = "doctor", implementation = DoctorDto.class)
    private DoctorDto doctor;

    @NotNull(message = "The username can't be null")
    @NotEmpty(message = "The username can't be empty")
    @Size(min = 1, max = 20, message = "The username must be between 1 and 20 characters")
    @Schema(name = "username", example = "mr_robot", required = true)
    private String username;

    @NotNull(message = "The password can't be null")
    @NotEmpty(message = "The password can't be empty")
    @Size(min = 1, max = 16, message = "The password must be between 1 and 16 characters")
    @Schema(name = "password", example = "mr_robot", required = true)
    private String password;

    @NotNull(message = "The name can't be null")
    @NotEmpty(message = "The name can't be empty")
    @Size(min = 1, max = 32, message = "The name must be between 1 and 32 characters")
    @Schema(name = "name", example = "Elliot", required = true)
    private String name;

    @NotNull(message = "The firstLastname can't be null")
    @NotEmpty(message = "The firstLastname can't be empty")
    @Size(min = 1, max = 32, message = "The firstLastname must be between 1 and 32 characters")
    @Schema(name = "firstLastname", example = "Alderson", required = true)
    private String firstLastname;

    @NotNull(message = "The secondLastname can't be null")
    @NotEmpty(message = "The secondLastname can't be empty")
    @Size(min = 1, max = 32, message = "The secondLastname must be between 1 and 32 characters")
    @Schema(name = "secondLastname", example = "Alderson", required = true)
    private String secondLastname;

    @NotNull(message = "The identification can't be null")
    @NotEmpty(message = "The identification can't be empty")
    @Size(min = 1, max = 16, message = "The identification must be between 1 and 16 characters")
    @Schema(name = "identification", example = "123456789", required = true)
    private String identification;

    @NotNull(message = "The email can't be null")
    @NotEmpty(message = "The email can't be empty")
    @Size(min = 1, max = 128, message = "The email must be between 1 and 128 characters")
    @Schema(name = "email", example = "elliot@proton.com", required = true)
    private String email;

    @NotNull(message = "The role can't be null")
    @NotEmpty(message = "The role can't be empty")
    @Size(min = 1, max = 13, message = "The role must be between 1 and 13 characters")
    @Schema(name = "role", description = "Different roles within the clinic", example = "ADMINISTRATOR", required = true, allowableValues = {
            "ADMINISTRATOR",
            "DOCTOR",
            "RECEPTIONIST" })
    private String role;

    @NotNull(message = "The phoneNumber can't be null")
    @NotEmpty(message = "The phoneNumber can't be empty")
    @Size(min = 1, max = 16, message = "The phoneNumber must be between 1 and 16 characters")
    @Schema(name = "phoneNumber", example = "12345678", required = true)
    private String phoneNumber;

    @NotNull(message = "The isActive can't be null")
    @NotEmpty(message = "The isActive can't be empty")
    @Size(min = 1, max = 1, message = "The isActive must be between 1 and 1 characters")
    @Schema(name = "isActive", example = "Y", required = true, allowableValues = { "Y", "N" }, defaultValue = "N")
    private String isActive;

    @NotNull(message = "The isAdmin can't be null")
    @NotEmpty(message = "The isAdmin can't be empty")
    @Size(min = 1, max = 1, message = "The isAdmin must be between 1 and 1 characters")
    @Schema(name = "isAdmin", example = "Y", required = true, allowableValues = { "Y", "N" }, defaultValue = "N")
    private String isAdmin;

    @NotNull(message = "The passwordChanged can't be null")
    @NotEmpty(message = "The passwordChanged can't be empty")
    @Size(min = 1, max = 1, message = "The passwordChanged must be between 1 and 1 characters")
    @Schema(name = "passwordChanged", example = "N", required = true, allowableValues = { "Y",
            "N" }, defaultValue = "N")
    private String passwordChanged;

    @Size(min = 1, max = 100, message = "The activationCode must be between 1 and 100 characters")
    @Schema(name = "activationCode", example = "123456", required = true)
    private String activationCode;

    @NotNull(message = "The language can't be null")
    @NotEmpty(message = "The language can't be empty")
    @Size(min = 1, max = 12, message = "The language must be between 1 and 12 characters")
    @Schema(name = "language", example = "ENGLISH", required = true, allowableValues = { "ENGLISH", "SPANISH" })
    private String language;

    @Schema(name = "profilePhoto", example = "")
    private byte[] profilePhoto;

    @Schema(name = "medicalAppointments", implementation = MedicalAppointmentDto.class)
    private List<MedicalAppointmentDto> medicalAppointments;

    @Schema(name = "token", example = "", required = true)
    private String token;

    @NotNull(message = "The version can't be null")
    @NotEmpty(message = "The version can't be empty")
    @Schema(name = "version", example = "1", required = true)
    private Long version;

    /**
     * @param entity Entity to be converted
     * @param dto    DTO to be updated
     * @return DTO with the updated information
     */
    @Override
    public UserDto convertFromEntityToDTO(User entity, UserDto dto) {
        Doctor doctorEntity = entity.getDoctor();
        if (doctorEntity != null) {
            dto.setDoctor(new DoctorDto(doctorEntity));
        }

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
