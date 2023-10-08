package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.PatientCare;
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
public class PatientCareDto implements DtoMapper<PatientCare, PatientCareDto> {

    private Long id;
    private PatientPersonalHistoryDto patientHistory;
    private String bloodPressure;
    private String heartRate;
    private String weight;
    private String height;
    private String temperature;
    private String bodyMassIndex;
    private String nursingNotes;
    private String reason;
    private String carePlan;
    private String observations;
    private String physicalExam;
    private String treatment;
    private Long version;

    @Override
    public PatientCareDto convertFromEntityToDTO(PatientCare entity, PatientCareDto dto) {
        PatientCareDto patientCareDto = new PatientCareDto(entity);

        patientCareDto.setPatientHistory(new PatientPersonalHistoryDto(entity.getPatientHistory()));
        return patientCareDto;
    }

    @Override
    public PatientCare convertFromDTOToEntity(PatientCareDto dto, PatientCare entity) {
        return new PatientCare(dto);
    }

    /**
     * @param entity constructor to dto
     */
    public PatientCareDto(PatientCare entity) {
        this.id = entity.getId();
        this.patientHistory = null;
        this.bloodPressure = entity.getBloodPressure();
        this.heartRate = entity.getHeartRate();
        this.weight = entity.getWeight();
        this.height = entity.getHeight();
        this.temperature = entity.getTemperature();
        this.bodyMassIndex = entity.getBodyMassIndex();
        this.nursingNotes = entity.getNursingNotes();
        this.reason = entity.getReason();
        this.carePlan = entity.getCarePlan();
        this.observations = entity.getObservations();
        this.physicalExam = entity.getPhysicalExam();
        this.treatment = entity.getTreatment();
        this.version = entity.getVersion();
    }

}
