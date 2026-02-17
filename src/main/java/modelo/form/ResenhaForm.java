package modelo.form;

import java.util.ArrayList;
import java.util.List;

public class ResenhaForm {
    private long idUsuario;
    private long idJuego;
    private boolean recomendado;
    private String cuerpoResena;

    public List<ErrorDto> validarResena() {
        var errores = new ArrayList<ErrorDto>();
        if (idUsario == 0) {
            errores.add(new ErrorDto("Ususario", ErrorType.REQUERIDO));

        }
        if (buscarUsuario().isEmpty) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        //juego en la biblioteca del user
        //Juego
        if (idJuego == 0) {
            errores.add(new ErrorDto("Juego", ErrorType.REQUERIDO));
        }
        if (buscarJuego().isEmpty) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
        }
        // 1 resena por juego
        if (cuerpoResena.length() < 50){
            errores.add(new ErrorDto("reseña",ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (cuerpoResena.length() > 8000 ){
            errores.add(new ErrorDto("reseña",ErrorType.VALOR_DEMASIADO_ALTO));
        }
        //Horas jugadas
        //Estado
        return errores;

    }
}
