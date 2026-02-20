package controlador;

import modelo.dto.CompraDto;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.MetodoPagoType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.CompraForm;
import repositorio.inmemory.CompraRepoInMemory;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;
import repositorio.interfaz.ICompra;

import java.util.List;

public class CompraControlador  {

    private CompraRepoInMemory compraRepo;
    private UsuarioRepoInMemory usuarioRepo;
    private JuegoRepoInMemory juegoRepo;

    public CompraControlador(CompraRepoInMemory compraRepo,
                            UsuarioRepoInMemory usuarioRepo,
                            JuegoRepoInMemory juegoRepo) {
        this.compraRepo = compraRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
    }

    // 🔹 Realizar compra
    public String realizarCompra(Long usuarioId, Long juegoId,
                                 MetodoPagoType metodoPago) {

        UsuarioEntidad usuario = usuarioRepo.buscarPorId(usuarioId);
        JuegoEntidad juego = juegoRepo.buscarPorId(juegoId);

        if (usuario==null || juego==null)
            return "Usuario o juego no encontrado";

        double precioFinal = juego.getPrecioBase() *
                (1 - juego.getProcentajeDescuento() / 100);

        if (metodoPago == MetodoPagoType.CARTERA_STEAM) {
            if (usuario.getSaldo() < precioFinal)
                return "Saldo insuficiente";

            usuario.setSaldo(
                    usuario.getSaldo() - precioFinal);
        }

        CompraForm compra = new CompraForm(null, usuario,
                juego.getMetodoPagoType,
                juego.getPrecioBase(),
                juego.getProcentajeDescuento());

        compraRepo.realizarCompra(usuario, juego,metodoPago,precioFinal, juego.getProcentajeDescuento());

        return "Compra realizada con ID: " + compra.getId();
    }
}
