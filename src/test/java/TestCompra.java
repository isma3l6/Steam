

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import controlador.CompraControlador;
import controlador.JuegoControlador;
import controlador.UsuarioControlador;
import excepciones.ValidationException;
import modelo.dto.JuegoDto;
import modelo.dto.UsuarioDto;
import modelo.entidad.*;
import modelo.form.CompraForm;
import modelo.form.JuegoForm;
import modelo.form.UsuarioForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.CompraRepoInMemory;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;


public class TestCompra {
 private UsuarioRepoInMemory ur=new UsuarioRepoInMemory();
    private UsuarioControlador usuarioController=new UsuarioControlador(ur);
    private JuegoRepoInMemory jr=new JuegoRepoInMemory();
    private JuegoControlador juegoController=new JuegoControlador(jr);
    private CompraRepoInMemory cr=new CompraRepoInMemory();
    private CompraControlador compraController=new CompraControlador(cr,ur,jr);

    UsuarioEntidad usuarioValido=ur.crear(new UsuarioForm("nuevo",
            "mail",
            "Pass12345",
            "nom",
            "apel",
            "pais",
            LocalDate.of(2026, 04, 24),
            "avtydrr",
            1000)).get();


      JuegoEntidad juegoValido = jr.crear(  new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
              "MembrilloGames", new Date(12 / 4 / 9), 15.75, 0,
              ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE)).get();

    public TestCompra() throws ValidationException {
    }


    // =====================================================
    // Realizar compra
    // =====================================================

    @Test
    public void realizarCompra_FormularioValido_RetornaCompraDTO() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.PAYPAL,
                29.99,
                0));

        assertNotNull(compra);
        assertEquals(usuarioValido.getNombreUsuario(), compra.getUsuario().getNombreUsuario());
        assertEquals(juegoValido.getTitulo(), compra.getJuego().getTitulo());
    }

    @Test
    public void realizarCompra_EstadoPorDefectoPendiente() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.TARJETA_CREDITO,
                29.99,
                0));

        assertEquals(EstadoCompraType.PENDIENTE, compra.getEstadoCompraType());
    }

    @Test
    public void realizarCompra_FechaCompraGeneradaAutomaticamente() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.TRANSFERENCIA,
                29.99,
                0
                ));

        assertNotNull(compra.getFechaCompra());
        assertEquals(LocalDate.now(), compra.getFechaCompra());
    }

    // ── Usuario ────────────────────────────────────────────────────────────

    @Test
    public void realizarCompra_UsuarioInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.realizarCompra(new CompraForm(
                        9999L, // usuario no existe
                        juegoValido.getId(),
                        MetodoPagoType.PAYPAL,
                        29.99,
                        0)));
    }

    // ── Juego ──────────────────────────────────────────────────────────────

    @Test
    public void realizarCompra_JuegoInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.realizarCompra(new CompraForm(
                        usuarioValido.getId(),
                        9999L, // juego no existe
                        MetodoPagoType.PAYPAL,
                        29.99,
                        0)));
    }

    @Test
    public void realizarCompra_JuegoNoDisponible_LanzaValidationException() throws ValidationException {
        // Cambiar estado del juego a NO_DISPONIBLE
        juegoController.cambiarEstado(juegoValido.getId(), EstadoJuegoType.NO_DISPONIBLE);

        assertThrows(ValidationException.class,
                () -> compraController.realizarCompra(new CompraForm(
                        usuarioValido.getId(),
                        juegoValido.getId(), // juego en estado NO_DISPONIBLE
                        MetodoPagoType.PAYPAL,
                        29.99,
                        0
                        )));
    }

    @Test
    public void realizarCompra_JuegoEnPreventa_Permitido() throws ValidationException {
        var juegoEnPreventa = jr.crear( new JuegoForm("Pepe el cazdor", "El cazador se llama Pepe",
                "MembrilloGames", new Date(12 / 4 / 9), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.PREVENTA)).get();


        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoEnPreventa.getId(),
                MetodoPagoType.PAYPAL,
                19.99,
                0));

        assertNotNull(compra);
    }

    @Test
    public void realizarCompra_JuegoEnAccesoAnticipado_Permitido() throws ValidationException {
        var juegoAccesoAnticipado = jr.crear(   new JuegoForm("Pepe el cador", "El cazador se llama Pepe",
                "MembrilloGames", new Date(12 / 4 / 9), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.PREVENTA)).get();


        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoAccesoAnticipado.getId(),
                MetodoPagoType.CARTERA_STEAM,
                0.0,
                0));

        assertNotNull(compra);
    }

    // ── Método de pago ─────────────────────────────────────────────────────//

    @Test
    public void realizarCompra_MetodoPagoNulo_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.realizarCompra(new CompraForm(
                        usuarioValido.getId(),
                        juegoValido.getId(),
                        null, // método de pago obligatorio
                        29.99,
                        0)));
    }

    // ── Precio sin descuento ───────────────────────────────────────────────

    @Test
    public void realizarCompra_PrecioSinDescuentoCero_permitido() {
        assertDoesNotThrow(() -> compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.PAYPAL,
                0.0, // precio cero permitido
                0
                )));
    }

    @Test
    public void realizarCompra_PrecioSinDescuentoNegativo_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.realizarCompra(new CompraForm(
                        usuarioValido.getId(),
                        juegoValido.getId(),
                        MetodoPagoType.PAYPAL,
                        -1.0, // precio negativo no permitido
                        0)));
    }

    // ── Descuento aplicado ─────────────────────────────────────────────────

    @Test
    public void realizarCompra_DescuentoCero_PorDefecto() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.OTROS,
                29.99,
                0));

        assertEquals(0.0, compra.getDescuento(), 0.001);
    }

    @Test
    public void realizarCompra_DescuentoNegativo_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.realizarCompra(new CompraForm(
                        usuarioValido.getId(),
                        juegoValido.getId(),
                        MetodoPagoType.PAYPAL,
                        29.99,
                        -1)));
    }

    @Test
    public void realizarCompra_DescuentoMayor100_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.realizarCompra(new CompraForm(
                        usuarioValido.getId(),
                        juegoValido.getId(),
                        MetodoPagoType.PAYPAL,
                        29.99,
                        101 // supera el máximo de 100
                        )));
    }

    // =====================================================
    // Procesar pago
    // =====================================================

    @Test
    public void procesarPago_CompraEnEstadoPendiente_RetornaCompraCompletada() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.TARJETA_CREDITO,
                29.99,
                0));

        var procesada = compraController.procesarPago(compra.getIdJuego(),compra.getMetodoPagoType());

        assertNotNull(procesada);
        assertEquals(EstadoCompraType.COMPLETADA, procesada.getEstadoCompraType());
    }

    @Test
    public void procesarPago_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.procesarPago(9999L,MetodoPagoType.PAYPAL)); // ID que no existe
    }

    @Test
    public void procesarPago_CompraYaCompletada_LanzaValidationException() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.PAYPAL,
                29.99,
                0));

        compraController.procesarPago(compra.getId(),compra.getMetodoPagoType()); // primera vez: PENDIENTE → COMPLETADA

        // Segunda vez no debe ser posible, ya no está en PENDIENTE
        assertThrows(ValidationException.class,
                () -> compraController.procesarPago(compra.getId(),compra.getMetodoPagoType()));
    }

    // =====================================================
    // Consultar compra
    // =====================================================

    @Test
    public void consultarCompra_IdValidoUsuarioCorrecto_RetornaCompraDTO() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.TRANSFERENCIA,
                29.99,
                0
                ));

        var detalle = compraController.consultarCompra(compra.getId(), usuarioValido.getId());

        assertNotNull(detalle);
        assertEquals(compra.getId(), detalle.getId());
    }

    @Test
    public void consultarCompra_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.consultarCompra(9999L, usuarioValido.getId())); // compra no existe
    }

    @Test
    public void consultarCompra_UsuarioNoEsDuenio_LanzaValidationException() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.PAYPAL,
                29.99,
                0
                ));

        UsuarioDto otroUsuario = usuarioController.registrar(new UsuarioForm(
                "usuario2",
                "usuario2@gmail.com",
                "12345678Aa@",
                "Usuario",
                " Dos",
                "España",
                LocalDate.of(2000,12, 10),
                null,
                0));

        // La compra pertenece a usuarioValido, no a otroUsuario
        assertThrows(ValidationException.class,
                () -> compraController.consultarCompra(compra.getId(), otroUsuario.getId()));
    }

    // =====================================================
    // Solicitar reembolso
    // =====================================================

    @Test
    public void solicitarReembolso_CompraCompletada_RetornaCompraReembolsada() throws ValidationException {
        usuarioController.anadirSaldo(usuarioValido.getId(), 29.99);
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.CARTERA_STEAM,
                29.99,
                0
                ));
        compraController.procesarPago(compra.getId(),compra.getMetodoPagoType()); // PENDIENTE → COMPLETADA

        var reembolsada = compraController.solicitarReembolso(compra.getId(),"alfo",usuarioValido.getId());

        assertNotNull(reembolsada);
        assertEquals(EstadoCompraType.REEMBOLDSADA, reembolsada.getEstadoCompraType());
        assertEquals(29.99, usuarioController.consultarSaldo(usuarioValido.getId())); // saldo reembolsado
    }

    @Test
    public void solicitarReembolso_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> compraController.solicitarReembolso(9999L,"",usuarioValido.getId())); // ID que no existe
    }

    @Test
    public void solicitarReembolso_CompraPendiente_LanzaValidationException() throws ValidationException {
        var compra = compraController.realizarCompra(new CompraForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                MetodoPagoType.PAYPAL,
                29.99,
                0));

        // Solo se puede reembolsar si está COMPLETADA, no PENDIENTE
        assertThrows(ValidationException.class,
                () -> compraController.solicitarReembolso(compra.getId(),"",usuarioValido.getId()));
    }

}
