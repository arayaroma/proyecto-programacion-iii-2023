package cr.ac.una.clinicaunaws.dto;

import java.util.List;
import cr.ac.una.clinicaunaws.entities.Patient;
import cr.ac.una.clinicaunaws.entities.PatientFamilyHistory;
import cr.ac.una.clinicaunaws.entities.PatientPersonalHistory;
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
    private List<MedicalAppointmentDto> medicalAppointments;
    private PatientPersonalHistoryDto patientPersonalHistory;
    private List<PatientFamilyHistoryDto> patientFamilyHistories;
    private Long version;

    @Override
    public PatientDto convertFromEntityToDTO(Patient entity, PatientDto dto) {

        dto.setPatientPersonalHistory(
                DtoMapper.convertToDto(entity.getPatientPersonalHistory(), PatientPersonalHistoryDto.class));
        dto.setPatientFamilyHistories(
                DtoMapper.fromEntityList(entity.getPatientFamilyHistories(), PatientFamilyHistoryDto.class));

        return dto;
    }

    @Override
    public Patient convertFromDTOToEntity(PatientDto dto, Patient entity) {

        entity.setPatientPersonalHistory(
                DtoMapper.convertToEntity(dto.getPatientPersonalHistory(), PatientPersonalHistory.class));
        entity.setPatientFamilyHistories(
                DtoMapper.fromDtoList(dto.getPatientFamilyHistories(), PatientFamilyHistory.class));

        return entity;
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
