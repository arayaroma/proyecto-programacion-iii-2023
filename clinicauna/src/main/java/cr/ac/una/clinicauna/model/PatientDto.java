package cr.ac.una.clinicauna.model;

import javafx.beans.property.SimpleStringProperty;

public class PatientDto {
    private Long id;
    private SimpleStringProperty name;
    private SimpleStringProperty firstLastname;
    private SimpleStringProperty secondLastname;
    private SimpleStringProperty identification;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty email;
    private SimpleStringProperty gender;
    private SimpleStringProperty birthDate;
    private Long version;

    public PatientDto() {
        name = new SimpleStringProperty();
        firstLastname = new SimpleStringProperty();
        secondLastname = new SimpleStringProperty();
        identification = new SimpleStringProperty();
        phoneNumber = new SimpleStringProperty();
        email = new SimpleStringProperty();
        gender = new SimpleStringProperty();
        birthDate = new SimpleStringProperty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public String getFirstLastname() {
        return firstLastname.get();
    }

    public String getSecondLastname() {
        return secondLastname.get();
    }

    public String getIdentification() {
        return identification.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setFirstLastname(String firstLastname) {
        this.firstLastname.set(firstLastname);
    }

    public void setSecondLastname(String secondLastname) {
        this.secondLastname.set(secondLastname);
    }

    public void setIdentification(String identification) {
        this.identification.set(identification);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
