package mapper;

import modelo.dto.UsuarioDto;
import modelo.entidad.UsuarioEntidad;

public class UsuarioMapper {


    public static UsuarioDto toDTO(UsuarioEntidad usuario) {

        if (usuario == null) {
            return null;
        }
        return new UsuarioDto(usuario.getNombreUsuario(), usuario.getEmail(), usuario.getNombre(), usuario.getApellido(), usuario.getFechaNacimiento());
    }
}
