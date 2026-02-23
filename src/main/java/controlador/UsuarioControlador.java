package controlador;

import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;
import repositorio.interfaz.IUsuarioRepo;

import java.text.DecimalFormat;
import java.util.*;

public class UsuarioControlador {

    private final IUsuarioRepo repo;

    public UsuarioControlador(IUsuarioRepo repo) {
        this.repo = repo;
    }


       //REGISTRAR NUEVO USUARIO

    public Object registrar(UsuarioForm form) {

        var errores = form.validarUsuario();

        if (!errores.isEmpty()) {
            return errores;
        }

        UsuarioEntidad usuario = repo.crear(form);

        return "Usuario creado exitosamente con ID: " + usuario.getId();
    }

    //CONSULTAR PERFIL//
    public Object consultarPerfil(Long id, String nombreUsuario) {

        UsuarioEntidad usuario = null;

        if (id != null) {
            usuario = repo.obtenerPorId(id);
        } else if (nombreUsuario != null) {
            for (UsuarioEntidad u : repo.obtenerTodos()) {
                if (u != null && u.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                    usuario = u;
                    break;
                }
            }
        }

        if (usuario == null) {
            return "Usuario no encontrado";
        }

        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            return "Acceso denegado. Cuenta no activa.";
        }

        Map<String, Object> perfil = new HashMap<>();
        perfil.put("Nombre usuario", usuario.getNombreUsuario());
        perfil.put("Avatar", usuario.getAvatar());
        perfil.put("Fecha registro", usuario.getFechaRegistro());
        perfil.put("Saldo", usuario.getSaldo());
        perfil.put("Estado", usuario.getEstadoType());

        // Biblioteca y estadísticas podrían venir de BibliotecaService
        perfil.put("Biblioteca", "No implementada en este controlador");
        perfil.put("Estadísticas", "No implementadas");

        return perfil;
    }


      //AÑADIR SALDO A CARTERA
    public String añadirSaldo(Long usuarioId, double cantidad) {

        UsuarioEntidad usuario = repo.obtenerPorId(usuarioId);

        if (usuario == null) {
            return "Usuario no encontrado";
        }

        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            return "La cuenta no está activa";
        }

        if (cantidad <= 0) {
            return "La cantidad debe ser mayor que 0";
        }

        if (cantidad < 5.00 || cantidad > 500.00) {
            return "La cantidad debe estar entre 5.00 y 500.00";
        }

        double nuevoSaldo = usuario.getSaldo() + cantidad;
        usuario.setSaldo(nuevoSaldo);

        DecimalFormat df = new DecimalFormat("#0.00");

        return "Nuevo saldo: " + df.format(nuevoSaldo) + " €";
    }


       //CONSULTAR SALDO

    public String consultarSaldo(Long usuarioId) {

        UsuarioEntidad usuario = repo.obtenerPorId(usuarioId);

        if (usuario == null) {
            return "Usuario no existe en el sistema";
        }

        DecimalFormat df = new DecimalFormat("#0.00");

        return df.format(usuario.getSaldo()) + " €";
    }
}