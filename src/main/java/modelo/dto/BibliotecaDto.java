package modelo.dto;

import modelo.entidad.InstalacionType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;

import java.util.Date;

public class BibliotecaDto {
    private long id;
    private long idUsario;
    private UsuarioDto Usuario;
    private long idJuego;
    private JuegoDto Juego;
    private Date fechaAdquisicion;
    private int horasJugadas;
    private Date jugadoPorUltimavez;
    private InstalacionType instalacionType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUsario() {
        return idUsario;
    }

    public void setIdUsario(long idUsario) {
        this.idUsario = idUsario;
    }

    public UsuarioDto getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        Usuario = usuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public JuegoDto getJuego() {
        return Juego;
    }

    public void setJuego(JuegoDto juego) {
        Juego = juego;
    }

    public Date getFechaAdquisicion() {return fechaAdquisicion;}

    public void setFechaAdquisicion(Date fechaAdquisicion) {this.fechaAdquisicion = fechaAdquisicion;}

    public int getHorasJugadas() {return horasJugadas;}

    public void setHorasJugadas(int horasJugadas) {this.horasJugadas = horasJugadas;}

    public Date getJugadoPorUltimavez() {return jugadoPorUltimavez;}

    public void setJugadoPorUltimavez(Date jugadoPorUltimavez) {
        this.jugadoPorUltimavez = jugadoPorUltimavez;
    }

    public InstalacionType getInstalacionType() {
        return instalacionType;
    }

    public void setInstalacionType(InstalacionType instalacionType) {
        this.instalacionType = instalacionType;
    }

    public BibliotecaDto( UsuarioDto usuario, JuegoDto juego, Date fechaAdquisicion, int horasJugadas, Date jugadoPorUltimavez, InstalacionType instalacionType) {
        Usuario = usuario;
        Juego = juego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.horasJugadas = horasJugadas;
        this.jugadoPorUltimavez = jugadoPorUltimavez;
        this.instalacionType = instalacionType;
    }
}
