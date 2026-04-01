package mapper;

import modelo.dto.BibliotecaDto;
import modelo.dto.CompraDto;
import modelo.entidad.CompraEntidad;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

public class CompraMapper {

    private static UsuarioRepoInMemory usuarioInMemory;
    private static JuegoRepoInMemory juegoRepoInMemory;

    public static CompraDto toDTO(CompraEntidad b) {

        if (b == null) {
            return null;
        }
        var u=usuarioInMemory.obtenerPorId(b.getIdUsuario());
        var j=juegoRepoInMemory.obtenerPorId(b.getIdJuego());

        return new CompraDto(u, j,b.getPrecio(),b.getDescuento(),b.getDescuento()/100* b.getPrecio(),b.getMetodoPagoType());
    }
}
