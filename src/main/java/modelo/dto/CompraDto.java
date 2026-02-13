package modelo.dto;

import modelo.entidad.EstadoCompraType;
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
private MetodoPagoType metodoPagoType;
    private double precio;
    //descuento es un porcentaje
    private int descuento;
    private double descuentoAplicado;
    private EstadoCompraType estadoCompraType;

    public UsuarioEntidad getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioEntidad usuario) {
        Usuario = usuario;
    }

    public JuegoEntidad getJuego() {
        return Juego;
    }

    public void setJuego(JuegoEntidad juego) {
        Juego = juego;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public MetodoPagoType getMetodoPagoType() {
        return metodoPagoType;
    }

    public void setMetodoPagoType(MetodoPagoType metodoPagoType) {
        this.metodoPagoType = metodoPagoType;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public EstadoCompraType getEstadoCompraType() {
        return estadoCompraType;
    }

    public void setEstadoCompraType(EstadoCompraType estadoCompraType) {
        this.estadoCompraType = estadoCompraType;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CompraDto(UsuarioEntidad usuario, JuegoEntidad juego, double precio, int descuento, double descuentoAplicado, MetodoPagoType metodoPagoType) {

        this.id=id;
        Usuario = usuario;
        Juego = juego;
        this.precio = precio;
        this.descuento = descuento;
        this.descuentoAplicado = descuentoAplicado;
        this.metodoPagoType=metodoPagoType;
    }
}
