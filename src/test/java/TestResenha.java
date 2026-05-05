import controlador.*;
import excepciones.ValidationException;
import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;
import modelo.form.JuegoForm;
import modelo.form.ResenhaForm;
import modelo.form.UsuarioForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.*;

import java.time.LocalDate;
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
    private JuegoControlador juegoController=new JuegoControlador(jr);
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
            "MembrilloGames", new Date(12 / 4 / 9), 15.75, 0,
            ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE)).get();

    // =====================================================
    // Crear reseña
    // =====================================================

   /** @Test
    public void crearResena_FormularioValido_RetornaResenaDTO() throws ValidationException {
        var resena = resenaController.escribirResenha(new ResenhaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        assertNotNull(resena);
        assertEquals(usuarioValido.id(), resena.idUsuario());
        assertEquals(juegoValido.id(), resena.idJuego());
        assertTrue(resena.recomendado());
    }

    @Test
    public void crearResena_EstadoPorDefectoPublicada() throws ValidationException {
        var resena = resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        assertEquals(EstadoResena.PUBLICADA, resena.estado());
    }

    @Test
    public void crearResena_FechaPublicacionGeneradaAutomaticamente() throws ValidationException {
        var resena = resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                false,
                TEXTO_VALIDO,
                0.0,
                null));

        assertNotNull(resena.fechaPublicacion());
        assertEquals(LocalDate.now(), resena.fechaPublicacion());
    }

    @Test
    public void crearResena_HorasJugadasObtenidaDeBiblioteca() throws ValidationException {
        var resena = resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        // Las horas jugadas iniciales son 0.0 (recién añadido a la biblioteca)
        assertEquals(0.0, resena.horasJugadas(), 0.1);
    }

    // ── Usuario ────────────────────────────────────────────────────────────

    @Test
    public void crearResena_UsuarioInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.crearResena(new ResenaForm(
                        9999L, // usuario no existe
                        juegoValido.id(),
                        true,
                        TEXTO_VALIDO,
                        0.0,
                        null)));
    }

    @Test
    public void crearResena_UsuarioSinJuegoEnBiblioteca_LanzaValidationException() throws ValidationException {
        JuegoDTO juegoSinBiblioteca = juegoController.crearJuego(new JuegoForm(
                "Portal 3",
                null,
                "Valve",
                LocalDate.now(),
                19.99,
                0,
                "Puzzle",
                ClasificacionEdad.PEGI_7,
                new String[] { "Español" },
                EstadoJuego.DISPONIBLE));

        // El usuario no tiene este juego en su biblioteca
        assertThrows(ValidationException.class,
                () -> resenaController.crearResena(new ResenaForm(
                        usuarioValido.id(),
                        juegoSinBiblioteca.id(),
                        true,
                        TEXTO_VALIDO,
                        0.0,
                        null)));
    }

    // ── Juego ──────────────────────────────────────────────────────────────

    @Test
    public void crearResena_JuegoInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.crearResena(new ResenaForm(
                        usuarioValido.id(),
                        9999L, // juego no existe
                        true,
                        TEXTO_VALIDO,
                        0.0,
                        null)));
    }

    @Test
    public void crearResena_ResenaDuplicada_LanzaValidationException() throws ValidationException {
        resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        // El mismo usuario no puede tener dos reseñas del mismo juego
        assertThrows(ValidationException.class,
                () -> resenaController.crearResena(new ResenaForm(
                        usuarioValido.id(),
                        juegoValido.id(),
                        false,
                        TEXTO_VALIDO,
                        0.0,
                        null)));
    }

    // ── Texto ──────────────────────────────────────────────────────────────

    @Test
    public void crearResena_TextoVacio_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.crearResena(new ResenaForm(
                        usuarioValido.id(),
                        juegoValido.id(),
                        true,
                        "", // texto obligatorio
                        0.0,
                        null)));
    }

    @Test
    public void crearResena_TextoMenor50Caracteres_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.crearResena(new ResenaForm(
                        usuarioValido.id(),
                        juegoValido.id(),
                        true,
                        "Muy corto.", // menos de 50 caracteres
                        0.0,
                        null)));
    }

    @Test
    public void crearResena_TextoMayor8000Caracteres_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.crearResena(new ResenaForm(
                        usuarioValido.id(),
                        juegoValido.id(),
                        true,
                        "a".repeat(8001), // 8001 caracteres, máximo 8000
                        0.0,
                        null)));
    }

    // =====================================================
    // Eliminar reseña
    // =====================================================

    @Test
    public void eliminarResena_ResenaPropiaExistente_EliminaCorrectamente() throws ValidationException {
        var resena = resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        resenaController.eliminarResena(resena.id(), usuarioValido.id());

        // Tras eliminar, la reseña no debe aparecer en el listado del juego
        var resenas = resenaController.listarResenasJuego(juegoValido.id());
        assertTrue(resenas.stream().noneMatch(r -> r.id() == resena.id()));
    }

    @Test
    public void eliminarResena_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.eliminarResena(9999L, usuarioValido.id())); // reseña no existe
    }

    @Test
    public void eliminarResena_UsuarioNoEsDuenio_LanzaValidationException() throws ValidationException {
        var resena = resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        UsuarioDTO otroUsuario = usuarioController.creaUsuarioDTO(new UsuarioForm(
                "usuario2",
                "usuario2@gmail.com",
                "12345678Aa@",
                "Usuario Dos",
                "España",
                LocalDate.now().minusYears(25),
                null));

        // La reseña pertenece a usuarioValido, no a otroUsuario
        assertThrows(ValidationException.class,
                () -> resenaController.eliminarResena(resena.id(), otroUsuario.id()));
    }

    // =====================================================
    // Listar reseñas por juego
    // =====================================================

    @Test
    public void listarResenasJuego_JuegoCon1Resena_RetornaListaConUnaResena() throws ValidationException {
        resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        var resenas = resenaController.listarResenasJuego(juegoValido.id());

        assertNotNull(resenas);
        assertFalse(resenas.isEmpty());
        assertEquals(1, resenas.size());
    }

    @Test
    public void listarResenasJuego_JuegoSinResenas_RetornaListaVacia() throws ValidationException {
        var resenas = resenaController.listarResenasJuego(juegoValido.id());

        assertNotNull(resenas);
        assertTrue(resenas.isEmpty());
    }

    @Test
    public void listarResenasJuego_JuegoInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.listarResenasJuego(9999L)); // juego no existe
    }

    // =====================================================
    // Ocultar reseña
    // =====================================================

    @Test
    public void ocultarResena_ResenaPropiaPublicada_QuedaOculta() throws ValidationException {
        var resena = resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        resenaController.ocultarResena(resena.id(), usuarioValido.id());

        // Una reseña oculta no debe aparecer en el listado público del juego
        var resenas = resenaController.listarResenasJuego(juegoValido.id());
        assertTrue(resenas.stream().noneMatch(r -> r.id() == resena.id()));
    }

    @Test
    public void ocultarResena_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.ocultarResena(9999L, usuarioValido.id())); // reseña no existe
    }

    @Test
    public void ocultarResena_UsuarioNoEsDuenio_LanzaValidationException() throws ValidationException {
        var resena = resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        UsuarioDTO otroUsuario = usuarioController.creaUsuarioDTO(new UsuarioForm(
                "usuario2",
                "usuario2@gmail.com",
                "12345678Aa@",
                "Usuario Dos",
                "España",
                LocalDate.now().minusYears(25),
                null));

        assertThrows(ValidationException.class,
                () -> resenaController.ocultarResena(resena.id(), otroUsuario.id()));
    }

    // =====================================================
    // Listar reseñas por usuario
    // =====================================================

    @Test
    public void listarResenasPorUsuario_UsuarioConResenas_RetornaLista() throws ValidationException {
        resenaController.crearResena(new ResenaForm(
                usuarioValido.id(),
                juegoValido.id(),
                true,
                TEXTO_VALIDO,
                0.0,
                null));

        var resenas = resenaController.listarResenasPorUsuario(usuarioValido.id());

        assertNotNull(resenas);
        assertFalse(resenas.isEmpty());
        assertEquals(usuarioValido.id(), resenas.get(0).idUsuario());
    }

    @Test
    public void listarResenasPorUsuario_UsuarioSinResenas_RetornaListaVacia() throws ValidationException {
        var resenas = resenaController.listarResenasPorUsuario(usuarioValido.id());

        assertNotNull(resenas);
        assertTrue(resenas.isEmpty());
    }

    @Test
    public void listarResenasPorUsuario_UsuarioInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> resenaController.listarResenasPorUsuario(9999L)); // usuario no existe
    }*/
}
