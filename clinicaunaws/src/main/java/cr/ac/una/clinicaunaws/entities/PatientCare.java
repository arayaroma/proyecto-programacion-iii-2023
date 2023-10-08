package cr.ac.una.clinicaunaws.entities;

import static cr.ac.una.clinicaunaws.util.Database.*;
import java.io.Serializable;
import java.util.List;

import cr.ac.una.clinicaunaws.dto.PatientCareDto;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author arayaroma
 */
@Entity
@Table(name = "TBL_PATIENT_CARE", schema = SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "PatientCare.findAll", query = "SELECT p FROM PatientCare p"),
        @NamedQuery(name = "PatientCare.findById", query = "SELECT p FROM PatientCare p WHERE p.id = :id"),
})
public class PatientCare implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_PATIENT_CARE, sequenceName = SCHEMA + "."
            + SEQ_PATIENT_CARE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_PATIENT_CARE)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @JoinColumn(name = "PATIENTHISTORY")
    @ManyToOne(fetch = FetchType.LAZY)
    private PatientPersonalHistory patientHistory;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "BLOODPRESSURE")
    private String bloodPressure;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "HEARTRATE")
    private String heartRate;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "WEIGHT")
    private String weight;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "HEIGHT")
    private String height;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "TEMPERATURE")
    private String temperature;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "BODYMASSINDEX")
    private String bodyMassIndex;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "NURSINGNOTES")
    private String nursingNotes;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "REASON")
    private String reason;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "CAREPLAN")
    private String carePlan;

    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "OBSERVATIONS")
    private String observations;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "PHYSICALEXAM")
    private String physicalExam;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "TREATMENT")
    private String treatment;

    @OneToMany(mappedBy = "patientCare", fetch = FetchType.LAZY)
    private List<Agenda> agendas;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param dto constructor from entity to dto
     */
    public PatientCare(PatientCareDto dto) {
        this.id = dto.getId();
        updatePatientCare(dto);
    }

    /**
     * @param dto constructor from dto to entity
     */
    public void updatePatientCare(PatientCareDto dto) {
        this.patientHistory = null;
        this.bloodPressure = dto.getBloodPressure();
        this.heartRate = dto.getHeartRate();
        this.weight = dto.getWeight();
        this.height = dto.getHeight();
        this.temperature = dto.getTemperature();
        this.bodyMassIndex = dto.getBodyMassIndex();
        this.nursingNotes = dto.getNursingNotes();
        this.reason = dto.getReason();
        this.carePlan = dto.getCarePlan();
        this.observations = dto.getObservations();
        this.physicalExam = dto.getPhysicalExam();
        this.treatment = dto.getTreatment();
        this.agendas = null;
        this.version = dto.getVersion();
    }

}
