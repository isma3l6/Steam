package modelo.dto;

import modelo.entidad.JuegoEntidad;
import modelo.entidad.MetodoPagoType;
import modelo.entidad.UsuarioEntidad;

import java.util.Date;

public class CompraDto {
    private long id;
    private long idUsuario;
    private UsuarioEntidad Usuario;
    private long idJuego;
    private JuegoEntidad Juego;
    private Date fechaCompra;
    //Metodo de pago enum

    private double precio;
    //descuento es un porcentaje


    private int descuento;
    private double descuentoAplicado;

}
