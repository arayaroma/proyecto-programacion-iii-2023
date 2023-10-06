package cr.ac.una.clinicauna.model;

import cr.ac.una.clinicauna.util.DtoMapper;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author enajera
 */
public class DoctorDto implements DtoMapper<DoctorDto, DoctorDto> {

    public SimpleStringProperty id;
    public SimpleStringProperty code;
    public SimpleStringProperty idCard;
    public SimpleStringProperty shiftStartTime;
    public SimpleStringProperty shiftEndTime;
    public SimpleStringProperty hourlySlots;
    public SimpleStringProperty version;

    public DoctorDto() {
        id = new SimpleStringProperty();
        code = new SimpleStringProperty();
        idCard = new SimpleStringProperty();
        shiftStartTime = new SimpleStringProperty();
        shiftEndTime = new SimpleStringProperty();
        hourlySlots = new SimpleStringProperty();
        version = new SimpleStringProperty();

    }

    // All getters
    public Long getId() {
        return Long.valueOf(id.get());
    }

    public String getCode() {
        return code.get();
    }

    public Long getIdCard() {
        return Long.valueOf(idCard.get());
    }

    public String getShiftStartTime() {
        return shiftStartTime.get();
    }

    public String getShiftEndTime() {
        return shiftEndTime.get();
    }

    public Long getHourlySlots() {
        return Long.valueOf(hourlySlots.get());
    }

    public Long getVersion() {
        return Long.valueOf(version.get());
    }

    // All setters
    public void setId(Long id) {
        if (id == null) {
            id = Long.valueOf(0);
        }
        this.id.set(id.toString());
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public void setIdCard(Long idCard) {
        if (idCard == null) {
            idCard = Long.valueOf(0);
        }
        this.idCard.set(idCard.toString());
    }

    public void setShiftStartTime(String shiftStartTime) {
        this.shiftStartTime.set(shiftStartTime);
    }

    public void setShiftEndTime(String shiftEndTime) {
        this.shiftEndTime.set(shiftEndTime);
    }

    public void setHourlySlots(Long hourlySlots) {
        if (hourlySlots == null) {
            hourlySlots = Long.valueOf(0);
        }
        this.hourlySlots.set(hourlySlots.toString());
    }

    public void setVersion(Long version) {
        if (version == null) {
            version = Long.valueOf(0);
        }
        this.version.set(version.toString());
    }

    @Override
    public DoctorDto convertFromGeneratedToDTO(DoctorDto generated, DoctorDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DoctorDto convertFromDTOToGenerated(DoctorDto dto, DoctorDto generated) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
