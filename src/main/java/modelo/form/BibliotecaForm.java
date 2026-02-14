package modelo.form;

import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BibliotecaForm {
    private long id;
    private long idUsario;
    private long idJuego;
    private Date fechaAdquisicion;

    public List<ErrorDto> validarBiblioteca() {
        var errores = new ArrayList<ErrorDto>();
        if (idUsario == 0){
            errores.add(new ErrorDto("Ususario", ErrorType.REQUERIDO));

        }
        if (buscarUsuario() == "Lista vacia") {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        //Juego
        if (idJuego == 0){
            errores.add(new ErrorDto("Juego", ErrorType.REQUERIDO));
        }
        if (buscarJuego() == "Lista bacia"){
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        if (juegoUnico()== true){
            errores.add(new ErrorDto("Juego", ErrorType.DUPLICADO));
        }
        return errores;
    }

    private boolean juegoUnico() {
        // no se si hacerlo general, porque que el juego este dos veces en Steam no me tiene sentido
        //
        return true;
    }

}
