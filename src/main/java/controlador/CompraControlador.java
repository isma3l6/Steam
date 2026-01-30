package controlador;

import modelo.dto.CompraDto;
import repositorio.interfaz.ICompra;

import java.util.List;

public class CompraControlador implements ICompra {
    @Override
    public CompraDto comprar() {
        return null;
    }

    @Override
    public CompraDto procesarPago() {
        return null;
    }

    @Override
    public List<CompraDto> consultarHistorial() {
        return List.of();
    }

    @Override
    public CompraDto consultarDetallesCompra() {
        return null;
    }

    @Override
    public CompraDto devolverJuego() {
        return null;
    }

    @Override
    public CompraDto generarFactura() {
        return null;
    }
}
