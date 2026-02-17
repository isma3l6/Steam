package modelo.form;

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

    public List<ErrorDto> validarCompra() {
        var errores = new ArrayList<ErrorDto>();

        if (idUsuario == 0) {
            errores.add(new ErrorDto("Ususario", ErrorType.REQUERIDO));
        }
        if (buscarUsuario.isEmpty) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        //Activa
        if (idJuego == 0) {
            errores.add(new ErrorDto("Juego", ErrorType.REQUERIDO));
        }
        if (buscarJuego().isEmpty) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        //Estado juego
        if (metodoPago.isBlank()) {
            errores.add(new ErrorDto("Metodo pago", ErrorType.REQUERIDO));
        }
        if (precio < 0.00) {
            errores.add(new ErrorDto("precio",ErrorType.REQUERIDO));
        }
        if (descuento<0){
            errores.add(new ErrorDto("precio",ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (descuento>100){
            errores.add(new ErrorDto("precio",ErrorType.VALOR_DEMASIADO_ALTO));
        }
        //Estado pendiente
        return errores;
    }
}

