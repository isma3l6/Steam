package mapper;

import modelo.dto.BibliotecaDto;
import modelo.dto.ResenhaDto;
import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.ResenhaEntidad;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;


public class ResenhaMapper {
    private static UsuarioRepoInMemory usuarioInMemory;
    private static JuegoRepoInMemory juegoRepoInMemory;

    public static ResenhaDto toDTO(ResenhaEntidad r) {

        if (r == null) {
            return null;
        }
        var u=usuarioInMemory.obtenerPorId(r.getUsuaroId());
        var j=juegoRepoInMemory.obtenerPorId(r.getNombreJuegoId());

        return new ResenhaDto(u,j,r.getTexto(),r.isRecomendado(),r.getFechaPublicacion(),r.getFechaEdit(),r.getHorasJugadas()) ;
    }
}
