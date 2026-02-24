package repositorio.inmemory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import modelo.entidad.CompraEntidad;
import modelo.form.CompraForm;
import modelo.entidad.MetodoPagoType;
import modelo.entidad.EstadoCompraType;
import repositorio.interfaz.ICompraRepo;

public class CompraRepoInMemory implements ICompraRepo {
    private static final List<CompraEntidad> compras = new ArrayList<>();
    private static Long idCounter = 1L;


      // CREAR COMPRA

    public Optional<CompraEntidad> crear(CompraForm form) {
        var compra = new CompraEntidad(
                new Date(),                          // fechaCompra
                idCounter++,                          // id auto-incremental
                form.getIdUsuario(),                  // idUsuario
                form.getIdJuego(),                    // idJuego
                MetodoPagoType.valueOf(form.getMetodoPago().toUpperCase()), // metodoPago
                form.getPrecioFinal(),                // precio
                EstadoCompraType.PENDIENTE,           // estado inicial
                form.getDescuento()                   // descuento
        );
        compras.add(compra);
        return Optional.of(compra);
    }


      // OBTENER POR ID

    public Optional<CompraEntidad> obtenerPorId(Long id) {
        return compras.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

       //OBTENER TODAS LAS COMPRAS

    public List<CompraEntidad> obtenerTodos() {
        return new ArrayList<>(compras);
    }


       //ACTUALIZAR COMPRA

    public Optional<CompraEntidad> actualizar(Long id, CompraForm form) {
        var compraOpt = obtenerPorId(id);
        if (compraOpt.isEmpty()) {
            throw new IllegalArgumentException("Compra no encontrada");
        }

        var compraActualizada = new CompraEntidad(
                compraOpt.get().getFechaCompra(),          // mantenemos la fecha original
                id,
                form.getIdUsuario(),
                form.getIdJuego(),
                MetodoPagoType.valueOf(form.getMetodoPago().toUpperCase()),
                form.getPrecioFinal(),
                compraOpt.get().getEstadoCompraType(),    // mantenemos el estado actual
                form.getDescuento()
        );

        compras.removeIf(c -> c.getId() == id);
        compras.add(compraActualizada);
        return Optional.of(compraActualizada);
    }


      // ELIMINAR COMPRA

    public boolean eliminar(Long id) {
        return compras.removeIf(c -> c.getId() == id);
    }
}