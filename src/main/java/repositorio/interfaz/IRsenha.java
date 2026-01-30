package repositorio.interfaz;

import modelo.dto.ResenhaDto;

import java.util.List;

public interface IRsenha {

    ResenhaDto escribirResenha() ;

    ResenhaDto eliminarResenha();

    ResenhaDto mostrarResenha();

    ResenhaDto ocultarResenhar();

    List <ResenhaDto> consultarEstadistica();

    List <ResenhaDto>  mostrarResenhaUsuario();

    
}
