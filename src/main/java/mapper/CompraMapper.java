package mapper;

import modelo.dto.BibliotecaDto;
import modelo.dto.CompraDto;
import modelo.dto.JuegoDto;
import modelo.dto.UsuarioDto;
import modelo.entidad.CompraEntidad;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

public class CompraMapper {

    private static UsuarioRepoInMemory usuarioInMemory;
    private static JuegoRepoInMemory juegoRepoInMemory;

    public static CompraDto toDTO(CompraEntidad b, UsuarioDto u, JuegoDto j) {

        if (b == null) {
            return null;
        }


        return new CompraDto(u, j,b.getPrecio(),b.getDescuento(),b.getDescuento()/100* b.getPrecio(),b.getMetodoPagoType());
    }
}
