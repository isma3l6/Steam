package modelo.entidad;

import jakarta.persistence.*;

import java.util.Date;
@Table(name = "juegos")
@Entity
public class JuegoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "desarrollador")
    private String desarrollador;
    @Column(name = "fecha_lanzamiento")
    private Date fechaLanzamiento;
    @Column(name = "precio_base")
    private double precioBase;
    //descuento en porcentaje
    @Column(name = "porcentaje_descuento")
    private double procentajeDescuento;
    //categoria, clasificacion y Estado ponerlo como enum
    @Column(name = "categoria")
    private CategoriaType categoriaType;
    @Column(name = "clasificacion")
    private ClasificacionType clasificacionType;
    @Column(name = "estado")
    private EstadoJuegoType estadoJuegoType;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public JuegoEntidad(Long id, String titulo, String desarrollador, String descripcion, Date fechaLanzamiento, double precioBase, CategoriaType categoriaType, double procentajeDescuento, ClasificacionType clasificacionType, EstadoJuegoType estadoJuegoType) {
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

   /** public JuegoEntidad(String titulo, String descripcion, String desarrollador, Date fechaLanzamiento, CategoriaType categoriaType, ClasificacionType clasificacionType, EstadoJuegoType estadoJuegoType) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.categoriaType = categoriaType;
        this.clasificacionType = clasificacionType;
        this.estadoJuegoType = estadoJuegoType;
    }*/

    @Override
    public String toString() {
        return
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", desarrollador='" + desarrollador + '\'' +
                ", fechaLanzamiento=" + fechaLanzamiento +
                ", precioBase=" + precioBase +
                ", procentajeDescuento=" + procentajeDescuento +
                ", categoriaType=" + categoriaType +
                ", clasificacionType=" + clasificacionType +
                ", estadoJuegoType=" + estadoJuegoType
                ;
    }
}