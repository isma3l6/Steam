package controlador;

import modelo.dto.JuegoDto;
import repositorio.interfaz.IJuego;

import java.util.List;

public class JuegoControlador implements IJuego {
    @Override
    public JuegoDto anhadirJuego() {
        return null;
    }

    @Override
    public List<JuegoDto> buscarJuegos() {
        return List.of();
    }

    @Override
    public List<JuegoDto> consultarJuegos() {
        return List.of();
    }

    @Override
    public JuegoDto consultarDetalles() {
        return null;
    }

    @Override
    public JuegoDto aplicarDescuentos() {
        return null;
    }

    @Override
    public JuegoDto cambiarEstado() {
        return null;
    }
}
