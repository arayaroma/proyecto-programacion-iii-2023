package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.PatientFamilyHistory;
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
public class PatientFamilyHistoryDto implements DtoMapper<PatientFamilyHistory, PatientFamilyHistoryDto> {

    private Long id;
    private PatientDto patient;
    private String disease;
    private String relationship;
    private Long version;

    @Override
    public PatientFamilyHistoryDto convertFromEntityToDTO(PatientFamilyHistory entity, PatientFamilyHistoryDto dto) {
        

        dto.setPatient(new PatientDto(entity.getPatient()));
        return dto;
    }

    @Override
    public PatientFamilyHistory convertFromDTOToEntity(PatientFamilyHistoryDto dto, PatientFamilyHistory entity) {
    return entity;
    }

    
    public PatientFamilyHistoryDto(PatientFamilyHistory entity) {
        this.id = entity.getId();
        this.disease = entity.getDisease();
        this.relationship = entity.getRelationship();
        this.version = entity.getVersion();
    }

}
