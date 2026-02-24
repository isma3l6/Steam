package modelo.form;

import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.util.ArrayList;
import java.util.List;

public class CompraForm {
    private long idUsuario;
    private long idJuego;
    //fecha compra
    private String metodoPago;
    private double precio;
    private int descuento;
    private double precioFinal;
    private UsuarioRepoInMemory usuarioRepoInMemory;
    private JuegoRepoInMemory juegoRepoInMemory;

    public List<ErrorDto> validarCompra() {
        var errores = new ArrayList<ErrorDto>();

        if (idUsuario == 0) {
            errores.add(new ErrorDto("Ususario", ErrorType.REQUERIDO));
        }
        if (usuarioRepoInMemory.obtenerPorId(idUsuario) == null) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        //Activa
        if (idJuego == 0) {
            errores.add(new ErrorDto("Juego", ErrorType.REQUERIDO));
        }
        if (juegoRepoInMemory.obtenerPorId(idJuego) == null) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        //Estado juego
        if (metodoPago.isBlank()) {
            errores.add(new ErrorDto("Metodo pago", ErrorType.REQUERIDO));
        }
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

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public String getMetodoPago() {
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

