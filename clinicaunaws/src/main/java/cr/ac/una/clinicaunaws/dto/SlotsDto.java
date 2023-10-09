package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.Slots;
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
public class SlotsDto implements DtoMapper<Slots, SlotsDto> {

    private Long id;
    private AgendaDto agenda;
    private MedicalAppointmentDto medicalAppointment;
    private String date;
    private String timeSlot;
    private String available;
    private Long version;

    @Override
    public SlotsDto convertFromEntityToDTO(Slots entity, SlotsDto dto) {
        SlotsDto slotsDto = new SlotsDto(entity);

        slotsDto.setAgenda(new AgendaDto(entity.getAgenda()));
        slotsDto.setMedicalAppointment(new MedicalAppointmentDto(entity.getMedicalAppointment()));
        return slotsDto;
    }

    @Override
    public Slots convertFromDTOToEntity(SlotsDto dto, Slots entity) {
        return new Slots(dto);
    }

    /**
     * @param entity constructor to dto
     */
    public SlotsDto(Slots entity) {
        this.id = entity.getId();
        this.date = entity.getDate().toString();
        this.timeSlot = entity.getTimeSlot();
        this.available = entity.getAvailable();
        this.version = entity.getVersion();
    }

}
