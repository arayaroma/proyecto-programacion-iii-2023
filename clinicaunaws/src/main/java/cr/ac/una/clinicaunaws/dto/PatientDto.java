package cr.ac.una.clinicaunaws.dto;

import java.util.List;

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
    private List<MedicalAppointmentDto> medicalAppointments;
    private List<PatientPersonalHistoryDto> patientPersonalHistories;
    private List<PatientFamilyHistoryDto> patientFamilyHistories;
    private Long version;

    @Override
    public PatientDto convertFromEntityToDTO(Patient entity, PatientDto dto) {
        PatientDto patientDto = new PatientDto(entity);

        // Set the Patient Family History List
        for (int i = 0; i < entity.getPatientFamilyHistories().size(); i++) {
            patientDto.getPatientFamilyHistories()
                    .add(new PatientFamilyHistoryDto(entity.getPatientFamilyHistories().get(i)));
        }

        // Set the Patient Personal History List
        for (int i = 0; i < entity.getPatientPersonalHistories().size(); i++) {
            patientDto.getPatientPersonalHistories()
                    .add(new PatientPersonalHistoryDto(entity.getPatientPersonalHistories().get(i)));
            for (int j = 0; j < entity.getPatientPersonalHistories().get(i).getMedicalExams().size(); j++) {
                patientDto.getPatientPersonalHistories()
                        .get(i).getMedicalExams().add(new MedicalExamDto(entity.getPatientPersonalHistories()
                                .get(i).getMedicalExams().get(j)));
            }

            for (int j = 0; j < entity.getPatientPersonalHistories().get(i).getPatientCares().size(); j++) {
                patientDto.getPatientPersonalHistories()
                        .get(i).getPatientCares().add(new PatientCareDto(entity.getPatientPersonalHistories()
                                .get(i).getPatientCares().get(j)));
            }
        }
        return patientDto;
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
        this.medicalAppointments = null;
        this.patientPersonalHistories = null;
        this.patientFamilyHistories = null;
        this.version = patient.getVersion();
    }

}
