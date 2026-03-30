package modelo.entidad;

import java.util.Date;

public class ResenhaEntidad {
    private long id;
    private long usuaroId;
    private long nombreJuegoId;
    private boolean recomendado;
    private String texto;
    private Date fechaPublicacion;
    private Date fechaEdit;
    private EstadoResenhaType estadoResenhaType;
    private int horasJugadas;

    public int getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(int horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsuaroId() {
        return usuaroId;
    }

    public void setUsuaroId(Long usuaroId) {
        this.usuaroId = usuaroId;
    }

    public long getNombreJuegoId() {
        return nombreJuegoId;
    }

    public void setNombreJuegoId(Long nombreJuegoId) {
        this.nombreJuegoId = nombreJuegoId;
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

    public ResenhaEntidad(long id, long usuaroId, long nombreJuegoId, boolean recomendado, String texto, Date fechaPublicacion, Date fechaEdit, EstadoResenhaType estadoResenhaType) {
        this.id = id;
        this.usuaroId = usuaroId;
        this.nombreJuegoId = nombreJuegoId;
        this.recomendado = recomendado;
        this.texto = texto;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaEdit = fechaEdit;
        this.estadoResenhaType = estadoResenhaType;
    }
}
