package controlador;

import excepciones.ValidationException;
import mapper.UsuarioMapper;
import modelo.dto.UsuarioDto;
import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
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

    public UsuarioDto registrar(UsuarioForm form)throws ValidationException {

        var errores = form.validarUsuario();

        if (!errores.isEmpty()) {
             throw new ValidationException(errores);
        }

        UsuarioEntidad usuario = repo.crear(form);

        return UsuarioMapper.toDTO(usuario);
    }

    //CONSULTAR PERFIL//
    public UsuarioDto consultarPerfil(Long id, String nombreUsuario) throws ValidationException {

        UsuarioEntidad usuario = null;
        List <ErrorDto> errores=new ArrayList<>();

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
            errores.add(new ErrorDto("id",ErrorType.NO_USUARIO));
            throw new ValidationException(errores);
        }

        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            errores.add(new ErrorDto("usuario",ErrorType.CUENTA_BLOQUEADA));
           throw  new ValidationException(errores);
        }



        return UsuarioMapper.toDTO(usuario);
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