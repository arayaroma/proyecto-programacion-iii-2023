package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.Report;
import cr.ac.una.clinicaunaws.entities.ReportRecipients;
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
public class ReportRecipientsDto implements DtoMapper<ReportRecipients, ReportRecipientsDto> {

    private Long id;
    private ReportDto report;
    private String email;
    private Long version;

    @Override
    public ReportRecipientsDto convertFromEntityToDTO(ReportRecipients entity, ReportRecipientsDto dto) {
        dto.setReport(new ReportDto(entity.getReport()));
        return dto;
    }

    @Override
    public ReportRecipients convertFromDTOToEntity(ReportRecipientsDto dto, ReportRecipients entity) {
        entity.setReport(new Report(dto.getReport()));
        return entity;
    }

    /**
     * Constructor from entity
     * 
     * @param entity the entity to convert to DTO
     */
    public ReportRecipientsDto(ReportRecipients entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.version = entity.getVersion();
    }

}
