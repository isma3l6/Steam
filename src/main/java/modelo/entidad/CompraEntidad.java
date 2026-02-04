package modelo.entidad;

import modelo.dto.JuegoDto;
import modelo.dto.UsuarioDto;

import java.util.Date;

public class CompraEntidad {
     static final int MIN=0;
     static  final int MAX=100;

    private long id;
    private long idUsuario;
    private long idJuego;
    private Date fechaCompra;
    //Metodo de pago enum
    private MetodoPagoType metodoPagoType;
    private double precio;
    //descuento es un porcentaje
    @MIN(value=0)
    @MAX()
    private int descuento;
    private EstadoCompraType estadoCompraType;


}
