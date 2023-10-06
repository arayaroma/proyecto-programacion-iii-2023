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
    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.firstLastname = patient.getFirstLastname();
        this.secondLastname = patient.getSecondLastname();
        this.identification = patient.getIdentification();
        this.phoneNumber = patient.getPhoneNumber();
        this.email = patient.getEmail();
        this.gender = patient.getGender();
        this.birthDate = patient.getBirthDate().toString();
        this.version = patient.getVersion();
    }

}
