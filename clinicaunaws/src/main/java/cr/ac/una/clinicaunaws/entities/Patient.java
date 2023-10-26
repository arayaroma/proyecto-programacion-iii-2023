package cr.ac.una.clinicaunaws.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import cr.ac.una.clinicaunaws.dto.PatientDto;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.QueryHint;

/**
 *
 * @author arayaroma
 */
@Entity
@Table(name = "TBL_PATIENT", schema = SCHEMA)
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
        @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
})
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_PATIENT, sequenceName = SCHEMA + "." + SEQ_PATIENT, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_PATIENT)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "FIRSTLASTNAME")
    private String firstLastname;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "SECONDLASTNAME")
    private String secondLastname;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 9)
    @Column(name = "IDENTIFICATION", unique = true)
    private String identification;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 64)
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 6)
    @Column(name = "GENDER")
    private String gender;

    @NotNull
    @Basic(optional = false)
    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;

    @OneToOne(mappedBy = "patient", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private PatientPersonalHistory patientPersonalHistory;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<PatientFamilyHistory> patientFamilyHistories;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<MedicalAppointment> medicalAppointments;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param dto constructor from dto to entity
     */
    public Patient(PatientDto dto) {
        this.id = dto.getId();
        updatePatient(dto);
    }

    /**
     * @param dto constructor from entity to dto
     */
    public void updatePatient(PatientDto dto) {
        this.name = dto.getName();
        this.firstLastname = dto.getFirstLastname();
        this.secondLastname = dto.getSecondLastname();
        this.identification = dto.getIdentification();
        this.phoneNumber = dto.getPhoneNumber();
        this.email = dto.getEmail();
        this.gender = dto.getGender();
        this.birthDate = LocalDate.parse(dto.getBirthDate());
        this.version = dto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastname() {
        return firstLastname;
    }

    public void setFirstLastname(String firstLastname) {
        this.firstLastname = firstLastname;
    }

    public String getSecondLastname() {
        return secondLastname;
    }

    public void setSecondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PatientPersonalHistory getPatientPersonalHistory() {
        return patientPersonalHistory;
    }

    public void setPatientPersonalHistory(PatientPersonalHistory patientPersonalHistory) {
        this.patientPersonalHistory = patientPersonalHistory;
    }

    public List<PatientFamilyHistory> getPatientFamilyHistories() {
        return patientFamilyHistories;
    }

    public void setPatientFamilyHistories(List<PatientFamilyHistory> patientFamilyHistories) {
        this.patientFamilyHistories = patientFamilyHistories;
    }

    public List<MedicalAppointment> getMedicalAppointments() {
        return medicalAppointments;
    }

    public void setMedicalAppointments(List<MedicalAppointment> medicalAppointments) {
        this.medicalAppointments = medicalAppointments;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "";
    }
    
    
}
