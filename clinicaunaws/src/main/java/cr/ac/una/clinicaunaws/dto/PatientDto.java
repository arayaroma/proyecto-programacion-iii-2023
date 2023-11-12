package cr.ac.una.clinicaunaws.dto;

import java.util.List;
import cr.ac.una.clinicaunaws.entities.Patient;
import cr.ac.una.clinicaunaws.entities.PatientPersonalHistory;
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
    private String language;
    private PatientPersonalHistoryDto patientPersonalHistory;
    private List<PatientFamilyHistoryDto> patientFamilyHistories;
    private List<MedicalAppointmentDto> medicalAppointments;
    private Long version;

    @Override
    public PatientDto convertFromEntityToDTO(Patient entity, PatientDto dto) {
        PatientPersonalHistory personalHistory = entity.getPatientPersonalHistory();
        if (personalHistory != null) {
            dto.setPatientPersonalHistory(new PatientPersonalHistoryDto(personalHistory));
        }

        dto.setPatientFamilyHistories(
                DtoMapper.fromEntityList(entity.getPatientFamilyHistories(), PatientFamilyHistoryDto.class));
        dto.setMedicalAppointments(
                DtoMapper.fromEntityList(entity.getMedicalAppointments(), MedicalAppointmentDto.class));
        return dto;
    }

    @Override
    public Patient convertFromDTOToEntity(PatientDto dto, Patient entity) {
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
        this.language = patient.getLanguage();
        this.email = patient.getEmail();
        this.gender = patient.getGender();
        this.birthDate = patient.getBirthDate().toString();
        this.medicalAppointments = new ArrayList<>();
        this.patientFamilyHistories = new ArrayList<>();
        this.version = patient.getVersion();
    }

}
