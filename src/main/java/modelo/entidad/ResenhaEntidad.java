package modelo.entidad;

import java.util.Date;

public class ResenhaEntidad {
    private int id;
    private String usuaro;
    private String nombreJuego;
    private boolean recomendado;
    private String texto;
    private Date fechaPublicacion;
    private Date fechaEdit;
    private EstadoResenhaType estadoResenhaType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuaro() {
        return usuaro;
    }

    public void setUsuaro(String usuaro) {
        this.usuaro = usuaro;
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public void setNombreJuego(String nombreJuego) {
        this.nombreJuego = nombreJuego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaEdit() {
        return fechaEdit;
    }

    public void setFechaEdit(Date fechaEdit) {
        this.fechaEdit = fechaEdit;
    }

    public EstadoResenhaType getEstadoResenhaType() {
        return estadoResenhaType;
    }

    public void setEstadoResenhaType(EstadoResenhaType estadoResenhaType) {
        this.estadoResenhaType = estadoResenhaType;
    }

    public ResenhaEntidad(int id, String usuaro, String nombreJuego, boolean recomendado, String texto, Date fechaPublicacion, Date fechaEdit, EstadoResenhaType estadoResenhaType) {
        this.id = id;
        this.usuaro = usuaro;
        this.nombreJuego = nombreJuego;
        this.recomendado = recomendado;
        this.texto = texto;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaEdit = fechaEdit;
        this.estadoResenhaType = estadoResenhaType;
    }
}
