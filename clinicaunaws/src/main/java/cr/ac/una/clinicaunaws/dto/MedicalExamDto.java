package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.MedicalExam;
import cr.ac.una.clinicaunaws.entities.Patient;
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
public class MedicalExamDto implements DtoMapper<MedicalExam, MedicalExamDto> {

    private Long id;
    private PatientPersonalHistoryDto patientHistory;
    private String name;
    private String medicalExamDate;
    private String notes;
    private Long version;

    @Override
    public MedicalExamDto convertFromEntityToDTO(MedicalExam entity, MedicalExamDto dto) {
        dto.setPatientHistory(new PatientPersonalHistoryDto(entity.getPatientHistory()));
        dto.getPatientHistory().setPatient(new PatientDto(entity.getPatientHistory().getPatient()));
        return dto;
    }

    @Override
    public MedicalExam convertFromDTOToEntity(MedicalExamDto dto, MedicalExam entity) {
        if (dto.getPatientHistory() != null) {
            entity.setPatientHistory(new PatientPersonalHistory(dto.getPatientHistory()));
            if (dto.getPatientHistory().getPatient() != null) {
                entity.getPatientHistory().setPatient(new Patient(dto.getPatientHistory().getPatient()));
            }
        }

        return entity;
    }

    /**
     * @param entity constructor to dto
     */
    public MedicalExamDto(MedicalExam entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.medicalExamDate = entity.getMedicalExamDate().toString();
        this.notes = entity.getNotes();
        this.version = entity.getVersion();
    }

}
