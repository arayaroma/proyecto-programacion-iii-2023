package cr.ac.una.clinicaunaws.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static cr.ac.una.clinicaunaws.util.Database.*;
import java.io.Serializable;
import cr.ac.una.clinicaunaws.dto.ReportRecipientsDto;

/**
 * 
 * @author arayaroma
 */
@Entity
@Table(name = "TBL_REPORT_RECIPIENTS", schema = SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRecipients implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_REPORT_RECIPIENTS, sequenceName = SCHEMA + "."
            + SEQ_REPORT_RECIPIENTS, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_REPORT_RECIPIENTS)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @Column(name = "REPORT")
    private Report report;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 64)
    @Column(name = "EMAIL")
    private String email;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param dto the dto to convert to entity
     */
    public ReportRecipients(ReportRecipientsDto dto) {
        this.id = dto.getId();
        updateReportRecipients(dto);
    }

    /**
     * @param entity the entity to convert to dto
     */
    public void updateReportRecipients(ReportRecipientsDto dto) {
        this.report = null;
        this.email = dto.getEmail();
        this.version = dto.getVersion();
    }

}