package modelo.dto;

import modelo.entidad.InstalacionType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;

public class BibliotecaDto {
    private long id;
    private long idUsario;
    private UsuarioEntidad Usuario;
    private long idJuego;
    private JuegoEntidad Juego;

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

    public UsuarioEntidad getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioEntidad usuario) {
        Usuario = usuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public JuegoEntidad getJuego() {
        return Juego;
    }

    public void setJuego(JuegoEntidad juego) {
        Juego = juego;
    }

    public BibliotecaDto(UsuarioEntidad usuario, JuegoEntidad juego) {
        this.id=id;
        Usuario = usuario;
        Juego = juego;
        this.instalacionType=instalacionType;
    }
}
