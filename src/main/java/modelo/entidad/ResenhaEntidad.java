package modelo.entidad;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "resena")
@Entity
public class ResenhaEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "usuario_id")
    private long usuaroId;
    @Column(name = "nombre_juego_id")
    private long nombreJuegoId;
    @Column(name = "recomendado")
    private boolean recomendado;
    @Column(name = "texto")
    private String texto;
    @Column(name = "fecha_publicacion")
    private Date fechaPublicacion;
    @Column(name = "fecha_edit")
    private Date fechaEdit;
    @Column(name = "estado_resenha_type")
    private EstadoResenhaType estadoResenhaType;
    @Column(name = "horas_jugadas")
    private int horasJugadas;

    public int getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(int horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsuaroId() {
        return usuaroId;
    }

    public void setUsuaroId(Long usuaroId) {
        this.usuaroId = usuaroId;
    }

    public long getNombreJuegoId() {
        return nombreJuegoId;
    }

    public void setNombreJuegoId(Long nombreJuegoId) {
        this.nombreJuegoId = nombreJuegoId;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaEdit() {
        return fechaEdit;
    }

    public void setFechaEdit(Date fechaEdit) {
        this.fechaEdit = fechaEdit;
    }

    public EstadoResenhaType getEstadoResenhaType() {
        return estadoResenhaType;
    }

    public void setEstadoResenhaType(EstadoResenhaType estadoResenhaType) {
        this.estadoResenhaType = estadoResenhaType;
    }

    public ResenhaEntidad(long id, long usuaroId, long nombreJuegoId, boolean recomendado, String texto, Date fechaPublicacion, Date fechaEdit, EstadoResenhaType estadoResenhaType) {
        this.id = id;
        this.usuaroId = usuaroId;
        this.nombreJuegoId = nombreJuegoId;
        this.recomendado = recomendado;
        this.texto = texto;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaEdit = fechaEdit;
        this.estadoResenhaType = estadoResenhaType;
    }
}
