package controlador;

import modelo.dto.BibliotecaDto;
import repositorio.interfaz.IBiblioteca;

import java.util.List;

public class BibliotecaControlador implements IBiblioteca {
    @Override
    public List<BibliotecaDto> mostrarBiblioteca() {
        return List.of();
    }

    @Override
    public BibliotecaDto anhadirJuego() {
        return null;
    }

    @Override
    public BibliotecaDto elliminarJuegp() {
        return null;
    }

    @Override
    public List<BibliotecaDto> actualizarTiempoJuego() {
        return List.of();
    }

    @Override
    public BibliotecaDto consultarUltimaSesion() {
        return null;
    }

    @Override
    public List<BibliotecaDto> filtrarBiblioteca() {
        return List.of();
    }

    @Override
    public List<BibliotecaDto> verestadisticas() {
        return List.of();
    }
}
