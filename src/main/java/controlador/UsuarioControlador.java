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

    public UsuarioDto registrar(UsuarioForm form) throws ValidationException {

        var errores = form.validarUsuario();

        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }

        UsuarioEntidad usuario = repo.crear(form);

        return UsuarioMapper.toDTO(usuario);
    }

    //CONSULTAR PERFIL//
    public UsuarioDto consultarPerfilPorId(Long id) throws ValidationException {

        UsuarioEntidad usuario = null;
        List<ErrorDto> errores = new ArrayList<>();

        if (id != null) {
            usuario = repo.obtenerPorId(id);
        }


        if (usuario == null) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            errores.add(new ErrorDto("usuario", ErrorType.CUENTA_BLOQUEADA));
            throw new ValidationException(errores);
        }


        return UsuarioMapper.toDTO(usuario);
    }


    public UsuarioDto consultarPerfilPorNombre(String nombreUsuario) throws ValidationException {
        UsuarioEntidad usuario = null;
        List<ErrorDto> errores = new ArrayList<>();


        if (nombreUsuario != null) {
             usuario= Arrays.stream(repo.obtenerTodos()).filter(u-> Objects.equals(u.getNombreUsuario(), nombreUsuario)).findFirst().get();
            
        }

        if (usuario == null) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            errores.add(new ErrorDto("usuario", ErrorType.CUENTA_BLOQUEADA));
            throw new ValidationException(errores);
        }


        return UsuarioMapper.toDTO(usuario);
    }

    //AÑADIR SALDO A CARTERA
    public UsuarioDto añadirSaldo(Long usuarioId, double cantidad) throws ValidationException {

        UsuarioEntidad usuario = repo.obtenerPorId(usuarioId);
        List<ErrorDto> errores = new ArrayList<>();

        if (usuario == null) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            errores.add(new ErrorDto("id", ErrorType.CUENTA_BLOQUEADA));
            throw new ValidationException(errores);
        }

        if (cantidad <= 0) {
            errores.add(new ErrorDto("saldo", ErrorType.VALOR_DEMASIADO_BAJO));
            throw new ValidationException(errores);
        }

        var actualizado = repo.actualizar(usuario.getId(), new UsuarioForm(usuario.getNombreUsuario(), usuario.getEmail(),
                usuario.getContraseña(), usuario.getNombre(), usuario.getApellido(), usuario.getPais(), usuario.getFechaNacimiento(), usuario.getAvatar(), usuario.getSaldo()));
        return UsuarioMapper.toDTO(actualizado);
    }


    //CONSULTAR SALDO

    public UsuarioDto consultarSaldo(Long usuarioId) throws ValidationException {

        UsuarioEntidad usuario = repo.obtenerPorId(usuarioId);
        List<ErrorDto> errores = new ArrayList<>();

        if (usuario == null) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        return UsuarioMapper.toDTO(usuario);
    }
}