package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.MedicalAppointment;
import cr.ac.una.clinicaunaws.util.DtoMapper;
import java.util.ArrayList;
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

        dto.setAgenda(new AgendaDto(entity.getAgenda()));
        dto.setPatient(new PatientDto(entity.getPatient()));
        dto.setScheduledBy(new UserDto(entity.getScheduledBy()));
        dto.setPatientCare(new PatientCareDto(entity.getPatientCare()));
        // Set the Slots List
        dto.setSlots(DtoMapper.fromEntityList(entity.getSlots(), SlotsDto.class));
        return dto;
    }

    @Override
    public MedicalAppointment convertFromDTOToEntity(MedicalAppointmentDto dto, MedicalAppointment entity) {
    return entity;
    }

    /**
     * @param entity constructor to dto
     */
    public MedicalAppointmentDto(MedicalAppointment entity) {
        this.id = entity.getId();
        this.scheduledDate = entity.getScheduledDate().toString();
        this.scheduledTime = entity.getScheduledTime();
        this.state = entity.getState();
        this.reason = entity.getReason();
        this.patientPhoneNumber = entity.getPatientPhoneNumber();
        this.patientEmail = entity.getPatientEmail();
        this.version = entity.getVersion();
        this.slots = new ArrayList<>();
    }

}
