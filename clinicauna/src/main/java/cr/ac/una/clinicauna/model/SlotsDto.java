package cr.ac.una.clinicauna.model;

import javafx.beans.property.SimpleStringProperty;

public class SlotsDto {
    private Long id;
    private AgendaDto agenda;
    private MedicalAppointmentDto medicalAppointment;
    private SimpleStringProperty slotDate;
    private SimpleStringProperty timeSlot;
    private SimpleStringProperty available;
    private Long version;

    public SlotsDto() {
        slotDate = new SimpleStringProperty();
        timeSlot = new SimpleStringProperty();
        available = new SimpleStringProperty();
    }

    public SlotsDto(SlotsDto slotsDto) {
        setSlotDate(slotsDto.getSlotDate());
        setTimeSlot(slotsDto.getTimeSlot());
        setAvailable(slotsDto.getAvailable());
        setID(slotsDto.getID());
        setVersion(slotsDto.getVersion());
    }

    public String getSlotDate() {
        return slotDate.get();
    }

    public String getTimeSlot() {
        return timeSlot.get();
    }

    public String getAvailable() {
        return available.get();
    }

    public AgendaDto getAgenda() {
        return agenda;
    }
    public MedicalAppointmentDto getMedicalAppointment() {
        return medicalAppointment;
    }

    public Long getID() {
        return id;
    }

    public Long getVersion() {
        return version;
    }
    public void setAgenda(AgendaDto agenda) {
        this.agenda = agenda;
    }

    public void setSlotDate(String slotDate) {
        this.slotDate.set(slotDate);
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot.set(timeSlot);
    }

    public void setAvailable(String available) {
        this.available.set(available);
    }

    public void setMedicalAppointment(MedicalAppointmentDto medicalAppointment) {
        this.medicalAppointment = medicalAppointment;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
