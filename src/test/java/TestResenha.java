import controlador.*;
import excepciones.ValidationException;
import modelo.dto.UsuarioDto;
import modelo.entidad.*;
import modelo.form.BibliotecaForm;
import modelo.form.JuegoForm;
import modelo.form.ResenhaForm;
import modelo.form.UsuarioForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.*;
import transaction.ITransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestResenha {

    private static final String TEXTO_VALIDO = "Este juego es increíble, una obra maestra del género que todo aficionado debería jugar.";

    private UsuarioRepoInMemory ur=new UsuarioRepoInMemory();
    private UsuarioControlador usuarioController=new UsuarioControlador(ur);
    private JuegoRepoInMemory jr=new JuegoRepoInMemory();
    public ITransactionManager transactionManager;

    private JuegoControlador juegoController=new JuegoControlador(jr, transactionManager);
    private BibliotecaRepoInMemory br=new BibliotecaRepoInMemory();
    private BibliotecaControlador bibliotecaControlador=new BibliotecaControlador(br,jr,ur);
    private ResenhaRepoInMemory rr=new ResenhaRepoInMemory();
    private ResenhaControlador resenaController=new ResenhaControlador(rr,br,ur,jr);

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
            "MembrilloGames", LocalDate.of(2015 , 4 , 12), 15.75, 0,
            ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION)).get();

    BibliotecaEntidad bibliotecaValida=br.crear(new BibliotecaForm(usuarioValido.getId(),juegoValido.getId(),0)).get();
    // =====================================================
    // Crear reseña
    // =====================================================



 @Test
    public void crearResena_FormularioValido_RetornaResenaDTO() throws ValidationException {
        var resena = resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0));

        assertNotNull(resena);
        assertEquals(usuarioValido.getNombreUsuario(), resena.getUsuaro().getNombreUsuario());
        assertEquals(juegoValido.getTitulo(), resena.getJuego().getTitulo());
        assertTrue(resena.isRecomendado());
    }

    @Test
    public void crearResena_EstadoPorDefectoPublicada() throws ValidationException {
        var resena = resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0));

        assertEquals(EstadoResenhaType.PUBLICADA, resena.getEstadoResenhaType());
    }

    @Test
    public void crearResena_FechaPublicacionGeneradaAutomaticamente() throws ValidationException {
        var resena = resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                false,
                TEXTO_VALIDO,
                0.0));

        assertNotNull(resena.getFechaPublicacion());
        assertEquals(LocalDate.now(), resena.getFechaPublicacion());
    }

    @Test
    public void crearResena_HorasJugadasObtenidaDeBiblioteca() throws ValidationException {
        var resena = resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0));

        // Las horas jugadas iniciales son 0.0 (recién añadido a la biblioteca)
        assertEquals(0.0, resena.getHorasJugadas(), 0.1);
    }

    // ── Usuario ────────────────────────────────────────────────────────────

    @Test
    public void crearResena_UsuarioInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.escribirResenha(new ResenhaForm(
                        9999L, // usuario no existe
                        juegoValido.getId(),
                        true,
                        TEXTO_VALIDO,
                        0.0)));
    }

    @Test
    public void crearResena_UsuarioSinJuegoEnBiblioteca_LanzaValidationException() throws ValidationException {
        var juegoSinBiblioteca = jr.crear(new JuegoForm(
                "Portal 3",
                "null",
                "Valve",
                LocalDate.now(),
                19.99,
                0,
                ClasificacionType.PEGI_7,
                List.of( "Español" ),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION));

        // El usuario no tiene este juego en su biblioteca
        assertThrows(ValidationException.class,
                () -> resenaController.escribirResenha(new ResenhaForm(
                        usuarioValido.getId(),
                        juegoSinBiblioteca.get().getId(),
                        true,
                        TEXTO_VALIDO,
                        0.0)));
    }

    // ── Juego ──────────────────────────────────────────────────────────────

    @Test
    public void crearResena_JuegoInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.escribirResenha(new ResenhaForm(
                        usuarioValido.getId(),
                        9999L, // juego no existe
                        true,
                        TEXTO_VALIDO,
                        0.0
                        )));
    }

    @Test
    public void crearResena_ResenaDuplicada_LanzaValidationException() throws ValidationException {
        resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0));

        // El mismo usuario no puede tener dos reseñas del mismo juego
        assertThrows(ValidationException.class,
                () -> resenaController.escribirResenha(new ResenhaForm(
                        usuarioValido.getId(),
                        juegoValido.getId(),
                        false,
                        TEXTO_VALIDO,
                        0.0)));
    }

    // ── Texto ──────────────────────────────────────────────────────────────

    @Test
    public void crearResena_TextoVacio_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.escribirResenha(new ResenhaForm(
                        usuarioValido.getId(),
                        juegoValido.getId(),
                        true,
                        "", // texto obligatorio
                        0.0)));
    }

    @Test
    public void crearResena_TextoMenor50Caracteres_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.escribirResenha(new ResenhaForm(
                        usuarioValido.getId(),
                        juegoValido.getId(),
                        true,
                        "Muy corto.", // menos de 50 caracteres
                        0.0)));
    }

    @Test
    public void crearResena_TextoMayor8000Caracteres_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.escribirResenha(new ResenhaForm(
                        usuarioValido.getId(),
                        juegoValido.getId(),
                        true,
                        "a".repeat(8001), // 8001 caracteres, máximo 8000
                        0.0)));
    }

    // =====================================================
    // Eliminar reseña
    // =====================================================

    @Test
    public void eliminarResena_ResenaPropiaExistente_EliminaCorrectamente() throws ValidationException {
        var resena = rr.crear(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0)).get();

        resenaController.eliminarResenha(resena.getId(), usuarioValido.getId());

        // Tras eliminar, la reseña no debe aparecer en el listado del juego
        var resenas = resenaController.verResenasPorJuego(juegoValido.getId(),"","");
        assertTrue(resenas.stream().noneMatch(r -> r.getId() == resena.getId()));
    }

    @Test
    public void eliminarResena_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.eliminarResenha(9999L, usuarioValido.getId())); // reseña no existe
    }

    @Test
    public void eliminarResena_UsuarioNoEsDuenio_LanzaValidationException() throws ValidationException {
        var resena = resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0));

        UsuarioDto otroUsuario = usuarioController.registrar(new UsuarioForm(
                "usuario2",
                "usuario2@gmail.com",
                "12345678Aa@",
                "Usuario",
                " Dos",
                "España",
                LocalDate.now().minusYears(25),
                null,
                0));

        // La reseña pertenece a usuarioValido, no a otroUsuario
        assertThrows(ValidationException.class,
                () -> resenaController.eliminarResenha(resena.getId(), otroUsuario.getId()));
    }

    // =====================================================
    // Listar reseñas por juego
    // =====================================================

    @Test
    public void listarResenasJuego_JuegoCon1Resena_RetornaListaConUnaResena() throws ValidationException {
        resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0));

        var resenas = resenaController.verResenasPorJuego(juegoValido.getId(),"","");

        assertNotNull(resenas);
        assertFalse(resenas.isEmpty());
        assertEquals(1, resenas.size());
    }

    @Test
    public void listarResenasJuego_JuegoSinResenas_RetornaListaVacia() throws ValidationException {
        var resenas = resenaController.verResenasPorJuego(juegoValido.getId(),"","");

        assertNotNull(resenas);
        assertTrue(resenas.isEmpty());
    }

    @Test
    public void listarResenasJuego_JuegoInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.verResenasPorJuego(9999L,"","")); // juego no existe
    }

    // =====================================================
    // Ocultar reseña
    // =====================================================

    @Test
    public void ocultarResena_ResenaPropiaPublicada_QuedaOculta() throws ValidationException {
        var resena = rr.crear(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0)).get();

        resenaController.ocultarResenha(resena.getId(), usuarioValido.getId());

        // Una reseña oculta no debe aparecer en el listado público del juego
        var resenas = resenaController.verResenasPorJuego(juegoValido.getId(),"","");
        assertTrue(resenas.stream().noneMatch(r -> r.getId() == resena.getId()));
    }

    @Test
    public void ocultarResena_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.ocultarResenha(9999L, usuarioValido.getId())); // reseña no existe
    }

    @Test
    public void ocultarResena_UsuarioNoEsDuenio_LanzaValidationException() throws ValidationException {
        var resena = resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0));

        UsuarioDto otroUsuario = usuarioController.registrar(new UsuarioForm(
                "usuario2",
                "usuario2@gmail.com",
                "12345678Aa@",
                "Usuario ",
                "Dos",
                "España",
                LocalDate.now().minusYears(25),
                null
        ,0));

        assertThrows(ValidationException.class,
                () -> resenaController.ocultarResenha(resena.getId(), otroUsuario.getId()));
    }

    // =====================================================
    // Listar reseñas por usuario
    // =====================================================

    @Test
    public void listarResenasPorUsuario_UsuarioConResenas_RetornaLista() throws ValidationException {
        resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.getId(),
                juegoValido.getId(),
                true,
                TEXTO_VALIDO,
                0.0
                ));

        var resenas = resenaController.verResenasPorUsuario(usuarioValido.getId(),"");

        assertNotNull(resenas);
        assertFalse(resenas.isEmpty());
        assertEquals(usuarioValido.getNombreUsuario(), resenas.getFirst().getUsuaro().getNombreUsuario());
    }

    @Test
    public void listarResenasPorUsuario_UsuarioSinResenas_RetornaListaVacia() throws ValidationException {
        var resenas = resenaController.verResenasPorUsuario(usuarioValido.getId(),"");

        assertNotNull(resenas);
        assertTrue(resenas.isEmpty());
    }

    @Test
    public void listarResenasPorUsuario_UsuarioInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.verResenasPorUsuario(9999L,"")); // usuario no existe
    }
}
