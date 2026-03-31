package modelo.dto;

import modelo.entidad.CategoriaType;
import modelo.entidad.ClasificacionType;

import java.util.Date;

public class JuegoDto {
    private long id;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public double getProcentajeDescuento() {
        return procentajeDescuento;
    }

    public CategoriaType getCategoria() {
        return categoria;
    }

    public long getId() {
        return id;
    }

    private String desarrollador;
    private Date fechaLanzamiento;
    private double precioBase;
    //descuento en porcentaje
    private double procentajeDescuento;
    //categoria, clasificacion y Estado ponerlo como enum
    private CategoriaType categoria;

    public JuegoDto(String titulo, String desarrollador, Date fechaLanzamiento, double precioBase, double procentajeDescuento, CategoriaType categoria) {
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.procentajeDescuento = procentajeDescuento;
        this.categoria = categoria;
    }

    public JuegoDto(String titulo, String desarrollador, Date fechaLanzamiento, CategoriaType categoria, double precioBase) {
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.categoria = categoria;
        this.precioBase = precioBase;
    }
}