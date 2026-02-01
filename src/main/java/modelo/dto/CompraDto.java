package modelo.dto;

import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;

import java.util.Date;

public class CompraDto {
    private int id;
    private UsuarioEntidad idUsuario;
    private JuegoEntidad idJuego;
    private Date fechaCompra;
    //Metodo de pago enum
    private double precio;
    //descuento es un porcentaje
    private double descuentoAplicado;
    private boolean estado;
}
