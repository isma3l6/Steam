package modelo.entidad;

import jakarta.persistence.*;
import modelo.dto.JuegoDto;
import modelo.dto.UsuarioDto;

import java.util.Date;

@Table(name = "compra")
@Entity

public class CompraEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_usuario")
    private long idUsuario;
    @Column(name = "id_juego")
    private long idJuego;
    @Column(name = "fecha_compra")
    private Date fechaCompra;
    //Metodo de pago enum
    @Column(name = "matodo_pago_type")
    private MetodoPagoType metodoPagoType;
    @Column(name = "precio")
    private double precio;
    //descuento es un porcentaje
    @Column(name = "descuento")
    private int descuento;
    @Column(name = "estado_compra_type")
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
