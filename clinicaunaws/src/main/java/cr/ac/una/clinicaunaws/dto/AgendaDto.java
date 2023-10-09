package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.Agenda;
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
    private String date;
    private String shiftStartTime;
    private String shiftEndTime;
    private Long hourlySlots;
    private List<SlotsDto> slots;
    private List<MedicalAppointmentDto> medicalAppointments;
    private Long version;

    @Override
    public AgendaDto convertFromEntityToDTO(Agenda entity, AgendaDto dto) {
        dto.setDoctor(new DoctorDto(entity.getDoctor()));
        dto.setSlots(DtoMapper.fromEntityList(entity.getSlots(), SlotsDto.class));
        dto.setMedicalAppointments(DtoMapper.fromEntityList(entity.getMedicalAppointments(), MedicalAppointmentDto.class));
        return dto;
    }

    @Override
    public Agenda convertFromDTOToEntity(AgendaDto dto, Agenda entity) {
        return entity;
    }

    /**
     * @param agenda constructor from entity to dto
     */
    public AgendaDto(Agenda entity) {
        this.id = entity.getId();
        this.date = entity.getDate().toString();
        this.shiftStartTime = entity.getShiftStartTime();
        this.shiftEndTime = entity.getShiftEndTime();
        this.hourlySlots = entity.getHourlySlots();
        this.version = entity.getVersion();
        this.slots = new ArrayList<>();
        this.medicalAppointments = new ArrayList<>();
    }

}
