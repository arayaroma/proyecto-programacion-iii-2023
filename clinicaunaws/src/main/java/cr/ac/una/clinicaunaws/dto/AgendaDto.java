package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.Agenda;
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
public class AgendaDto implements DtoMapper<Agenda, AgendaDto> {

    private Long id;
    private DoctorDto doctor;
    private PatientCareDto patientCare;
    private String date;
    private String shiftStartTime;
    private String shiftEndTime;
    private Long hourlySlots;
    private List<SlotsDto> slots;
    private List<MedicalAppointmentDto> medicalAppointments;
    private Long version;

    @Override
    public AgendaDto convertFromEntityToDTO(Agenda entity, AgendaDto dto) {
        AgendaDto agendaDto = new AgendaDto(entity);

        agendaDto.setDoctor(new DoctorDto(entity.getDoctor()));
        agendaDto.setPatientCare(new PatientCareDto(entity.getPatientCare()));

        // Set the patient history to the patient care
        agendaDto.getPatientCare()
                .setPatientHistory(new PatientPersonalHistoryDto(entity
                        .getPatientCare()
                        .getPatientHistory()));
        // Set the patient to the patient history
        agendaDto.getPatientCare()
                .getPatientHistory().setPatient(new PatientDto(entity
                        .getPatientCare()
                        .getPatientHistory()
                        .getPatient()));
        return agendaDto;
    }

    @Override
    public Agenda convertFromDTOToEntity(AgendaDto dto, Agenda entity) {
        return new Agenda(dto);
    }

    /**
     * @param agenda constructor from entity to dto
     */
    public AgendaDto(Agenda entity) {
        this.id = entity.getId();
        this.doctor = null;
        this.patientCare = null;
        this.date = entity.getDate().toString();
        this.shiftStartTime = entity.getShiftStartTime();
        this.shiftEndTime = entity.getShiftEndTime();
        this.hourlySlots = entity.getHourlySlots();
        this.slots = null;
        this.medicalAppointments = null;
        this.version = entity.getVersion();
    }

}
