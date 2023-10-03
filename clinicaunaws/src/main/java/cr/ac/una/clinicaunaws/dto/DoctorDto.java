package cr.ac.una.clinicaunaws.dto;

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
public class DoctorDto {
    private Long id;
    private String code;
    private Long idCard;
    private String shiftStartTime;
    private String shiftEndTime;
    private Long hourlySlots;
    private Long version;
}
