package mapper;

import modelo.dto.BibliotecaDto;
import modelo.dto.JuegoDto;
import modelo.dto.UsuarioDto;
import modelo.entidad.BibliotecaEntidad;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.util.Optional;

public class BibliotecaMapper {

    public static BibliotecaDto toDTO(BibliotecaEntidad b, UsuarioDto u , JuegoDto j) {

        if (b == null) {
            return null;
        }


        return new BibliotecaDto(u, j,b.getFechaAdquisicion(),b.getHorasJugadas(),b.getJugadoPorUltimavez(),b.getInstalacionType());
    }
}
