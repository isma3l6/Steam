package repositorio.interfaz;

import modelo.dto.JuegoDto;

import java.util.List;

public interface IJuego {

    JuegoDto anhadirJuego();

    List<JuegoDto> buscarJuegos();

    List<JuegoDto> consultarJuegos();

    JuegoDto consultarDetalles();

    JuegoDto aplicarDescuentos();

    JuegoDto cambiarEstado();

}
