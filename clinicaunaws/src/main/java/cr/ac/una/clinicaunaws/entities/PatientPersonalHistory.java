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

import cr.ac.una.clinicaunaws.dto.PatientPersonalHistoryDto;

/**
 * 
 * @author arayaroma
 */
@Entity
@Table(name = "TBL_PATIENT_PERSONAL_HISTORY", schema = SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientPersonalHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_PATIENT_PERSONAL_HISTORY, sequenceName = SCHEMA + "."
            + SEQ_PATIENT_PERSONAL_HISTORY, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_PATIENT_PERSONAL_HISTORY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @Column(name = "PATIENT")
    private Patient patient;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "PATHOLOGICAL")
    private String pathological;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "HOSPITALIZATIONS")
    private String hospitalizations;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "SURGICAL")
    private String surgical;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "ALLERGIES")
    private String allergies;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "TREATMENTS")
    private String treatments;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param dto constructor from dto to entity
     */
    public PatientPersonalHistory(PatientPersonalHistoryDto dto) {
        this.id = dto.getId();
        updatePatientPersonalHistory(dto);
    }

    /**
     * @param dto to be updated
     */
    public void updatePatientPersonalHistory(PatientPersonalHistoryDto dto) {
        this.patient = null;
        this.pathological = dto.getPathological();
        this.hospitalizations = dto.getHospitalizations();
        this.surgical = dto.getSurgical();
        this.allergies = dto.getAllergies();
        this.treatments = dto.getTreatments();
        this.version = dto.getVersion();
    }

}
