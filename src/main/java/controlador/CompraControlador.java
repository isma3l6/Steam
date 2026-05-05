package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import excepciones.ValidationException;
import mapper.CompraMapper;
import mapper.JuegoMapper;
import mapper.UsuarioMapper;
import modelo.dto.CompraDto;
import modelo.entidad.*;
import modelo.form.CompraForm;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import modelo.form.UsuarioForm;
import repositorio.interfaz.ICompraRepo;
import repositorio.interfaz.IJuegoRepo;
import repositorio.interfaz.IUsuarioRepo;

public class CompraControlador {

    private final ICompraRepo compraRepo;
    private final IJuegoRepo juegoRepo;
    private final IUsuarioRepo usuarioRepo;

    public CompraControlador(ICompraRepo compraRepo, IUsuarioRepo usuarioRepo,IJuegoRepo juegoRepo) {
        this.compraRepo = compraRepo;
        this.usuarioRepo=usuarioRepo;
        this.juegoRepo=juegoRepo;
    }


    //REALIZAR COMPRA

    public CompraDto realizarCompra(CompraForm form) throws ValidationException {

        var errores = form.validarCompra();

        var usuario=usuarioRepo.obtenerPorId(form.getIdUsuario()).orElse(null);


        if (usuario==null) {
            throw new ValidationException( List.of( new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO)));

        }
        var juego=juegoRepo.obtenerPorId(form.getIdJuego()).orElse(null);
        // Validaciones básicas
        if (juego == null) {

            throw new ValidationException( List.of( new ErrorDto("Juego", ErrorType.NO_ENCONTRADO)));
        }
        if (juego.getEstadoJuegoType()==EstadoJuegoType.NO_DISPONIBLE){
            throw new ValidationException( List.of( new ErrorDto("Juego", ErrorType.INVALIDO)));

        }


        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            errores.add(new ErrorDto("usuario", ErrorType.CUENTA_BLOQUEADA));
        }



        // Validar duplicado (usuario ya compró el juego)
        List<CompraEntidad> comprasUsuario = compraRepo.obtenerTodos().stream()
                .filter(c -> c.getIdUsuario() == usuario.getId() && c.getIdJuego() == juego.getId())
                .toList();




        // Crear compra

       var compraOpt = compraRepo.crear(form).orElse(null);

        if (!errores.isEmpty()){
            throw new ValidationException(errores);
        }

        if (compraOpt!=null) {
            return CompraMapper.toDTO(compraOpt, UsuarioMapper.toDTO(usuario), JuegoMapper.toDTO(juego));
        } else {
            errores.add(new ErrorDto("compra", ErrorType.ERROR_EN_BASE));
            throw new ValidationException(errores);
        }
    }


    //PROCESAR PAGO

    public CompraDto procesarPago(Long idCompra, MetodoPagoType metodoPago) throws ValidationException {
        var compraOpt = compraRepo.obtenerPorId(idCompra).orElse(null);

        List<ErrorDto> errores = new ArrayList<>();

        if (compraOpt==null) {
            errores.add(new ErrorDto("compra", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        var u=UsuarioMapper.toDTO(usuarioRepo.obtenerPorId(compraOpt.getIdUsuario()).get());

        var j=JuegoMapper.toDTO(juegoRepo.obtenerPorId(compraOpt.getIdJuego()).get());




        if (compraOpt.getEstadoCompraType() != EstadoCompraType.PENDIENTE) {
            errores.add(new ErrorDto("compra", ErrorType.FORMATO_INVALIDO));
            throw new ValidationException(errores);
        }

        // Simular validación de pago
        if (metodoPago == null) {
            errores.add(new ErrorDto("Metodo de pago", ErrorType.FORMATO_INVALIDO));
            throw new ValidationException(errores);
        }

        // Procesar
        compraOpt.setEstadoCompraType(EstadoCompraType.COMPLETADA);
        compraOpt.setMetodoPagoType(metodoPago);
        if (errores.isEmpty()){
            return CompraMapper.toDTO(compraOpt,u,j);

        }
        else {
            throw new ValidationException(errores);
        }
    }


    //CONSULTAR DETALLES DE COMPRA

    public CompraDto consultarCompra(Long idCompra, Long idUsuario) throws ValidationException {
        List<ErrorDto>errores=new ArrayList<>();

        var compraOpt = compraRepo.obtenerPorId(idCompra).orElse(null);

        if (compraOpt==null) {
            errores.add(new ErrorDto("compra", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);        }

        var u=UsuarioMapper.toDTO(usuarioRepo.obtenerPorId(compraOpt.getIdUsuario()).get());

        var j=JuegoMapper.toDTO(juegoRepo.obtenerPorId(compraOpt.getIdJuego()).get());


        if (compraOpt.getIdUsuario() != (idUsuario)) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores); // Usuario no propietario
        }

        return CompraMapper.toDTO(compraOpt,u,j);
    }


    //SOLICITAR REEMBOLSO

    public CompraDto solicitarReembolso(Long idCompra, String motivo,Long usuarioId) throws ValidationException{

        List<ErrorDto>errores=new ArrayList<>();

        var compraOpt = compraRepo.obtenerPorId(idCompra).orElse(null);

        if (compraOpt==null) {
            errores.add(new ErrorDto("compra", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        var u=UsuarioMapper.toDTO(usuarioRepo.obtenerPorId(compraOpt.getIdUsuario()).orElse(null));
        if (u==null) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        var j=JuegoMapper.toDTO(juegoRepo.obtenerPorId(compraOpt.getIdJuego()).orElse(null));
        if (compraOpt==null) {
            errores.add(new ErrorDto("Juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }





        CompraEntidad compra = compraOpt;

        // Validaciones
        if (compra.getIdUsuario() != (usuarioId)) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        if (compra.getEstadoCompraType() != EstadoCompraType.COMPLETADA) {
            errores.add(new ErrorDto("usuario", ErrorType.FORMATO_INVALIDO));
            throw new ValidationException(errores);
        }

        // Simulación de plazo / horas jugadas (omitido)
        // Reembolsar
        compra.setEstadoCompraType(EstadoCompraType.REEMBOLDSADA);
        // Actualizar saldo del usuario
        var usuario=usuarioRepo.obtenerPorId(usuarioId).get();
        var usuarioActualizado=new UsuarioForm(usuario.getNombreUsuario(),
                usuario.getEmail(),
                usuario.getContrasena(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getPais(),
                usuario.getFechaNacimiento(),
                usuario.getAvatar(),
                usuario.getSaldo()+((compraOpt.getPrecio()* compra.getDescuento())/100)
                );

usuarioRepo.actualizar(usuarioId,usuarioActualizado);
        return CompraMapper.toDTO(compra,u,j);
    }
}