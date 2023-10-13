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
public class PatientPersonalHistoryDto implements DtoMapper<PatientPersonalHistory, PatientPersonalHistoryDto> {

    private Long id;
    private PatientDto patient;
    private String pathological;
    private String hospitalizations;
    private String surgical;
    private String allergies;
    private String treatments;
    private List<MedicalExamDto> medicalExams;
    private List<PatientCareDto> patientCares;
    private Long version;

    @Override
    public PatientPersonalHistoryDto convertFromEntityToDTO(PatientPersonalHistory entity,
            PatientPersonalHistoryDto dto) { 
            dto.setPatient(new PatientDto(entity.getPatient()));
        dto.setMedicalExams(DtoMapper.fromEntityList(entity.getMedicalExams(), MedicalExamDto.class));
        dto.setPatientCares(DtoMapper.fromEntityList(entity.getPatientCares(), PatientCareDto.class));

        return dto;
    }

    @Override
    public PatientPersonalHistory convertFromDTOToEntity(PatientPersonalHistoryDto dto, PatientPersonalHistory entity) {
        if (dto.getPatient() != null) {
            entity.setPatient(new Patient(dto.getPatient()));
        }
        return entity;
    }

    public PatientPersonalHistoryDto(PatientPersonalHistory entity) {
        this.id = entity.getId();
        this.pathological = entity.getPathological();
        this.hospitalizations = entity.getHospitalizations();
        this.surgical = entity.getSurgical();
        this.allergies = entity.getAllergies();
        this.treatments = entity.getTreatments();
        this.version = entity.getVersion();
        this.medicalExams = new ArrayList<>();
        this.patientCares = new ArrayList<>();
    }

}
