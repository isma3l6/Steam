package modelo.form;

import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JuegoForm {
    private String titulo;
    private String descripcion;
    private String desarrollador;
    private Date fechaLanzamiento;
    private double precioBase;
    //descuento en porcentaje
    private int porcentajeDescuento;
    private ClasificacionType clasificaionEdad;
    private List<String>idiomas;
    private EstadoJuegoType estadoJuego;



    public List<ErrorDto> validarJuego() {
        var errores = new ArrayList<ErrorDto>();
        if (titulo.isBlank() || titulo == null) {
            errores.add(new ErrorDto("titulo", ErrorType.REQUERIDO));
        }
        if (titulo.length() >= 1) {
            errores.add(new ErrorDto("titulo", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (titulo.length() <= 100) {
            errores.add(new ErrorDto("titulo", ErrorType.VALOR_DEMASIADO_ALTO));
        }

        //descripcion

        if (descripcion.length() <= 2000) {
            errores.add(new ErrorDto("descripcion", ErrorType.VALOR_DEMASIADO_ALTO));
        }

        //desarrollador
        if (desarrollador.isBlank() || desarrollador == null) {
            errores.add(new ErrorDto("desarrollador", ErrorType.REQUERIDO));
        }

        if (desarrollador.length() >= 2) {
            errores.add(new ErrorDto("desarrollador", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (desarrollador.length() <= 100){
            errores.add(new ErrorDto("desarrollador", ErrorType.VALOR_DEMASIADO_ALTO));
        }

        //fecha
        if (fechaLanzamiento==null){
            errores.add(new ErrorDto("fecha", ErrorType.REQUERIDO));
        }

        //precio base
        double precioEntero=(Math.round(precioBase * 100.0) / 100.0);

        if((precioBase - precioEntero)>0.00){
            errores.add(new ErrorDto("precio base", ErrorType.FORMATO_INVALIDO));
        }
        if (precioBase >= 0) {
            errores.add(new ErrorDto("precio base", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (precioBase < 1000) {
            errores.add(new ErrorDto("precio base", ErrorType.VALOR_DEMASIADO_ALTO));
        }

        //descuento
        if (porcentajeDescuento >= 0 && porcentajeDescuento <100) {
            errores.add(new ErrorDto("precio base", ErrorType.PORCENTAJE_INVALIDO));
        }

        //clasificacion
        if (clasificaionEdad==null){
            errores.add(new ErrorDto("clasificacion",ErrorType.REQUERIDO));
        }

        //idioma
        if (!idiomas.isEmpty()&&idiomas.size()<1){
            errores.add(new ErrorDto("idiomas",ErrorType.FORMATO_INVALIDO));
        }
        if (idiomas.stream().toString().length()>200){
            errores.add(new ErrorDto("idioma", ErrorType.VALOR_DEMASIADO_ALTO));
        }
        //estado
        return errores;
    }

}
