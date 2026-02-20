package repositorio.inmemory;

import modelo.entidad.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompraRepoInMemory {


    private List<CompraEntidad> compras = new ArrayList<>();
    private Long contadorId = 1L;

    // CREATE
    public CompraEntidad realizarCompra(UsuarioEntidad usuario,
                                        JuegoEntidad juego,
                                        MetodoPagoType metodoPago,
                                        BigDecimal precio,
                                        double descuento) {

        CompraEntidad nueva = new CompraEntidad(
                contadorId++,
                usuario.getId(),
                juego.getId(),
                metodoPago,
                precio,
                descuento,
                EstadoCompraType.COMPLETADA
        );

        compras.add(nueva);
        return nueva;
    }

    // READ BY ID
    public CompraEntidad buscarPorId(Long id) {
        return compras.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // READ ALL
    public List<CompraEntidad> listarTodas() {
        return compras;
    }

    // READ por usuario
    public List<CompraEntidad> listarPorUsuario(UsuarioEntidad usuario) {
        return compras.stream()
                .filter(c -> c.getUsuario().getId().equals(usuario.getId()))
                .collect(Collectors.toList());
    }

    // UPDATE estado (ej: reembolso)
    public void cambiarEstado(Long id, EstadoCompraType estado) {
        CompraEntidad compra = buscarPorId(id);
        if (compra != null) {
            compra.setEstadoCompraType(estado);
        } else {
            throw new RuntimeException("Compra no encontrada");
        }
    }

    // DELETE
    public void eliminar(Long id) {
        compras.removeIf(c -> c.getId().equals(id));
    }
}
