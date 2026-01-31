package modelo.dto;

import java.util.Date;

public class CompraDto {
    private int id;
    private UsuarioDto idUsuario;
    private JuegoDto idJuego;
    private Date fechaCompra;
    //Metodo de pago enum
    private double precio;
    //descuento es un porcentaje
    private double descuentoAplicado;
    private boolean estado;
}
