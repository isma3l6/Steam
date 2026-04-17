package modelo.dto;

import modelo.entidad.EstadoCompraType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.MetodoPagoType;
import modelo.entidad.UsuarioEntidad;

import java.util.Date;

public class CompraDto {
    private long id;
    private long idUsuario;
    private UsuarioDto Usuario;
    private long idJuego;
    private JuegoDto Juego;
    private Date fechaCompra;
    //Metodo de pago enum
private MetodoPagoType metodoPagoType;
    private double precio;
    //descuento es un porcentaje
    private int descuento;
    private double descuentoAplicado;
    private EstadoCompraType estadoCompraType;

    public UsuarioDto getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        Usuario = usuario;
    }

    public JuegoDto getJuego() {
        return Juego;
    }

    public void setJuego(JuegoDto juego) {
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

    public CompraDto(UsuarioDto usuario, JuegoDto juego, double precio, int descuento, double descuentoAplicado, MetodoPagoType metodoPagoType) {

        this.id=id;
        Usuario = usuario;
        Juego = juego;
        this.precio = precio;
        this.descuento = descuento;
        this.descuentoAplicado = descuentoAplicado;
        this.metodoPagoType=metodoPagoType;
    }
}
