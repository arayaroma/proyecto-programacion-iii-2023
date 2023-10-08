package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.MedicalAppointment;
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
    private UserDto scheduledBy;
    private String scheduledDate;
    private String scheduledTime;
    private String state;
    private String reason;
    private String patientPhoneNumber;
    private String patientEmail;
    private List<SlotsDto> slots;
    private Long version;

    @Override
    public MedicalAppointmentDto convertFromEntityToDTO(MedicalAppointment entity, MedicalAppointmentDto dto) {
        MedicalAppointmentDto medicalAppointmentDto = new MedicalAppointmentDto(entity);

        medicalAppointmentDto.setAgenda(new AgendaDto(entity.getAgenda()));
        medicalAppointmentDto.setPatient(new PatientDto(entity.getPatient()));
        medicalAppointmentDto.setScheduledBy(new UserDto(entity.getScheduledBy()));

        // set the doctor
        medicalAppointmentDto.getAgenda()
                .setDoctor(new DoctorDto(entity.getAgenda().getDoctor()));
        // set the patient care
        medicalAppointmentDto.getAgenda()
                .setPatientCare(new PatientCareDto(entity.getAgenda().getPatientCare()));
        // set the patient personal history
        medicalAppointmentDto.getAgenda()
                .getPatientCare()
                .setPatientHistory(
                        new PatientPersonalHistoryDto(entity.getAgenda().getPatientCare().getPatientHistory()));
        return medicalAppointmentDto;
    }

    @Override
    public MedicalAppointment convertFromDTOToEntity(MedicalAppointmentDto dto, MedicalAppointment entity) {
        return new MedicalAppointment(dto);
    }

    /**
     * @param entity constructor to dto
     */
    public MedicalAppointmentDto(MedicalAppointment entity) {
        this.id = entity.getId();
        this.agenda = null;
        this.patient = null;
        this.scheduledBy = null;
        this.scheduledDate = entity.getScheduledDate().toString();
        this.scheduledTime = entity.getScheduledTime();
        this.state = entity.getState();
        this.reason = entity.getReason();
        this.patientPhoneNumber = entity.getPatientPhoneNumber();
        this.patientEmail = entity.getPatientEmail();
        this.slots = null;
        this.version = entity.getVersion();
    }

}
