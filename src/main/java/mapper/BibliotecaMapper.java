package mapper;

import modelo.dto.BibliotecaDto;
import modelo.entidad.BibliotecaEntidad;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.util.Optional;

public class BibliotecaMapper {
    private static UsuarioRepoInMemory usuarioInMemory;
    private static  JuegoRepoInMemory juegoRepoInMemory;

    public static BibliotecaDto toDTO(BibliotecaEntidad b) {

        if (b == null) {
            return null;
        }
        var u=usuarioInMemory.obtenerPorId(b.getIdUsuario());
        var j=juegoRepoInMemory.obtenerPorId(b.getIdJuego());

        return new BibliotecaDto(u, j,b.getFechaAdquisicion(),b.getHorasJugadas(),b.getJugadoPorUltimavez(),b.getInstalacionType());
    }
}
