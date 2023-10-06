package cr.ac.una.clinicaunaws.dto;

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
public class PatientPersonalHistoryDto implements DtoMapper<PatientPersonalHistory, PatientPersonalHistoryDto> {

    private Long id;
    private PatientDto patient;
    private String pathological;
    private String hospitalizations;
    private String surgical;
    private String allergies;
    private String treatments;
    private Long version;

    @Override
    public PatientPersonalHistoryDto convertFromEntityToDTO(PatientPersonalHistory entity,
            PatientPersonalHistoryDto dto) {
        return new PatientPersonalHistoryDto(entity);
    }

    @Override
    public PatientPersonalHistory convertFromDTOToEntity(PatientPersonalHistoryDto dto, PatientPersonalHistory entity) {
        return new PatientPersonalHistory(dto);
    }

    /**
     * @param patientPersonalHistory constructor from entity to dto
     */
    public PatientPersonalHistoryDto(PatientPersonalHistory entity) {
        this.id = entity.getId();
        this.patient = null;
        this.pathological = entity.getPathological();
        this.hospitalizations = entity.getHospitalizations();
        this.surgical = entity.getSurgical();
        this.allergies = entity.getAllergies();
        this.treatments = entity.getTreatments();
        this.version = entity.getVersion();
    }

}