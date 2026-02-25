package modelo.form;

import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.ResenhaRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.util.ArrayList;
import java.util.List;

public class ResenhaForm {
    private long idUsuario;
    private long idJuego;
    private boolean recomendado;
    private String cuerpoResena;
    private double horasJugadas;
private UsuarioRepoInMemory usuarioRepoInMemory;
private JuegoRepoInMemory juegoRepoInMemory;
    public List<ErrorDto> validarResena() {
        var errores = new ArrayList<ErrorDto>();
        if (idUsuario == 0) {
            errores.add(new ErrorDto("Ususario", ErrorType.REQUERIDO));

        }
        if (usuarioRepoInMemory.obtenerPorId(idUsuario)==null) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        //juego en la biblioteca del user
        //Juego
        if (idJuego == 0) {
            errores.add(new ErrorDto("Juego", ErrorType.REQUERIDO));
        }
        if (juegoRepoInMemory.obtenerPorId(idJuego)==null) {
            errores.add(new ErrorDto("Juego", ErrorType.NO_ENCONTRADO));
        }
        // 1 resena por juego
        if (cuerpoResena.length() < 50) {
            errores.add(new ErrorDto("reseña", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (cuerpoResena.length() > 8000) {
            errores.add(new ErrorDto("reseña", ErrorType.VALOR_DEMASIADO_ALTO));
        }
        //Horas jugadas
        if (horasJugadas < 0) {
            errores.add(new ErrorDto("horas jugadas", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        //Estado
        return errores;

    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public String getCuerpoResena() {
        return cuerpoResena;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public double getHorasJugadas() {
        return horasJugadas;
    }

    public ResenhaForm(long idUsuario, long idJuego, boolean recomendado, String cuerpoResena, double horasJugadas) {
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.recomendado = recomendado;
        this.cuerpoResena = cuerpoResena;
        this.horasJugadas = horasJugadas;
    }
}
