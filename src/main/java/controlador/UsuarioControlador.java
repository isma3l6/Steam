package controlador;

import excepciones.ValidationException;
import mapper.UsuarioMapper;
import modelo.dto.UsuarioDto;
import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import modelo.form.UsuarioForm;
import repositorio.inmemory.UsuarioRepoInMemory;
import repositorio.interfaz.IUsuarioRepo;

import java.util.*;


public class UsuarioControlador {

    private UsuarioRepoInMemory repo = new UsuarioRepoInMemory();

    public UsuarioControlador(UsuarioRepoInMemory repo) {
        this.repo = repo;
    }


    //REGISTRAR NUEVO USUARIO

    public UsuarioDto registrar(UsuarioForm form) throws ValidationException {

        var errores = form.validarUsuario();

        verificarDuplicidad(form, errores);

        if (!errores.isEmpty()) {

            throw new ValidationException(errores);
        }
        UsuarioEntidad usuario = repo.crear(form).get();

        return UsuarioMapper.toDTO(usuario);
    }

    private void verificarDuplicidad(UsuarioForm form, List<ErrorDto> errores) {

        if (!repo.buscarUsuarioPorNombre(form.getNombreUsuario()).isEmpty()) {
            errores.add(new ErrorDto("usuario", ErrorType.DUPLICADO));
        }
        if (!repo.buscarUsuarioPorCorreo(form.getEmail()).isEmpty()) {
            errores.add(new ErrorDto("Email duplicado", ErrorType.DUPLICADO));

        }
    }

    //CONSULTAR PERFIL//
    public UsuarioDto consultarPerfilPorId(Long id) throws ValidationException {

        UsuarioEntidad usuario = null;
        List<ErrorDto> errores = new ArrayList<>();

        if (id != null) {
            usuario = repo.obtenerPorId(id).orElse(null);
        }


        if (usuario == null) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            errores.add(new ErrorDto("usuario", ErrorType.CUENTA_BLOQUEADA));
        }
        if (!errores.isEmpty()){
            throw new ValidationException(errores);
        }


        return UsuarioMapper.toDTO(usuario);
    }


    public UsuarioDto consultarPerfilPorNombre(String nombreUsuario) throws ValidationException {
        UsuarioEntidad usuario = null;
        List<ErrorDto> errores = new ArrayList<>();


        if (nombreUsuario != null) {
            usuario = repo.buscarUsuarioPorNombre(nombreUsuario).get();

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

    public UsuarioDto anadirSaldo(Long usuarioId, double cantidad) throws ValidationException {

        UsuarioEntidad usuario = repo.obtenerPorId(usuarioId).get();
        List<ErrorDto> errores = new ArrayList<>();

        if (usuarioId < 0) {
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
        double saldo= (usuario.getSaldo()+ cantidad);
        var actualizado = repo.actualizar(usuario.getId(),
                new UsuarioForm(usuario.getNombreUsuario(), usuario.getEmail(),
                        usuario.getContrasena(), usuario.getNombre(), usuario.getApellido(),
                        usuario.getPais(), usuario.getFechaNacimiento(), usuario.getAvatar(),saldo)).get();


        return UsuarioMapper.toDTO(actualizado);
    }


    //CONSULTAR SALDO

    public UsuarioDto consultarSaldo(Long usuarioId) throws ValidationException {

        UsuarioEntidad usuario = null;
        List<ErrorDto> errores = new ArrayList<>();

        if (usuarioId == null) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));

            throw new ValidationException(errores);
        }
        usuario = repo.obtenerPorId(usuarioId).orElse(null);


        if (usuario == null) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        return UsuarioMapper.toDTO(usuario);
    }
}