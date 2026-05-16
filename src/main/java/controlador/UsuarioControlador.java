package controlador;

import excepciones.ValidationException;
import mapper.UsuarioMapper;
import modelo.dto.UsuarioDto;
import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import modelo.form.UsuarioForm;
import org.hibernate.query.sqm.PathElementException;

import repositorio.interfaz.IUsuarioRepo;
import transaction.ITransactionManager;

import java.util.*;


public class UsuarioControlador {

    private final IUsuarioRepo repo;

    public ITransactionManager transactionManager;

    public UsuarioControlador(IUsuarioRepo repo, ITransactionManager transactionManager) {
        this.repo = repo;
        this.transactionManager = transactionManager;
    }


    //REGISTRAR NUEVO USUARIO

    public UsuarioDto registrar(UsuarioForm form) throws ValidationException {

        var errores = form.validarUsuario();

        if (!errores.isEmpty()) {

            throw new ValidationException(errores);
        }

        var registrar = transactionManager.inTransaction(() -> {

            if (repo.obtenerTodos().stream().anyMatch(j -> j.getNombreUsuario().equals(form.getNombreUsuario()))) {
                errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));
                throw new ValidationException(errores);
            }
            if (repo.obtenerTodos().stream().anyMatch(j -> j.getEmail().equals(form.getEmail()))) {
                errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));
                throw new ValidationException(errores);
            }

            UsuarioEntidad usuario = repo.crear(form).get();
            return UsuarioMapper.toDTO(usuario);
        });


        return registrar;
    }


    //CONSULTAR PERFIL//
    public UsuarioDto consultarPerfilPorId(Long id) throws ValidationException {

        try {

            var consultarPerfil = transactionManager.inTransaction(() -> {
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
                if (!errores.isEmpty()) {
                    throw new ValidationException(errores);
                }


                return UsuarioMapper.toDTO(usuario);
            });
            return consultarPerfil;
        } catch (NullPointerException e) {
            throw new ValidationException(List.of(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO)));
        }

    }


    public UsuarioDto consultarPerfilPorNombre(String nombreUsuario) throws ValidationException {

            var consultarNombre = transactionManager.inTransaction(() -> {
                UsuarioEntidad usuario = null;
                List<ErrorDto> errores = new ArrayList<>();


                if (nombreUsuario != null) {
                    usuario = repo.buscarUsuarioPorNombre(nombreUsuario).orElse(null);

                }

                if (usuario == null) {
                    errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
                    throw new ValidationException(errores);
                }

                if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
                    errores.add(new ErrorDto("usuario", ErrorType.CUENTA_BLOQUEADA));
                    throw new ValidationException(errores);
                }
                return UsuarioMapper.toDTO(usuario);
            });

            return consultarNombre;


    }

    //AÑADIR SALDO A CARTERA

    public UsuarioDto anadirSaldo(Long usuarioId, double cantidad) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        try{
        var saldoanadir = transactionManager.inTransaction(() -> {
            UsuarioEntidad usuario = repo.obtenerPorId(usuarioId).orElse(null);


            if (usuario == null) {
                errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
                throw new ValidationException(errores);
            }

            if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
                errores.add(new ErrorDto("id", ErrorType.CUENTA_BLOQUEADA));

            }

            if (cantidad <= 0) {
                errores.add(new ErrorDto("saldo", ErrorType.VALOR_DEMASIADO_BAJO));
                throw new ValidationException(errores);
            }
            double saldo = (usuario.getSaldo() + cantidad);

            var actualizado = repo.actualizar(usuario.getId(),
                    new UsuarioForm(usuario.getNombreUsuario(), usuario.getEmail(),
                            usuario.getContrasena(), usuario.getNombre(), usuario.getApellido(),
                            usuario.getPais(), usuario.getFechaNacimiento(), usuario.getAvatar(), saldo)).get();


            return UsuarioMapper.toDTO(actualizado);
        });
        return saldoanadir;}
        catch (NullPointerException e){
            throw new ValidationException(errores);
        }
    }


    //CONSULTAR SALDO

    public UsuarioDto consultarSaldo(Long usuarioId) throws ValidationException {
        var saldoConsultar = transactionManager.inTransaction(() -> {
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
        });
        return saldoConsultar;
    }
}