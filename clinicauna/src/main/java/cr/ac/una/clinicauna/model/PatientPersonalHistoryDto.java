package cr.ac.una.clinicauna.model;

import cr.ac.una.clinicauna.util.DtoMapper;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author estebannajera
 */
public class PatientPersonalHistoryDto implements DtoMapper<PatientPersonalHistoryDto, PatientPersonalHistoryDto> {

    public Long id;
    public PatientDto patient;
    public SimpleStringProperty pathological;
    public SimpleStringProperty hospitalizations;
    public SimpleStringProperty surgical;
    public SimpleStringProperty allergies;
    public SimpleStringProperty treatments;
    public Long version;

    public PatientPersonalHistoryDto() {
        allergies = new SimpleStringProperty();
        pathological = new SimpleStringProperty();
        treatments = new SimpleStringProperty();
        hospitalizations = new SimpleStringProperty();
        surgical = new SimpleStringProperty();
    }

    public PatientPersonalHistoryDto(PatientPersonalHistoryDto patientPersonalHistoryDto) {
        this();
        setId(patientPersonalHistoryDto.getId());
        setPathological(patientPersonalHistoryDto.getPathological());
        setHospitalizations(patientPersonalHistoryDto.getHospitalizations());
        setSurgical(patientPersonalHistoryDto.getSurgical());
        setAllergies(patientPersonalHistoryDto.getAllergies());
        setTreatments(patientPersonalHistoryDto.getTreatments());
        setVersion(patientPersonalHistoryDto.getVersion());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public String getPathological() {
        return pathological.get();
    }

    public void setPathological(String pathological) {
        this.pathological.set(pathological);
    }

    public String getHospitalizations() {
        return hospitalizations.get();
    }

    public void setHospitalizations(String hospitalizations) {
        this.hospitalizations.set(hospitalizations);
    }

    public String getSurgical() {
        return surgical.get();
    }

    public void setSurgical(String surgical) {
        this.surgical.set(surgical);
    }

    public String getAllergies() {
        return allergies.get();
    }

    public void setAllergies(String allergies) {
        this.allergies.set(allergies);
    }

    public String getTreatments() {
        return treatments.get();
    }

    public void setTreatments(String treatments) {
        this.treatments.set(treatments);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public PatientPersonalHistoryDto convertFromGeneratedToDTO(PatientPersonalHistoryDto generated, PatientPersonalHistoryDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PatientPersonalHistoryDto convertFromDTOToGenerated(PatientPersonalHistoryDto dto, PatientPersonalHistoryDto generated) {
        generated.setPatient(new PatientDto(dto.getPatient()));
        return generated;
    }

}
