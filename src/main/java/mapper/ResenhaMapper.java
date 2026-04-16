package mapper;

import modelo.dto.BibliotecaDto;
import modelo.dto.JuegoDto;
import modelo.dto.ResenhaDto;
import modelo.dto.UsuarioDto;
import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.ResenhaEntidad;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;


public class ResenhaMapper {
    private static UsuarioRepoInMemory usuarioInMemory;
    private static JuegoRepoInMemory juegoRepoInMemory;

    public static ResenhaDto toDTO(ResenhaEntidad r, UsuarioDto u, JuegoDto j) {

        if (r == null) {
            return null;
        }


        return new ResenhaDto(u,j,r.getTexto(),r.isRecomendado(),r.getFechaPublicacion(),r.getFechaEdit(),r.getHorasJugadas()) ;
    }
}
