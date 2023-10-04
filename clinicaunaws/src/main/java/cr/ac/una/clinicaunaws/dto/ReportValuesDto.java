package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.ReportValues;
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
public class ReportValuesDto implements DtoMapper<ReportValues, ReportValuesDto> {

    private Long id;
    private ReportDto report;
    private String name;
    private String value;
    private Long version;

    @Override
    public ReportValuesDto convertFromEntityToDTO(ReportValues entity, ReportValuesDto dto) {
        return new ReportValuesDto(entity);
    }

    @Override
    public ReportValues convertFromDTOToEntity(ReportValuesDto dto, ReportValues entity) {
        return new ReportValues(dto);
    }

    /**
     * Constructor from entity
     * 
     * @param entity the entity to convert to DTO
     */
    public ReportValuesDto(ReportValues entity) {
        this.id = entity.getId();
        this.report = null;
        this.name = entity.getName();
        this.value = entity.getValue();
        this.version = entity.getVersion();
    }

}
