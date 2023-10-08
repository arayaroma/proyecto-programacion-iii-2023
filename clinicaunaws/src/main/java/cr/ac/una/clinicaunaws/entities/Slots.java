package cr.ac.una.clinicaunaws.entities;

import java.io.Serializable;
import java.time.LocalDate;
import cr.ac.una.clinicaunaws.dto.SlotsDto;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static cr.ac.una.clinicaunaws.util.Database.*;

@Entity
@Table(name = "TBL_SLOTS", schema = SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Slots.findAll", query = "SELECT s FROM Slots s"),
        @NamedQuery(name = "Slots.findById", query = "SELECT s FROM Slots s WHERE s.id = :id"),
})
public class Slots implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_SLOTS, sequenceName = SCHEMA + "." + SEQ_SLOTS, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_SLOTS)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @JoinColumn(name = "AGENDA")
    @ManyToOne(fetch = FetchType.LAZY)
    private Agenda agenda;

    @NotNull
    @Basic(optional = false)
    @JoinColumn(name = "MEDICALAPPOINTMENT")
    @ManyToOne(fetch = FetchType.LAZY)
    private MedicalAppointment medicalAppointment;

    @NotNull
    @Basic(optional = false)
    @Column(name = "DATE")
    private LocalDate date;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 5)
    @Column(name = "TIMESLOT")
    private String timeSlot;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 12)
    @Column(name = "AVAILABLE")
    private String available;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param dto constructor to dto
     */
    public Slots(SlotsDto dto) {
        this.id = dto.getId();
        updateSlots(dto);
    }

    /**
     * @param dto constructor from dto to entity
     */
    public void updateSlots(SlotsDto dto) {
        this.agenda = null;
        this.medicalAppointment = null;
        this.date = LocalDate.parse(dto.getDate());
        this.timeSlot = dto.getTimeSlot();
        this.available = dto.getAvailable();
        this.version = dto.getVersion();
    }
}
