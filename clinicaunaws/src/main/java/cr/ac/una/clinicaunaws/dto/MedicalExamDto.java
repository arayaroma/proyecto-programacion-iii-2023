package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.MedicalExam;
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
public class MedicalExamDto implements DtoMapper<MedicalExam, MedicalExamDto> {

    private Long id;
    private PatientPersonalHistoryDto patientHistory;
    private String name;
    private String date;
    private String notes;
    private Long version;

    @Override
    public MedicalExamDto convertFromEntityToDTO(MedicalExam entity, MedicalExamDto dto) {
        return new MedicalExamDto(entity);
    }

    @Override
    public MedicalExam convertFromDTOToEntity(MedicalExamDto dto, MedicalExam entity) {
        return new MedicalExam(dto);
    }

    /**
     * @param entity constructor to dto
     */
    public MedicalExamDto(MedicalExam entity) {
        this.id = entity.getId();
        this.patientHistory = null;
        this.name = entity.getName();
        this.date = entity.getDate().toString();
        this.notes = entity.getNotes();
        this.version = entity.getVersion();
    }

}
