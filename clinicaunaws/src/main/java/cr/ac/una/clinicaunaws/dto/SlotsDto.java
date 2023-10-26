package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.Agenda;
import cr.ac.una.clinicaunaws.entities.MedicalAppointment;
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
    private String slotDate;
    private String timeSlot;
    private String available;
    private Long version;

    @Override
    public SlotsDto convertFromEntityToDTO(Slots entity, SlotsDto dto) {
        Agenda agendaEntity = entity.getAgenda();
        if (agendaEntity != null) {
            dto.setAgenda(new AgendaDto(agendaEntity));
        }
        MedicalAppointment medicalAppointmentEntity = entity.getMedicalAppointment();
        if (medicalAppointmentEntity != null) {
            dto.setMedicalAppointment(new MedicalAppointmentDto(medicalAppointmentEntity));
        }
        return dto;
    }

    @Override
    public Slots convertFromDTOToEntity(SlotsDto dto, Slots entity) {
        if(dto.getAgenda() != null){
            entity.setAgenda(new Agenda(dto.getAgenda()));
        }
        if (dto.getMedicalAppointment() != null) {
            entity.setMedicalAppointment(new MedicalAppointment(dto.getMedicalAppointment()));
        }
        return entity;
    }

    /**
     * @param entity constructor to dto
     */
    public SlotsDto(Slots entity) {
        this.id = entity.getId();
        this.slotDate = entity.getSlotDate().toString();
        this.timeSlot = entity.getTimeSlot();
        this.available = entity.getAvailable();
        this.version = entity.getVersion();
    }

}
