package modelo.entidad;

import modelo.dto.JuegoDto;
import modelo.dto.UsuarioDto;

import java.util.Date;

public class CompraEntidad {
    private long id;
    private long idUsuario;
    private long idJuego;
    private Date fechaCompra;
    //Metodo de pago enum
    private double precio;
    //descuento es un porcentaje
    private double descuentoAplicado;
    private boolean estado;
}
