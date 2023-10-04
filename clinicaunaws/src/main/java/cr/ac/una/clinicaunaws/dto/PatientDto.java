package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.Patient;
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
public class PatientDto implements DtoMapper<Patient, PatientDto> {

    private Long id;
    private String name;
    private String firstLastname;
    private String secondLastname;
    private String identification;
    private String phoneNumber;
    private String email;
    private String gender;
    private String birthDate;
    private Long version;

    @Override
    public PatientDto convertFromEntityToDTO(Patient entity, PatientDto dto) {
        return new PatientDto(entity);
    }

    @Override
    public Patient convertFromDTOToEntity(PatientDto dto, Patient entity) {
        return new Patient(dto);
    }

    /**
     * @param patient constructor from entity to dto
     */
    public PatientDto(Patient entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.firstLastname = entity.getFirstLastname();
        this.secondLastname = entity.getSecondLastname();
        this.identification = entity.getIdentification();
        this.phoneNumber = entity.getPhoneNumber();
        this.email = entity.getEmail();
        this.gender = entity.getGender();
        this.birthDate = entity.getBirthDate().toString();
        this.version = entity.getVersion();
    }

}
