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
        PatientFamilyHistoryDto patientFamilyHistoryDto = new PatientFamilyHistoryDto(entity);

        patientFamilyHistoryDto.setPatient(new PatientDto(entity.getPatient()));
        return patientFamilyHistoryDto;
    }

    @Override
    public PatientFamilyHistory convertFromDTOToEntity(PatientFamilyHistoryDto dto, PatientFamilyHistory entity) {
        return new PatientFamilyHistory(dto);
    }

    /**
     * @param patientFamilyHistory constructor from entity to dto
     */
    public PatientFamilyHistoryDto(PatientFamilyHistory entity) {
        this.id = entity.getId();
        this.disease = entity.getDisease();
        this.relationship = entity.getRelationship();
        this.version = entity.getVersion();
    }

}
