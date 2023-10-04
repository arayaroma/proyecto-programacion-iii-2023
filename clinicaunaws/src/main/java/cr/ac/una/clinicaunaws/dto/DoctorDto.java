package cr.ac.una.clinicaunaws.dto;

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
    private String code;
    private Long idCard;
    private String shiftStartTime;
    private String shiftEndTime;
    private Long hourlySlots;
    private Long version;

    @Override
    public DoctorDto convertFromEntityToDTO(Doctor entity, DoctorDto dto) {
        return new DoctorDto(entity);
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
        this.code = entity.getCode();
        this.idCard = entity.getIdCard();
        this.shiftStartTime = entity.getShiftStartTime();
        this.shiftEndTime = entity.getShiftEndTime();
        this.hourlySlots = entity.getHourlySlots();
        this.version = entity.getVersion();
    }

}
