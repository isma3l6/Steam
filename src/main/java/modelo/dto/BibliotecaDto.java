package modelo.dto;

import java.util.Date;

public class BibliotecaDto {
    private int id;
    private UsuarioDto idUsuario;
    private JuegoDto idJuego;
    private Date fechaAdquisicion;
    private int horasJugadas;
    private Date jugadoPorUltimavez;
    private boolean noInstalado;
}
