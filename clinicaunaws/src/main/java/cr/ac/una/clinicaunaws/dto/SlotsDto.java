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
        return new SlotsDto(entity);
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
        this.agenda = null;
        this.medicalAppointment = null;
        this.date = entity.getDate().toString();
        this.timeSlot = entity.getTimeSlot();
        this.available = entity.getAvailable();
        this.version = entity.getVersion();
    }

}
