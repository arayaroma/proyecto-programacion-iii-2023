package cr.ac.una.clinicaunaws.dto;

import java.util.List;

import cr.ac.una.clinicaunaws.entities.Agenda;
import cr.ac.una.clinicaunaws.entities.Doctor;
import cr.ac.una.clinicaunaws.entities.MedicalAppointment;
import cr.ac.una.clinicaunaws.entities.User;
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
public class AgendaDto implements DtoMapper<Agenda, AgendaDto> {

    private Long id;
    private DoctorDto doctor;
    private String agendaDate;
    private String shiftStartTime;
    private String shiftEndTime;
    private Long hourlySlots;
    private List<SlotsDto> slots;
    private List<MedicalAppointmentDto> medicalAppointments;
    private Long version;

    @Override
    public AgendaDto convertFromEntityToDTO(Agenda entity, AgendaDto dto) {
        Doctor doctorEntity = entity.getDoctor();
        if (doctorEntity != null) {
            dto.setDoctor(new DoctorDto(doctorEntity));
            User user = doctorEntity.getUser();
            if (user != null) {
                dto.getDoctor().setUser(new UserDto(user));
            }
        }
        List<MedicalAppointment> appointments = entity.getMedicalAppointments();
        dto.setMedicalAppointments(DtoMapper.fromEntityList(appointments, MedicalAppointmentDto.class));
        if (dto.getMedicalAppointments() != null) {
            for (int i = 0; i < appointments.size(); i++) {
                dto.getMedicalAppointments().get(i).setScheduledBy(new UserDto(appointments.get(i).getScheduledBy()));
                dto.getMedicalAppointments().get(i).setAgenda(new AgendaDto(appointments.get(i).getAgenda()));
                dto.getMedicalAppointments().get(i).setPatient(new PatientDto(appointments.get(i).getPatient()));
                dto.getMedicalAppointments().get(i).setSlots(DtoMapper.fromEntityList(appointments.get(i).getSlots(), SlotsDto.class));
            }

        }

        dto.setSlots(DtoMapper.fromEntityList(entity.getSlots(), SlotsDto.class));
        return dto;
    }

    @Override
    public Agenda convertFromDTOToEntity(AgendaDto dto, Agenda entity) {
        if (dto.getDoctor() != null) {
            entity.setDoctor(new Doctor(dto.getDoctor()));
            if (dto.getDoctor().getUser() != null) {
                entity.getDoctor().setUser(new User(dto.getDoctor().getUser()));
            }
        }

        return entity;
    }

    /**
     * @param agenda constructor from entity to dto
     */
    public AgendaDto(Agenda entity) {
        this.id = entity.getId();
        this.agendaDate = entity.getAgendaDate().toString();
        this.shiftStartTime = entity.getShiftStartTime();
        this.shiftEndTime = entity.getShiftEndTime();
        this.hourlySlots = entity.getHourlySlots();
        this.version = entity.getVersion();
        this.slots = new ArrayList<>();
        this.medicalAppointments = new ArrayList<>();
    }

}
