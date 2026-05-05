package modelo.entidad;

import jakarta.persistence.*;
import modelo.dto.JuegoDto;
import modelo.dto.UsuarioDto;

import java.time.LocalDate;
import java.util.Date;
@Table(name = "biblioteca")
@Entity

public class BibliotecaEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_usuario")
    private long idUsuario;
    @Column(name = "id_juego")
    private long idJuego;
    @Column(name = "fecha_adquisicion")
    private LocalDate fechaAdquisicion;
    @Column(name = "horas_jugadas")
    private int horasJugadas;
    @Column(name = "jugado_por_ultima_vez")
    private LocalDate jugadoPorUltimavez;
    @Column(name = "instalacion")
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

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public int getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(int horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public LocalDate getJugadoPorUltimavez() {
        return jugadoPorUltimavez;
    }

    public void setJugadoPorUltimavez(LocalDate jugadoPorUltimavez) {
        this.jugadoPorUltimavez = jugadoPorUltimavez;
    }




    public InstalacionType getInstalacionType() {
        return instalacionType;
    }

    public void setInstalacionType(InstalacionType instalacionType) {
        this.instalacionType = instalacionType;
    }

    public BibliotecaEntidad(long id, long idUsuario, long idJuego, LocalDate fechaAdquisicion, int horasJugadas, LocalDate jugadoPorUltimavez, InstalacionType instalacionType) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.horasJugadas = horasJugadas;
        this.jugadoPorUltimavez = jugadoPorUltimavez;
        this.instalacionType = instalacionType;

    }

    @Override
    public String toString() {
        return "BibliotecaEntidad{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idJuego=" + idJuego +
                ", fechaAdquisicion=" + fechaAdquisicion +
                ", horasJugadas=" + horasJugadas +
                ", jugadoPorUltimavez=" + jugadoPorUltimavez +
                ", instalacionType=" + instalacionType +
                '}';
    }
}
