package modelo.form;

import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;
import modelo.entidad.JuegoEntidad;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BibliotecaForm {
    private long id;
    private long idUsario;
    private long idJuego;
    private Date fechaAdquisicion;
    private double tiempoJugado;
    private UsuarioRepoInMemory usuarioRepoInMemory;
    private JuegoRepoInMemory juegoRepoInMemory;


    public List<ErrorDto> validarBiblioteca() {
        var errores = new ArrayList<ErrorDto>();
        if (idUsario == 0) {
            errores.add(new ErrorDto("Ususario", ErrorType.REQUERIDO));

        }
        if (usuarioRepoInMemory.obtenerPorId(idUsario)==null) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        //Juego
        if (idJuego == 0) {
            errores.add(new ErrorDto("Juego", ErrorType.REQUERIDO));
        }
        if (juegoRepoInMemory.obtenerPorId(idJuego)==null) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        if (!juegoUnico(idJuego)) {
            errores.add(new ErrorDto("Juego", ErrorType.DUPLICADO));
        }
        //Fecha Adquisicion No puede ser futura, No anterior fecha registro user
        if (tiempoJugado < 0) {
            errores.add(new ErrorDto("Juego", ErrorType.FORMATO_INVALIDO));
        }
        return errores;
    }

    private boolean juegoUnico(long idJuego) {
        // no se si hacerlo general, porque que el juego este dos veces en Steam no me tiene sentido
        var listaJuegos = new ArrayList<Long>();
        for (int i = 0; i < listaJuegos.size(); i++) {
            if (idJuego == i) {
                return false;
            }
        }
        return true;
    }

    public long getIdUsario() {
        return idUsario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public double getTiempoJugado() {
        return tiempoJugado;
    }

    public BibliotecaForm(long id, long idUsario, long idJuego, Date fechaAdquisicion, double tiempoJugado) {
        this.id = id;
        this.idUsario = idUsario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJugado = tiempoJugado;
    }
}
