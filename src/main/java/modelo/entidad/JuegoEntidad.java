package modelo.entidad;

import java.util.Date;

public class JuegoEntidad {
    private int id;
    private String titulo;
    private String descripcion;
    private String desarrollador;
    private Date fechaLanzamiento;
    private double precioBase;
    //descuento en porcentaje
    private double procentajeDescuento;
    //categoria, clasificacion y Estado ponerlo como enum
    private CategoriaType categoriaType;

    private ClasificacionType clasificacionType;

    private EstadoJuegoType estadoJuegoType;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public double getProcentajeDescuento() {
        return procentajeDescuento;
    }

    public void setProcentajeDescuento(double procentajeDescuento) {
        this.procentajeDescuento = procentajeDescuento;
    }

    public CategoriaType getCategoriaType() {
        return categoriaType;
    }

    public void setCategoriaType(CategoriaType categoriaType) {
        this.categoriaType = categoriaType;
    }

    public ClasificacionType getClasificacionType() {
        return clasificacionType;
    }

    public void setClasificacionType(ClasificacionType clasificacionType) {
        this.clasificacionType = clasificacionType;
    }

    public EstadoJuegoType getEstadoJuegoType() {
        return estadoJuegoType;
    }

    public void setEstadoJuegoType(EstadoJuegoType estadoJuegoType) {
        this.estadoJuegoType = estadoJuegoType;
    }

    public JuegoEntidad(int id, String titulo, String desarrollador, String descripcion, Date fechaLanzamiento, double precioBase, CategoriaType categoriaType, double procentajeDescuento, ClasificacionType clasificacionType, EstadoJuegoType estadoJuegoType) {
        this.id = id;
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.descripcion = descripcion;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.categoriaType = categoriaType;
        this.procentajeDescuento = procentajeDescuento;
        this.clasificacionType = clasificacionType;
        this.estadoJuegoType = estadoJuegoType;
    }
}
