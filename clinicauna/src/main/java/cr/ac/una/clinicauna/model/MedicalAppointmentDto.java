package cr.ac.una.clinicauna.model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author estebannajera
 */
public class MedicalAppointmentDto {
    private Long id;
    private AgendaDto agenda;
    private PatientDto patient;
    private PatientCareDto patientCare;
    private UserDto scheduledBy;
    private SimpleStringProperty scheduledDate;
    private SimpleStringProperty scheduledTime;
    private SimpleStringProperty state;
    private SimpleStringProperty reason;
    private SimpleStringProperty patientPhoneNumber;
    private SimpleStringProperty patientEmail;
    private List<SlotsDto> slots;
    private Long version;

    public MedicalAppointmentDto() {
        scheduledDate = new SimpleStringProperty();
        scheduledTime = new SimpleStringProperty();
        state = new SimpleStringProperty();
        reason = new SimpleStringProperty();
        patientPhoneNumber = new SimpleStringProperty();
        patientEmail = new SimpleStringProperty();
    }

    public MedicalAppointmentDto(MedicalAppointmentDto medicalAppointmentDto) {
        setId(medicalAppointmentDto.getId());
        setScheduledDate(medicalAppointmentDto.getScheduledDate());
        setScheduledTime(medicalAppointmentDto.getScheduledTime());
        setState(medicalAppointmentDto.getState());
        setReason(medicalAppointmentDto.getReason());
        setPatientPhoneNumber(medicalAppointmentDto.getPatientPhoneNumber());
        setPatientEmail(medicalAppointmentDto.getPatientEmail());
        setVersion(medicalAppointmentDto.getVersion());
    }

    public Long getId() {
        return id;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public String getScheduledDate() {
        return scheduledDate.get();
    }

    public String getScheduledTime() {
        return scheduledTime.get();
    }

    public String getState() {
        return state.get();
    }

    public String getReason() {
        return reason.get();
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber.get();
    }

    public String getPatientEmail() {
        return patientEmail.get();
    }

    public Long getVersion() {
        return version;
    }

    public List<SlotsDto> getSlots() {
        return slots;
    }

    public AgendaDto getAgenda() {
        return agenda;
    }

    public PatientCareDto getPatientCare() {
        return patientCare;
    }

    public void setPatientCare(PatientCareDto patientCare) {
        this.patientCare = patientCare;
    }

    public void setAgenda(AgendaDto agenda) {
        this.agenda = agenda;
    }

    public void setSlots(List<SlotsDto> slots) {
        this.slots = slots;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate.set(scheduledDate);
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime.set(scheduledTime);
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public void setReason(String reason) {
        this.reason.set(reason);
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber.set(patientPhoneNumber);
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail.set(patientEmail);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public UserDto getScheduledBy() {
        return scheduledBy;
    }

    public void setScheduledBy(UserDto scheduledBy) {
        this.scheduledBy = scheduledBy;
    }

}
