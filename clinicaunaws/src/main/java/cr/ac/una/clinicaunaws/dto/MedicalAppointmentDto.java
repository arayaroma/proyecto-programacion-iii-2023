package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.Agenda;
import cr.ac.una.clinicaunaws.entities.MedicalAppointment;
import cr.ac.una.clinicaunaws.entities.Patient;
import cr.ac.una.clinicaunaws.entities.PatientCare;
import cr.ac.una.clinicaunaws.entities.User;
import cr.ac.una.clinicaunaws.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author arayaroma
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalAppointmentDto implements DtoMapper<MedicalAppointment, MedicalAppointmentDto> {

    private Long id;
    private AgendaDto agenda;
    private PatientDto patient;
    private PatientCareDto patientCare;
    private UserDto scheduledBy;
    private Long slotsNumber;
    private String scheduledDate;
    private String scheduledStartTime;
    private String scheduledEndTime;
    private String state;
    private String reason;
    private String patientPhoneNumber;
    private String patientEmail;
    private Long version;

    @Override
    public MedicalAppointmentDto convertFromEntityToDTO(MedicalAppointment entity, MedicalAppointmentDto dto) {

        PatientCare patientCareEntity = entity.getPatientCare();
        User scheduledByEntity = entity.getScheduledBy();
        Patient patientEntity = entity.getPatient();
        Agenda agendaEntity = entity.getAgenda();
        if (agendaEntity != null) {
            dto.setAgenda(new AgendaDto(agendaEntity));
        }
        if (patientEntity != null) {
            dto.setPatient(new PatientDto(patientEntity));
        }
        if (scheduledByEntity != null) {
            dto.setScheduledBy(new UserDto(scheduledByEntity));
        }
        if (patientCareEntity != null) {
            dto.setPatientCare(new PatientCareDto(patientCareEntity));
        }

        return dto;
    }

    @Override
    public MedicalAppointment convertFromDTOToEntity(MedicalAppointmentDto dto, MedicalAppointment entity) {
        if (dto.getAgenda() != null) {
            entity.setAgenda(new Agenda(dto.getAgenda()));
        }
        if (dto.getPatient() != null) {
            entity.setPatient(new Patient(dto.getPatient()));
        }
        if (dto.getScheduledBy() != null) {
            entity.setScheduledBy(new User(dto.getScheduledBy()));
        }
        if (dto.getPatientCare() != null) {
            entity.setPatientCare(new PatientCare(dto.getPatientCare()));
        }
        return entity;
    }

    /**
     * @param entity constructor to dto
     */
    public MedicalAppointmentDto(MedicalAppointment entity) {
        this.id = entity.getId();
        this.slotsNumber = entity.getSlotsNumber();
        this.scheduledDate = entity.getScheduledDate().toString();
        this.scheduledStartTime = entity.getScheduledStartTime();
        this.scheduledEndTime = entity.getScheduledEndTime();
        this.state = entity.getState();
        this.reason = entity.getReason();
        this.patientPhoneNumber = entity.getPatientPhoneNumber();
        this.patientEmail = entity.getPatientEmail();
        this.version = entity.getVersion();
    }

}
