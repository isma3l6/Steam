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
    private long idUsario;
    private long idJuego;
    private double tiempoJugado;


    public BibliotecaForm( long idUsario, long idJuego, double tiempoJugado) {
        this.idUsario = idUsario;
        this.idJuego = idJuego;

        this.tiempoJugado = tiempoJugado;

    }

    public List<ErrorDto> validarBiblioteca() {
        var errores = new ArrayList<ErrorDto>();
        if (idUsario == 0) {
            errores.add(new ErrorDto("Ususario", ErrorType.REQUERIDO));

        }

        //Juego
        if (idJuego == 0) {
            errores.add(new ErrorDto("Juego", ErrorType.REQUERIDO));
        }

        //Fecha Adquisicion No puede ser futura, No anterior fecha registro user
        if (tiempoJugado < 0) {
            errores.add(new ErrorDto("Juego", ErrorType.FORMATO_INVALIDO));
        }
        return errores;
    }



    public long getIdUsario() {
        return idUsario;
    }

    public long getIdJuego() {
        return idJuego;
    }



    public double getTiempoJugado() {
        return tiempoJugado;
    }
}
