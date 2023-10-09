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
        ReportDto reportDto = new ReportDto(entity);

        // Set the Report Parameters List
        for (int i = 0; i < entity.getReportParameters().size(); i++) {
            reportDto.getReportParameters().add(new ReportParametersDto(entity.getReportParameters().get(i)));
        }

        // Set the Report Recipients List
        for (int i = 0; i < entity.getReportRecipients().size(); i++) {
            reportDto.getReportRecipients().add(new ReportRecipientsDto(entity.getReportRecipients().get(i)));
        }
        return reportDto;
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
        this.version = entity.getVersion();
    }

}
