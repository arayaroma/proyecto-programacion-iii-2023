package cr.ac.una.clinicauna.model;

import cr.ac.una.clinicauna.util.DtoMapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author estebannajera
 */
public class UserDto implements DtoMapper<UserDto, UserDto> {

    private SimpleLongProperty id;
    public SimpleStringProperty username;
    public SimpleStringProperty password;
    public SimpleStringProperty name;
    public SimpleStringProperty firstLastname;
    public SimpleStringProperty secondLastname;
    public SimpleStringProperty identification;
    public SimpleStringProperty email;
    public ObjectProperty<String> role;
    public SimpleStringProperty phoneNumber;
    public SimpleStringProperty isActive;
    public SimpleStringProperty isAdmin;
    public SimpleStringProperty passwordChanged;
    public SimpleStringProperty activationCode;
    public SimpleStringProperty language;
    private byte[] profilePhoto;
    public SimpleLongProperty version;

    public UserDto() {
        this.id = new SimpleLongProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.firstLastname = new SimpleStringProperty();
        this.secondLastname = new SimpleStringProperty();
        this.identification = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.role = new SimpleObjectProperty<>();
        this.phoneNumber = new SimpleStringProperty();
        this.isActive = new SimpleStringProperty();
        this.isAdmin = new SimpleStringProperty();
        this.passwordChanged = new SimpleStringProperty();
        this.activationCode = new SimpleStringProperty();
        this.language = new SimpleStringProperty();
        this.version = new SimpleLongProperty();
    }

    public Long getId() {
        return id.get();
    }

    public String getUsername() {
        return this.username.getValue();
    }

    public String getPassword() {
        return this.password.getValue();
    }

    public String getName() {
        return this.name.getValue();
    }

    public String getFirstLastname() {
        return this.firstLastname.getValue();
    }

    public String getSecondLastname() {
        return this.secondLastname.getValue();
    }

    public String getIdentification() {
        return this.identification.getValue();
    }

    public String getEmail() {
        return this.email.getValue();
    }

    public String getRole() {
        return this.role.getValue();
    }

    public String getPhoneNumber() {
        return this.phoneNumber.getValue();
    }

    public String getIsActive() {
        return this.isActive.getValue();
    }

    public String getIsAdmin() {
        return this.isAdmin.getValue();
    }

    public String getPasswordChanged() {
        return this.passwordChanged.getValue();
    }

    public String getActivationCode() {
        return this.activationCode.getValue();
    }

    public String getLanguage() {
        return this.language.getValue();
    }

    public byte[] getProfilePhoto() {
        return this.profilePhoto;
    }

    public Long getVersion() {
        return this.version.getValue();
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

    public void setRole(String role) {
        this.role.set(role);
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public void setIsActive(String isActive) {
        this.isActive.set(isActive);
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public void setPasswordChanged(String passwordChanged) {
        this.passwordChanged.set(passwordChanged);
    }

    public void setActivationCode(String activationCode) {
        this.activationCode.set(activationCode);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setIdentification(String identification) {
        this.identification.set(identification);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public void setVersion(Long version) {
        this.version.set(version);
    }

    public String parseLanguage(String language) {
        switch (language) {
            case "Inglés":
                return "ENGLISH";
            case "Español":
                return "SPANISH";
            case "English":
                return "ENGLISH";
            case "Spanish":
                return "SPANISH";
        }
        return "SPANISH";
    }

    public String parseRole(String role) {
        switch (role) {
            case "administrador":
                setIsAdmin("Y");
                return "ADMINISTRATOR";
            case "administrator":
                setIsAdmin("Y");
                return "ADMINISTRATOR";
            case "doctor":
                setIsAdmin("N");
                return "DOCTOR";
            case "recepcionista":
                setIsAdmin("N");
                return "RECEPCIONIST";
            case "recepcionist":
                setIsAdmin("N");
                return "RECEPCIONIST";
        }
        return "RECEPCIONIST";
    }

    @Override
    public UserDto convertFromGeneratedToDTO(UserDto generated, UserDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from        
    }

    @Override
    public UserDto convertFromDTOToGenerated(UserDto dto, UserDto generated) {
        dto.parseLanguage(generated.getLanguage());
        dto.parseRole(generated.getRole());
        return dto;
    }

}
