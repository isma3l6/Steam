package modelo.form;

import modelo.entidad.MetodoPagoType;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.util.ArrayList;
import java.util.List;

public class CompraForm {
    private long idUsuario;
    private long idJuego;
    //fecha compra
    private MetodoPagoType metodoPago;
    private double precio;
    private int descuento;
    private double precioFinal;


    public List<ErrorDto> validarCompra() {
        var errores = new ArrayList<ErrorDto>();

        if (idUsuario == 0) {
            errores.add(new ErrorDto("Ususario", ErrorType.REQUERIDO));
        }

        //Activa
        if (idJuego == 0) {
            errores.add(new ErrorDto("Juego", ErrorType.REQUERIDO));
        }

        //Estado juego
       /** if (metodoPago.isBlank()) {
            errores.add(new ErrorDto("Metodo pago", ErrorType.REQUERIDO));
        }*/
        if (precio < 0.00) {
            errores.add(new ErrorDto("precio", ErrorType.REQUERIDO));
        }
        if (descuento < 0) {
            errores.add(new ErrorDto("precio", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (descuento > 100) {
            errores.add(new ErrorDto("precio", ErrorType.VALOR_DEMASIADO_ALTO));
        }
        //Estado pendiente
        return errores;
    }

    public CompraForm(long idUsuario, long idJuego, MetodoPagoType metodoPago, double precio, int descuento) {
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.metodoPago = metodoPago;
        this.precio = precio;
        this.descuento = descuento;
        this.precioFinal = (precioFinal*descuento)/100;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public MetodoPagoType getMetodoPago() {
        return metodoPago;
    }

    public double getPrecio() {
        return precio;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public int getDescuento() {
        return descuento;
    }
}

