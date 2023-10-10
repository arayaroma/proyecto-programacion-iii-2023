package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.Doctor;
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
public class DoctorDto implements DtoMapper<Doctor, DoctorDto> {

    private Long id;
    private UserDto user;
    private String code;
    private Long idCard;
    private String shiftStartTime;
    private String shiftEndTime;
    private Long hourlySlots;
    private List<AgendaDto> agendas;
    private Long version;

    @Override
    public DoctorDto convertFromEntityToDTO(Doctor entity, DoctorDto dto) {
        dto.setAgendas(DtoMapper.fromEntityList(entity.getAgendas(), AgendaDto.class));

        return dto;
    }

    @Override
    public Doctor convertFromDTOToEntity(DoctorDto dto, Doctor entity) {
        return entity;
    }

    /**
     * @param doctor constructor from entity to dto
     */
    public DoctorDto(Doctor entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.idCard = entity.getIdCard();
        this.shiftStartTime = entity.getShiftStartTime();
        this.shiftEndTime = entity.getShiftEndTime();
        this.hourlySlots = entity.getHourlySlots();
        this.version = entity.getVersion();
        this.agendas = new ArrayList<>();
    }

}
