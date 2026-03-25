package modelo.entidad;

import java.util.Date;

public class UsuarioEntidad {
    private long id;
    private String nombreUsuario;
    private String email;
    private String contraseña;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private Date fechaRegistro;
    private String avatar;
    private double saldo;
    //Estado de cuanta
    private EstadoUserType estadoType;


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public EstadoUserType getEstadoType() {
        return estadoType;
    }

    public void setEstadoType(EstadoUserType estadoType) {
        this.estadoType = estadoType;
    }

    public UsuarioEntidad(long id, String nombreUsuario, String email, String contraseña, String nombre, String apellido, Date fechaNacimiento, Date fechaRegistro, String avatar, double saldo, EstadoUserType estadoType) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.saldo = saldo;
        this.estadoType = estadoType;
    }
}
