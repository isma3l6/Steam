package modelo.dto;

import java.util.Date;

public class JuegoDto {
    private int id;
    private String titulo;
    private String descripcion;
    private String desarrollador;
    private Date fechaLanzamiento;
    private double precioBase;
    //descuento en porcentaje
    private double procentajeDescuento;
    //categoria, clasificacion y Estado ponerlo como enum

}
