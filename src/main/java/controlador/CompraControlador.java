package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import excepciones.ValidationException;
import mapper.CompraMapper;
import modelo.dto.CompraDto;
import modelo.entidad.*;
import modelo.form.CompraForm;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import repositorio.interfaz.ICompraRepo;

public class CompraControlador {

    private final ICompraRepo compraRepo;

    public CompraControlador(ICompraRepo compraRepo) {
        this.compraRepo = compraRepo;
    }


    //REALIZAR COMPRA

    public CompraDto realizarCompra(CompraForm form, UsuarioEntidad usuario, JuegoEntidad juego) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        // Validaciones básicas
        if (usuario == null) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        if (usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            errores.add(new ErrorDto("usuario", ErrorType.CUENTA_BLOQUEADA));
            throw new ValidationException(errores);

        }
        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        // Validar duplicado (usuario ya compró el juego)
        List<CompraEntidad> comprasUsuario = compraRepo.obtenerTodos().stream()
                .filter(c -> c.getIdUsuario() == usuario.getId() && c.getIdJuego() == juego.getId())
                .toList();
        if (!comprasUsuario.isEmpty()) {
            errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));
            throw new ValidationException(errores);
        }

        // Crear compra
        Optional<CompraEntidad> compraOpt = compraRepo.crear(form);
        if (compraOpt.isPresent()) {
            return CompraMapper.toDTO(compraOpt.get());
        } else {
            errores.add(new ErrorDto("compra", ErrorType.ERROR_EN_BASE));
            throw new ValidationException(errores);
        }
    }


    //PROCESAR PAGO

    public CompraDto procesarPago(Long idCompra, MetodoPagoType metodoPago) throws ValidationException {
        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra);
        List<ErrorDto> errores = new ArrayList<>();
        if (compraOpt.isEmpty()) {
            errores.add(new ErrorDto("compra", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        CompraEntidad compra = compraOpt.get();
        if (compra.getEstadoCompraType() != EstadoCompraType.PENDIENTE) {
            errores.add(new ErrorDto("compra", ErrorType.FORMATO_INVALIDO));
            throw new ValidationException(errores);
        }

        // Simular validación de pago
        if (metodoPago == null) {
            errores.add(new ErrorDto("Metodo de pago", ErrorType.FORMATO_INVALIDO));
            throw new ValidationException(errores);
        }

        // Procesar
        compra.setEstadoCompraType(EstadoCompraType.COMPLETADA);
        compra.setMetodoPagoType(metodoPago);
        return CompraMapper.toDTO(compra);
    }


    //CONSULTAR DETALLES DE COMPRA

    public CompraDto consultarCompra(Long idCompra, Long idUsuario) throws ValidationException {
        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra);
        List<ErrorDto>errores=new ArrayList<>();
        if (compraOpt.isEmpty()) {
            errores.add(new ErrorDto("compra", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);        }

        CompraEntidad compra = compraOpt.get();
        if (compra.getIdUsuario() != (idUsuario)) {
            errores.add(new ErrorDto("usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores); // Usuario no propietario
        }
        return CompraMapper.toDTO(compra);
    }


    //SOLICITAR REEMBOLSO

    public CompraDto solicitarReembolso(Long idCompra, String motivo, UsuarioEntidad usuario) throws ValidationException{
        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra);
        List<ErrorDto>errores=new ArrayList<>();
        if (compraOpt.isEmpty()) {
            errores.add(new ErrorDto("compra", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        CompraEntidad compra = compraOpt.get();

        // Validaciones
        if (compra.getIdUsuario() != (usuario.getId())) {
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
        usuario.setSaldo(usuario.getSaldo() + compra.getPrecio());

        return CompraMapper.toDTO(compra);
    }
}