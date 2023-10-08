package cr.ac.una.clinicaunaws.entities;

import java.io.Serializable;
import java.time.LocalDate;
import cr.ac.una.clinicaunaws.dto.MedicalAppointmentDto;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static cr.ac.una.clinicaunaws.util.Database.*;

/**
 * 
 * @author arayaroma
 */
@Entity
@Table(name = "TBL_MEDICAL_APPOINTMENT", schema = SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "MedicalAppointment.findAll", query = "SELECT m FROM MedicalAppointment m"),
        @NamedQuery(name = "MedicalAppointment.findById", query = "SELECT m FROM MedicalAppointment m WHERE m.id = :id"),
})
public class MedicalAppointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_MEDICAL_APPOINTMENT, sequenceName = SCHEMA + "."
            + SEQ_MEDICAL_APPOINTMENT, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_MEDICAL_APPOINTMENT)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @Column(name = "AGENDA")
    private Agenda agenda;

    @NotNull
    @Basic(optional = false)
    @Column(name = "PATIENT")
    private Patient patient;

    @NotNull
    @Basic(optional = false)
    @Column(name = "SCHEDULEDBY")
    private User scheduledBy;

    @NotNull
    @Basic(optional = false)
    @Column(name = "SCHEDULEDATE")
    private LocalDate scheduleDate;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 5)
    @Column(name = "SCHEDULETIME")
    private String scheduleTime;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 9)
    @Column(name = "STATE")
    private String state;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "REASON")
    private String reason;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "PATIENTPHONENUMBER")
    private String patientPhoneNumber;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 64)
    @Column(name = "PATIENTEMAIL")
    private String patientEmail;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param dto constructor from entity to dto
     */
    public MedicalAppointment(MedicalAppointmentDto dto) {
        this.id = dto.getId();
        updateMedicalAppointment(dto);
    }

    /**
     * @param dto constructor from dto to entity
     */
    public void updateMedicalAppointment(MedicalAppointmentDto dto) {
        this.agenda = null;
        this.patient = null;
        this.scheduledBy = null;
        this.scheduleDate = LocalDate.parse(dto.getScheduleDate());
        this.scheduleTime = dto.getScheduleTime();
        this.state = dto.getState();
        this.reason = dto.getReason();
        this.patientPhoneNumber = dto.getPatientPhoneNumber();
        this.patientEmail = dto.getPatientEmail();
        this.version = dto.getVersion();
    }
}
