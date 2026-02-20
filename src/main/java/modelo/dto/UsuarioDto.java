package modelo.dto;

import modelo.entidad.EstadoUserType;

import java.util.Date;

public class UsuarioDto {
    private long id;
    private String nombreUsuario;
    private String email;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    //Estado de cuanta
    private EstadoUserType estadoUserType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EstadoUserType getEstadoUserType() {
        return estadoUserType;
    }

    public void setEstadoUserType(EstadoUserType estadoUserType) {
        this.estadoUserType = estadoUserType;
    }

    public UsuarioDto(String nombreUsuario, String email, String nombre, String apellido, Date fechaNacimiento) {
        this.id=id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoUserType=EstadoUserType.ACTIVA;
    }
}
