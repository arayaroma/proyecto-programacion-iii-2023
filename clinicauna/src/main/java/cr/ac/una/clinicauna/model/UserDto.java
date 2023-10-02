package cr.ac.una.clinicauna.model;

import cr.ac.una.clinicauna.util.DtoMapper;

/**
 *
 * @author estebannajera
 */
public class UserDto implements DtoMapper<UserDto, UserDto> {

    @Override
    public UserDto convertFromGeneratedToDTO(UserDto generated, UserDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UserDto convertFromDTOToGenerated(UserDto dto, UserDto generated) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
