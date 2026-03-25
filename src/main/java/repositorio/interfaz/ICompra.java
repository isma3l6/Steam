package repositorio.interfaz;

import modelo.dto.CompraDto;

import java.util.List;

public interface ICompra {

    CompraDto comprar();

    CompraDto procesarPago();

    List <CompraDto> consultarHistorial();

    CompraDto consultarDetallesCompra();

    CompraDto devolverJuego();

    CompraDto generarFactura();


}
