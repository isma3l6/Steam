package controlador;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import modelo.entidad.*;
import modelo.form.CompraForm;
import repositorio.interfaz.ICompraRepo;

public class CompraControlador {

    private final ICompraRepo compraRepo;

    public CompraControlador(ICompraRepo compraRepo) {
        this.compraRepo = compraRepo;
    }


      //REALIZAR COMPRA

    public String realizarCompra(CompraForm form, UsuarioEntidad usuario, JuegoEntidad juego) {
        // Validaciones básicas
        if (usuario == null || usuario.getEstadoType() != EstadoUserType.ACTIVA) {
            return "Usuario inactivo o no encontrado";
        }
        if (juego == null) {
            return "Juego no encontrado";
        }
        // Validar duplicado (usuario ya compró el juego)
        List<CompraEntidad> comprasUsuario = compraRepo.obtenerTodos().stream()
                .filter(c -> c.getIdUsuario() == usuario.getId() && c.getIdJuego() == juego.getId())
                .toList();
        if (!comprasUsuario.isEmpty()) {
            return "El usuario ya posee este juego";
        }

        // Crear compra
        Optional<CompraEntidad> compraOpt = compraRepo.crear(form);
        if (compraOpt.isPresent()) {
            return "Compra creada con ID: " + compraOpt.get().getId();
        } else {
            return "Error al crear la compra";
        }
    }


       //PROCESAR PAGO

    public String procesarPago(Long idCompra, MetodoPagoType metodoPago) {
        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra);
        if (compraOpt.isEmpty()) {
            return "Compra no encontrada";
        }
        CompraEntidad compra = compraOpt.get();
        if (compra.getEstadoCompraType() != EstadoCompraType.PENDIENTE) {
            return "Compra no válida para procesar";
        }

        // Simular validación de pago
        if (metodoPago == null) {
            return "Método de pago inválido";
        }

        // Procesar
        compra.setEstadoCompraType(EstadoCompraType.COMPLETADA);
        compra.setMetodoPagoType(metodoPago);
        return "Pago procesado correctamente para la compra ID: " + compra.getId();
    }


       //CONSULTAR DETALLES DE COMPRA

    public Optional<CompraEntidad> consultarCompra(Long idCompra, Long idUsuario) {
        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra);
        if (compraOpt.isEmpty()) {
            return Optional.empty();
        }
        CompraEntidad compra = compraOpt.get();
        if (compra.getIdUsuario()!=(idUsuario)) {
            return Optional.empty(); // Usuario no propietario
        }
        return Optional.of(compra);
    }


       //SOLICITAR REEMBOLSO

    public String solicitarReembolso(Long idCompra, String motivo, UsuarioEntidad usuario) {
        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra);
        if (compraOpt.isEmpty()) {
            return "Compra no encontrada";
        }

        CompraEntidad compra = compraOpt.get();

        // Validaciones
        if (compra.getIdUsuario()!=(usuario.getId())) {
            return "Compra no pertenece al usuario";
        }
        if (compra.getEstadoCompraType() != EstadoCompraType.COMPLETADA) {
            return "Solo se pueden reembolsar compras completadas";
        }

        // Simulación de plazo / horas jugadas (omitido)
        // Reembolsar
        compra.setEstadoCompraType(EstadoCompraType.REEMBOLDSADA);
        // Actualizar saldo del usuario
        usuario.setSaldo(usuario.getSaldo() + compra.getPrecio());

        return "Reembolso realizado con éxito. Nuevo saldo: " + usuario.getSaldo();
    }
}