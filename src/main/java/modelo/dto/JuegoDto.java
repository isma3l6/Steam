package modelo.dto;

import modelo.entidad.CategoriaType;
import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;

import java.util.Date;

public class JuegoDto {
    private long id;
    private String titulo;

    private String desarrollador;
    private Date fechaLanzamiento;
    private double precioBase;
    //descuento en porcentaje
    private double procentajeDescuento;
    //categoria, clasificacion y Estado ponerlo como enum
    private ClasificacionType categoria;

}
