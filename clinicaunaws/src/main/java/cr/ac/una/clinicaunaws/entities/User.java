package cr.ac.una.clinicaunaws.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cr.ac.una.clinicaunaws.util.Database.*;

import java.io.Serializable;

import cr.ac.una.clinicaunaws.dto.UserDto;

/**
 * 
 * @author arayaroma
 */
@Entity
@Table(name = "TBL_USER", schema = SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
        @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
        @NamedQuery(name = "User.findByUsernameAndPassword", query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
        @NamedQuery(name = "User.findByActivationCode", query = "SELECT u FROM User u WHERE u.activationCode = :activationCode", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = SEQ_USER, sequenceName = SCHEMA + "." + SEQ_USER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_USER)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "USERNAME", unique = true)
    private String username;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "FIRSTLASTNAME")
    private String firstLastname;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "SECONDLASTNAME")
    private String secondLastname;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 9)
    @Column(name = "IDENTIFICATION", unique = true)
    private String identification;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 64)
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 13)
    @Column(name = "ROLE")
    private String role;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "ISACTIVE")
    private String isActive;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "ISADMIN")
    private String isAdmin;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "PASSWORDCHANGED")
    private String passwordChanged;

    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "ACTIVATIONCODE")
    private String activationCode;

    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 12)
    @Column(name = "LANGUAGE")
    private String language;

    @Lob
    @Column(name = "PROFILEPHOTO")
    private byte[] profilePhoto;

    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * @param userDto constructor from dto to entity
     */
    public User(UserDto userDto) {
        this.id = userDto.getId();
        updateUser(userDto);
    }

    /**
     * @param userDto updates the userDto
     */
    public void updateUser(UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.name = userDto.getName();
        this.firstLastname = userDto.getFirstLastname();
        this.secondLastname = userDto.getSecondLastname();
        this.identification = userDto.getIdentification();
        this.email = userDto.getEmail();
        this.role = userDto.getRole();
        this.phoneNumber = userDto.getPhoneNumber();
        this.isActive = userDto.getIsActive();
        this.isAdmin = userDto.getIsAdmin();
        this.passwordChanged = userDto.getPasswordChanged();
        this.activationCode = userDto.getActivationCode();
        this.profilePhoto = userDto.getProfilePhoto();
        this.version = userDto.getVersion();
    }

}
