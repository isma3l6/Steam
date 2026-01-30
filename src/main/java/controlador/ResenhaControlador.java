package controlador;

import modelo.dto.ResenhaDto;
import repositorio.interfaz.IResenha;

import java.util.List;

public class ResenhaControlador implements IResenha {
    @Override
    public ResenhaDto escribirResenha() {
        return null;
    }

    @Override
    public ResenhaDto eliminarResenha() {
        return null;
    }

    @Override
    public ResenhaDto mostrarResenha() {
        return null;
    }

    @Override
    public ResenhaDto ocultarResenhar() {
        return null;
    }

    @Override
    public List<ResenhaDto> consultarEstadistica() {
        return List.of();
    }

    @Override
    public List<ResenhaDto> mostrarResenhaUsuario() {
        return List.of();
    }
}
