package controlador;

import modelo.dto.UsuarioDto;
import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;
import repositorio.inmemory.UsuarioRepoInMemory;
import repositorio.interfaz.IUsuario;

import java.time.LocalDate;
import java.util.Optional;

public class UsuarioControlador  {
    private UsuarioRepoInMemory usuarioRepo;

    public UsuarioControlador(UsuarioRepoInMemory usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    // 🔹 Registrar usuario
    public String registrarUsuario(String nombreUsuario, String email,
                                   String contrasenha, String nombreReal,
                                   String pais, LocalDate fechaNacimiento) {

        if (nombreUsuario == null || nombreUsuario.isEmpty())
            return "Error: Nombre de usuario obligatorio";

        if (email == null || !email.contains("@"))
            return "Error: Email inválido";

        UsuarioForm usuario = new UsuarioForm( nombreUsuario, email, contrasenha,
                nombreReal, pais, fechaNacimiento, "default.png");

        usuarioRepo.crear(usuario);
        return "Usuario creado con ID: " + usuario.getId();
    }

    // 🔹 Consultar perfil
    public String consultarPerfil(Long id) {
        UsuarioEntidad usuario = usuarioRepo.buscarPorId(id);

        if (usuario==null) {
            return "Usuario no encontrado";
        }


        return "Usuario: " + usuario.getNombreUsuario() +
                "\nPaís: " + usuario.getPais() +
                "\nRegistro: " + usuario.getFechaRegistro() +
                "\nSaldo: " + usuario.getSaldoCartera() + " €";
    }

    // 🔹 Añadir saldo
    public String añadirSaldo(Long id, double cantidad) {

        UsuarioEntidad usuarioOpt = usuarioRepo.buscarPorId(id);
        if (usuarioOpt==null) {
            return "Usuario no encontrado";
        }


        if (usuarioOpt.getEstadoUserType() != EstadoUserType.ACTIVA)
            return "Cuenta no activa";

        if (cantidad < 5.0 || cantidad > 500.0)
            return "Cantidad fuera de rango (5€ - 500€)";

        usuarioOpt.setSaldoCartera(usuarioOpt.getSaldoCartera() + cantidad);

        return "Nuevo saldo: " + usuarioOpt.getSaldoCartera() + " €";
    }

    // 🔹 Consultar saldo
    public String consultarSaldo(Long id) {
        UsuarioEntidad usuario = usuarioRepo.buscarPorId(id);

        if (usuario==null){
            return "Usuario no encontrado";}

        return "Saldo actual: " + usuario.getSaldoCartera() + " €";
    }
}
