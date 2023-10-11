package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.Agenda;
import cr.ac.una.clinicaunaws.entities.Doctor;
import cr.ac.una.clinicaunaws.entities.User;
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
public class AgendaDto implements DtoMapper<Agenda, AgendaDto> {

    private Long id;
    private DoctorDto doctor;
    private String agendaDate;
    private String shiftStartTime;
    private String shiftEndTime;
    private Long hourlySlots;
    private List<SlotsDto> slots;
    private List<MedicalAppointmentDto> medicalAppointments;
    private Long version;

    @Override
    public AgendaDto convertFromEntityToDTO(Agenda entity, AgendaDto dto) {
        dto.setDoctor(new DoctorDto(entity.getDoctor()));
        dto.getDoctor().setUser(new UserDto(entity.getDoctor().getUser()));
        return dto;
    }

    @Override
    public Agenda convertFromDTOToEntity(AgendaDto dto, Agenda entity) {
        entity.setDoctor(new Doctor(dto.getDoctor()));
        entity.getDoctor().setUser(new User(dto.getDoctor().getUser()));
        return entity;
    }

    /**
     * @param agenda constructor from entity to dto
     */
    public AgendaDto(Agenda entity) {
        this.id = entity.getId();
        this.agendaDate = entity.getAgendaDate().toString();
        this.shiftStartTime = entity.getShiftStartTime();
        this.shiftEndTime = entity.getShiftEndTime();
        this.hourlySlots = entity.getHourlySlots();
        this.version = entity.getVersion();
        this.slots = new ArrayList<>();
        this.medicalAppointments = new ArrayList<>();
    }

}
