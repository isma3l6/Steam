package modelo.dto;

import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;

import java.util.Date;

public class BibliotecaDto {
    private int id;
    private UsuarioEntidad idUsuario;
    private JuegoEntidad idJuego;
    private InstalacionType instalacionType;
}
