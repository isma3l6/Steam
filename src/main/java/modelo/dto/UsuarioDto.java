package modelo.dto;

import modelo.entidad.EstadoUserType;

import java.time.LocalDate;
import java.util.Date;

public class UsuarioDto {
    private long id;
    private String nombreUsuario;
    private String email;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private  LocalDate fechaRegistro;
    private double saldo;
    //Estado de cuanta
    private EstadoUserType estadoUserType;

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EstadoUserType getEstadoUserType() {
        return estadoUserType;
    }

    public void setEstadoUserType(EstadoUserType estadoUserType) {
        this.estadoUserType = estadoUserType;
    }

    public UsuarioDto(String nombreUsuario, String email, String nombre, String apellido, LocalDate fechaNacimiento,double saldo,
                      LocalDate fechaRegistro) {
        this.id=id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.saldo=saldo;
        this.estadoUserType=EstadoUserType.ACTIVA;
        this.fechaRegistro = fechaRegistro;
    }
}
