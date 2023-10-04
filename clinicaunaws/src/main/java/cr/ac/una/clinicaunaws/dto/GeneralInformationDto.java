package cr.ac.una.clinicaunaws.dto;

import cr.ac.una.clinicaunaws.entities.GeneralInformation;
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
public class GeneralInformationDto implements DtoMapper<GeneralInformation, GeneralInformationDto> {

    private Long id;
    private String name;
    private String email;
    private byte[] photo;
    private String htmltemplate;
    private Long version;

    /**
     * @param generalInformation constructor to convert an entity to a dto
     */
    public GeneralInformationDto(GeneralInformation generalInformation) {
        this.id = generalInformation.getId();
        this.name = generalInformation.getName();
        this.email = generalInformation.getEmail();
        this.photo = generalInformation.getPhoto();
        this.htmltemplate = generalInformation.getHtmltemplate();
        this.version = generalInformation.getVersion();
    }

    /**
     * @param entity the entity to be converted
     * @param dto    the dto to be updated
     * @return the dto with the updated information
     */
    @Override
    public GeneralInformationDto convertFromEntityToDTO(GeneralInformation entity, GeneralInformationDto dto) {
        return new GeneralInformationDto(entity);
    }

    /**
     * @param dto    the dto to be converted
     * @param entity the entity to be updated
     * @return the entity with the updated information
     */
    @Override
    public GeneralInformation convertFromDTOToEntity(GeneralInformationDto dto, GeneralInformation entity) {
        return new GeneralInformation(dto);
    }

}