package modelo.entidad;

import modelo.dto.JuegoDto;
import modelo.dto.UsuarioDto;

import java.util.Date;

public class BibliotecaEntidad {
    private long id;
    private long idUsuario;
    private long idJuego;
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

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public int getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(int horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public Date getJugadoPorUltimavez() {
        return jugadoPorUltimavez;
    }

    public void setJugadoPorUltimavez(Date jugadoPorUltimavez) {
        this.jugadoPorUltimavez = jugadoPorUltimavez;
    }




    public InstalacionType getInstalacionType() {
        return instalacionType;
    }

    public void setInstalacionType(InstalacionType instalacionType) {
        this.instalacionType = instalacionType;
    }

    public BibliotecaEntidad(long id, long idUsuario, long idJuego, Date fechaAdquisicion, int horasJugadas, Date jugadoPorUltimavez, InstalacionType instalacionType) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.horasJugadas = horasJugadas;
        this.jugadoPorUltimavez = jugadoPorUltimavez;
        this.instalacionType = instalacionType;

    }
}
