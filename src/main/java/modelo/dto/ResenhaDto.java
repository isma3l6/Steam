package modelo.dto;

import modelo.entidad.EstadoResenhaType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;

import java.util.Date;

public class ResenhaDto {
    private long id;
    private long idUsuario;
    private UsuarioEntidad usuaro;
    private JuegoEntidad nombreJuego;
    private boolean recomendado;
    private String texto;
    private Date fechaPublicacion;
    private Date fechaEdit;
    //Estado enum
    private EstadoResenhaType estadoResenhaType;


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

    public UsuarioEntidad getUsuaro() {
        return usuaro;
    }

    public void setUsuaro(UsuarioEntidad usuaro) {
        this.usuaro = usuaro;
    }

    public JuegoEntidad getNombreJuego() {
        return nombreJuego;
    }

    public void setNombreJuego(JuegoEntidad nombreJuego) {
        this.nombreJuego = nombreJuego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public ResenhaDto(UsuarioEntidad usuaro, JuegoEntidad nombreJuego, String texto, boolean recomendado, Date fechaPublicacion,Date fechaEdit) {
        this.id=id;
        this.usuaro = usuaro;
        this.nombreJuego = nombreJuego;
        this.texto = texto;
        this.recomendado = recomendado;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaEdit=fechaEdit;
        this.estadoResenhaType=EstadoResenhaType.PUBLICADA;
    }
}
