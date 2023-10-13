package cr.ac.una.clinicaunaws.dto;

import java.util.ArrayList;
import java.util.List;

import cr.ac.una.clinicaunaws.entities.Patient;
import cr.ac.una.clinicaunaws.entities.PatientCare;
import cr.ac.una.clinicaunaws.entities.PatientPersonalHistory;
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
    private String patientCareDate;
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
    private List<MedicalAppointmentDto> medicalAppointments;
    private Long version;

    @Override
    public PatientCareDto convertFromEntityToDTO(PatientCare entity, PatientCareDto dto) {
        dto.setPatientHistory(new PatientPersonalHistoryDto(entity.getPatientHistory()));
        dto.getPatientHistory().setPatient(new PatientDto(entity.getPatientHistory().getPatient()));
        return dto;
    }

    @Override
    public PatientCare convertFromDTOToEntity(PatientCareDto dto, PatientCare entity) {
        entity.setPatientHistory(new PatientPersonalHistory(dto.getPatientHistory()));
        entity.getPatientHistory().setPatient(new Patient(dto.getPatientHistory().getPatient()));
        return entity;
    }

    /**
     * @param entity constructor to dto
     */
    public PatientCareDto(PatientCare entity) {
        this.id = entity.getId();
        this.patientCareDate = entity.getPatientCareDate().toString();
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
        this.medicalAppointments = new ArrayList<>();
        this.version = entity.getVersion();
    }

}
