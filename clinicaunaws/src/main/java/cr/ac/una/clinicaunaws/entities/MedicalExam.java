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
import java.time.LocalDate;
import cr.ac.una.clinicaunaws.dto.MedicalExamDto;

/**
 * 
 * @author arayaroma
 */
@Entity
@Table(name = "TBL_MEDICAL_EXAM", schema = SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalExam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_MEDICAL_EXAM, sequenceName = SCHEMA + "." + SEQ_MEDICAL_EXAM, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_MEDICAL_EXAM)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @Column(name = "PATIENTHISTORY")
    private PatientPersonalHistory patientHistory;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 32)
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Basic(optional = false)
    @Column(name = "DATE")
    private LocalDate date;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "NOTES")
    private String notes;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param dto to convert to entity
     */
    public MedicalExam(MedicalExamDto dto) {
        this.id = dto.getId();
        updateMedicalExam(dto);
    }

    /**
     * @param dto to update entity
     */
    public void updateMedicalExam(MedicalExamDto dto) {
        this.patientHistory = null;
        this.name = dto.getName();
        this.date = LocalDate.parse(dto.getDate());
        this.notes = dto.getNotes();
        this.version = dto.getVersion();
    }

}