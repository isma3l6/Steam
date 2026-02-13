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
    private MetodoPagoType metodoPagoType;
    private double precio;
    //descuento es un porcentaje
    private int descuento;
    private EstadoCompraType estadoCompraType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public MetodoPagoType getMetodoPagoType() {
        return metodoPagoType;
    }

    public void setMetodoPagoType(MetodoPagoType metodoPagoType) {
        this.metodoPagoType = metodoPagoType;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoCompraType getEstadoCompraType() {
        return estadoCompraType;
    }

    public void setEstadoCompraType(EstadoCompraType estadoCompraType) {
        this.estadoCompraType = estadoCompraType;
    }

    public CompraEntidad(Date fechaCompra, long id, long idUsuario, long idJuego, MetodoPagoType metodoPagoType, double precio, EstadoCompraType estadoCompraType, int descuento) {
        this.fechaCompra = fechaCompra;
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.metodoPagoType = metodoPagoType;
        this.precio = precio;
        this.estadoCompraType = estadoCompraType;
        this.descuento = descuento;
    }
}
