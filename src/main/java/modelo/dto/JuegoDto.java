package modelo.dto;

import modelo.entidad.CategoriaType;
import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;

import java.time.LocalDate;
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

    public LocalDate getFechaLanzamiento() {
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
    public EstadoJuegoType getEstado() {return estadoJuego;}

    private EstadoJuegoType estadoJuego;

    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private double precioBase;
    //descuento en porcentaje
    private double procentajeDescuento;
    //categoria, clasificacion y Estado ponerlo como enum
    private CategoriaType categoria;

    public JuegoDto(Long id, String titulo, String desarrollador, LocalDate fechaLanzamiento, double precioBase, double procentajeDescuento, CategoriaType categoria, EstadoJuegoType estadoJuego) {
       this.id=id;
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.procentajeDescuento = procentajeDescuento;
        this.categoria = categoria;
        this.estadoJuego = estadoJuego;
    }

    public JuegoDto(String titulo, String desarrollador, LocalDate fechaLanzamiento, CategoriaType categoria, double precioBase, EstadoJuegoType estadoJuego) {
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.estadoJuego = estadoJuego;
    }
}