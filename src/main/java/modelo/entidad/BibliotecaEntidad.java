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
    private boolean noInstalado;
    private InstalacionType instalacionType;

}
