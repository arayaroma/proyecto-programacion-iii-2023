package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.Report;
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
public class ReportDto implements DtoMapper<Report, ReportDto> {

    private Long id;
    private String name;
    private String description;
    private String query;
    private String date;
    private Long frequency;
    private List<ReportParametersDto> reportParameters;
    private List<ReportRecipientsDto> reportRecipients;
    private Long version;

    @Override
    public ReportDto convertFromEntityToDTO(Report entity, ReportDto dto) {
        return new ReportDto(entity);
    }

    @Override
    public Report convertFromDTOToEntity(ReportDto dto, Report entity) {
        return new Report(dto);
    }

    /**
     * @param entity constructor from entity to dto
     */
    public ReportDto(Report entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.query = entity.getQuery();
        this.date = entity.getDate().toString();
        this.frequency = entity.getFrequency();
        this.reportParameters = null;
        this.reportRecipients = null;
        this.version = entity.getVersion();
    }

}
