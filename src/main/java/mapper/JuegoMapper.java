package mapper;

import modelo.dto.JuegoDto;
import modelo.entidad.JuegoEntidad;

public class JuegoMapper {
    public static JuegoDto toDTO(JuegoEntidad juego) {

        if (juego == null) {
            return null;
        }
        return new JuegoDto(juego.getTitulo(), juego.getDesarrollador(), juego.getFechaLanzamiento(), juego.getCategoriaType(), juego.getPrecioBase());
    }
}