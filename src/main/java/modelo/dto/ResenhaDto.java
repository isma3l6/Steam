package modelo.dto;

import modelo.entidad.EstadoResenhaType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;

import java.util.Date;

public class ResenhaDto {
    private long id;
    private long idUsuario;
    private UsuarioDto usuaro;
    private JuegoDto nombreJuego;
    private boolean recomendado;
    private String texto;
    private Date fechaPublicacion;
    private Date fechaEdit;
    //Estado enum
    private int horasJugadas;
    private EstadoResenhaType estadoResenhaType;

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

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsuarioDto getUsuaro() {
        return usuaro;
    }

    public void setUsuaro(UsuarioDto usuaro) {
        this.usuaro = usuaro;
    }

    public JuegoDto getNombreJuego() {
        return nombreJuego;
    }

    public void setNombreJuego(JuegoDto nombreJuego) {
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

    public ResenhaDto(UsuarioDto usuaro, JuegoDto nombreJuego, String texto, boolean recomendado, Date fechaPublicacion,Date fechaEdit,int horasJugadas) {
        this.id=id;
        this.usuaro = usuaro;
        this.nombreJuego = nombreJuego;
        this.texto = texto;
        this.recomendado = recomendado;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaEdit=fechaEdit;
        this.estadoResenhaType=EstadoResenhaType.PUBLICADA;
        this.horasJugadas=horasJugadas;
    }
}
