package modelo.dto;

import modelo.entidad.EstadoResenhaType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;

import java.time.LocalDate;
import java.util.Date;

public class ResenhaDto {
    private long id;
    private long idUsuario;
    private UsuarioDto usuaro;
    private JuegoDto juego;
    private boolean recomendado;
    private String texto;
    private LocalDate fechaPublicacion;
    private LocalDate fechaEdit;
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

    public JuegoDto getJuego() {
        return juego;
    }

    public void setJuego(JuegoDto juego) {
        this.juego = juego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDate getFechaEdit() {
        return fechaEdit;
    }

    public void setFechaEdit(LocalDate fechaEdit) {
        this.fechaEdit = fechaEdit;
    }

    public EstadoResenhaType getEstadoResenhaType() {
        return estadoResenhaType;
    }

    public void setEstadoResenhaType(EstadoResenhaType estadoResenhaType) {
        this.estadoResenhaType = estadoResenhaType;
    }

    public ResenhaDto(UsuarioDto usuaro, JuegoDto juego, String texto, boolean recomendado, LocalDate fechaPublicacion, LocalDate fechaEdit, int horasJugadas) {
        this.usuaro = usuaro;
        this.juego = juego;
        this.texto = texto;
        this.recomendado = recomendado;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaEdit=fechaEdit;
        this.estadoResenhaType=EstadoResenhaType.PUBLICADA;
        this.horasJugadas=horasJugadas;
    }
}
