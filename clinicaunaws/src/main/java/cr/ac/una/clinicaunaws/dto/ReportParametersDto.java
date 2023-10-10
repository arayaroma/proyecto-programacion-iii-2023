package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.ReportParameters;
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
public class ReportParametersDto implements DtoMapper<ReportParameters, ReportParametersDto> {

    private Long id;
    private ReportDto report;
    private String name;
    private String value;
    private Long version;

    @Override
    public ReportParametersDto convertFromEntityToDTO(ReportParameters entity, ReportParametersDto dto) {
        dto.setReport(new ReportDto(entity.getReport()));
        return dto;
    }

    @Override
    public ReportParameters convertFromDTOToEntity(ReportParametersDto dto, ReportParameters entity) {
        return new ReportParameters(dto);
    }

    /**
     * Constructor from entity
     * 
     * @param entity the entity to convert to DTO
     */
    public ReportParametersDto(ReportParameters entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.value = entity.getValue();
        this.version = entity.getVersion();
    }

}
