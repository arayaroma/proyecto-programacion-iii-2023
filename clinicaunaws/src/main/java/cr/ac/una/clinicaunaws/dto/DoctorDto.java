package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.Doctor;
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
        DoctorDto doctorDto = new DoctorDto(entity);

        doctorDto.setUser(new UserDto(entity.getUser()));

        // Set the Agendas List
        for (int i = 0; i < entity.getAgendas().size(); i++) {
            doctorDto.getAgendas().add(new AgendaDto(entity.getAgendas().get(i)));
        }
        return doctorDto;
    }

    @Override
    public Doctor convertFromDTOToEntity(DoctorDto dto, Doctor entity) {
        return new Doctor(dto);
    }

    /**
     * @param doctor constructor from entity to dto
     */
    public DoctorDto(Doctor entity) {
        this.id = entity.getId();
        this.user = null;
        this.code = entity.getCode();
        this.idCard = entity.getIdCard();
        this.shiftStartTime = entity.getShiftStartTime();
        this.shiftEndTime = entity.getShiftEndTime();
        this.hourlySlots = entity.getHourlySlots();
        this.version = entity.getVersion();
    }

}
