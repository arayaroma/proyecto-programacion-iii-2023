package cr.ac.una.clinicaunaws.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
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
import java.util.List;

import cr.ac.una.clinicaunaws.dto.DoctorDto;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.QueryHint;

/**
 * 
 * @author arayaroma
 */
@Entity
@Table(name = "TBL_DOCTOR", schema = SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Doctor.findAll", query = "SELECT d FROM Doctor d", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
        @NamedQuery(name = "Doctor.findById", query = "SELECT d FROM Doctor d WHERE d.id = :id", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
})
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_DOCTOR, sequenceName = SCHEMA + "." + SEQ_DOCTOR, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_DOCTOR)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID", referencedColumnName = "ID", unique = true)
    private User user;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 10)
    @Column(name = "CODE", unique = true)
    private String code;

    @NotNull
    @Basic(optional = false)
    @Column(name = "IDCARD")
    private Long idCard;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 5)
    @Column(name = "SHIFTSTARTTIME")
    private String shiftStartTime;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 5)
    @Column(name = "SHIFTENDTIME")
    private String shiftEndTime;

    @NotNull
    @Basic(optional = false)
    @Column(name = "HOURLYSLOTS")
    private Long hourlySlots;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Agenda> agendas;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param dto constructor from entity to dto
     */
    public Doctor(DoctorDto dto) {
        this.id = dto.getId();
        updateDoctor(dto);
    }

    /**
     * @param dto to be updated
     */
    public void updateDoctor(DoctorDto dto) {
        this.code = dto.getCode();
        this.idCard = dto.getIdCard();
        this.shiftStartTime = dto.getShiftStartTime();
        this.shiftEndTime = dto.getShiftEndTime();
        this.hourlySlots = dto.getHourlySlots();
        this.version = dto.getVersion();
    }

}
