import controlador.JuegoControlador;

import excepciones.ValidationException;
import modelo.dto.JuegoDto;
import modelo.entidad.CategoriaType;
import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;
import modelo.entidad.JuegoEntidad;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import modelo.form.JuegoForm;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.JuegoRepoInMemory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestJuegoControlador {
    private final JuegoRepoInMemory repo = new JuegoRepoInMemory();
    private final JuegoControlador jc = new JuegoControlador(repo);

    JuegoForm validForm = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
            "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
            ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);

    @Test
    public void testCrearBien() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(12 , 4 , 9), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);

        var creacionbien = jc.anadirJuego(j);
        assertEquals(j.getTitulo(), creacionbien.getTitulo());
    }

    //Fecha
    @Test
    public void testFechaObligatoria() {
        try {

            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", null, 15.75, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            var creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("fecha", ErrorType.REQUERIDO)), e.getErrores());
        }
    }

    @Test
    public void testCreaFechaFutura() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(2030 , 4 , 12), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);

        var creacionbien = jc.anadirJuego(j);
        assertEquals(j.getTitulo(), creacionbien.getTitulo());
    }

    //Nombre
    @Test
    public void testNoCreaNombreDuplicado() {

        var jValido = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(2030 , 4 , 12), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
        var ej = repo.crear(jValido);

        try {

            JuegoDto creacionbien = jc.anadirJuego(jValido);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("juego", ErrorType.DUPLICADO)), e.getErrores());
        }
    }

    @Test
    public void testNombreDemasiadoLargo() {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador mimiiimiimimimimimimimimimimimimimiim mimiiimiimimimimimimimimimimimimimiim mimiiimiimimimimimimimimimimimimimiim mimiiimiimimimimimimimimimimimimimiim mimiiimiimimimimimimimimimimimimimiim",
                    "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("titulo", ErrorType.VALOR_DEMASIADO_ALTO)), e.getErrores());

        }
    }

    @Test
    public void testNombreMuyCorto() {
        try {
            JuegoForm j = new JuegoForm(" ", "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("titulo", ErrorType.REQUERIDO)), e.getErrores());
        }
    }

    @Test
    public void testNombreObligatorio() {
        try {
            JuegoForm j = new JuegoForm(null, "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("titulo", ErrorType.REQUERIDO)), e.getErrores());
        }
    }


    //descripcion
    @Test
    public void testCreaSinDescripcion() throws ValidationException {

        JuegoForm j = new JuegoForm("Pepe el cazador", "",
                "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
        JuegoDto creacionbien = jc.anadirJuego(j);

        assertEquals(j.getTitulo(), creacionbien.getTitulo());
    }

    @Test
    public void testNoCreaDescripcionNull() throws ValidationException {

        JuegoForm j = new JuegoForm("Pepe el cazador", null,
                "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
        JuegoDto creacionbien = jc.anadirJuego(j);

        assertEquals(j.getTitulo(), creacionbien.getTitulo());
    }

    //Desarrollador:
    // Obligatorio
    // Longitud: entre 2 y 100 caracteres
    @Test
    public void testDesarrolladorObligatorio() {

        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "", LocalDate.of(2015 , 4 , 12), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("desarrollador", ErrorType.REQUERIDO), new ErrorDto("desarrollador", ErrorType.VALOR_DEMASIADO_BAJO)),
                    e.getErrores());
        }
    }

    @Test
    public void testDesarrolladorMuyLargo() {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "mimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimimimimimiimimimimimimi",
                    LocalDate.of(2015 , 4 , 12), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("desarrollador", ErrorType.VALOR_DEMASIADO_ALTO)), e.getErrores());
        }
    }

    //Precio
    @Test
    public void testPrecioNegativo() {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), -5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(e.getErrores(), List.of(new ErrorDto("precio base", ErrorType.VALOR_DEMASIADO_BAJO)));
        }
    }

    @Test
    public void testPrecioDemasiadoAlto() {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), 1000.1, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(e.getErrores(), List.of(new ErrorDto("precio base", ErrorType.VALOR_DEMASIADO_ALTO)));
        }
    }

    @Test
    public void testPrecioJusto() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(2015 , 4 , 12), 0, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
        JuegoDto creacionbien = jc.anadirJuego(j);


    }


    //Opcional
    @Test
    public void testDescuentoBien() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 15,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
        JuegoDto creacionbien = jc.anadirJuego(j);
        assertEquals(j.getTitulo(), creacionbien.getTitulo());
    }

    //  Rango: 0 a 100
    @Test
    public void testDescuentoSuperaRango() throws ValidationException {

        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 120,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("descuento", ErrorType.PORCENTAJE_INVALIDO)), e.getErrores());
        }

    }

    @Test
    public void testDescuentooInferiorRango() {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, -5,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("descuento", ErrorType.PORCENTAJE_INVALIDO)), e.getErrores());
        }
    }

    @Test
    public void testDescuentoLimite() throws ValidationException {

        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 100,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
        JuegoDto creacionbien = jc.anadirJuego(j);

    }


    //  // Clasificación por edad:
    @Test
    public void testClasificacionEdad() throws ValidationException {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                    null, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("clasificacion", ErrorType.REQUERIDO)), e.getErrores());
        }
    }


    //To do // Idiomas disponibles:
    //Opcional
    @Test
    public void testSinIdiomas() throws ValidationException {

        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                ClasificacionType.PEGI_18, null, EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);

        JuegoDto creacionbien = jc.anadirJuego(j);
        assertEquals(j.getTitulo(), creacionbien.getTitulo());
    }

    @Test
    public void testEstado() throws ValidationException {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                    ClasificacionType.PEGI_18, List.of("español", "ingles"), null, CategoriaType.ACCION);

            JuegoDto creacionbien = jc.anadirJuego(j);
            assertTrue(false);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("estado", ErrorType.REQUERIDO)), e.getErrores());
        }
    }

    @Test
    public void testAplicarDescuento() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);
        JuegoEntidad juego = repo.crear(j).get();
        Double juegoDescuento = jc.aplicarDescuento(juego.getId());
        assertEquals(j.getPorcentajeDescuento() * (j.getPrecioBase() / 100), juegoDescuento);
    }

    @Test
    public void testCambiarEstado() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(2015 , 4 , 12), 5, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION);

        JuegoEntidad juego = repo.crear(j).get();

         var result=jc.cambiarEstado(juego.getId(), EstadoJuegoType.NO_DISPONIBLE);

         assertEquals(EstadoJuegoType.NO_DISPONIBLE, result.getEstado());

    }


    //test copiados de pedro

    @Test
    public void crearJuego_FormularioValido_RetornaJuegoDTO() throws ValidationException {
        var juego = jc.anadirJuego(validForm);

        assertNotNull(juego);
        assertEquals("Pepe el cazador", juego.getTitulo());
        assertEquals(EstadoJuegoType.DISPONIBLE, juego.getEstado());
    }

    // ── Título ─────────────────────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_TituloObligatorio() {
        var form = new JuegoForm(
                "", // título obligatorio
                "Descripción válida.",
                "Valve",
                LocalDate.now(),
                29.99,
                0,
                ClasificacionType.PEGI_18,
                List.of("Español") ,
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_TituloNoUnico() throws ValidationException {
        jc.anadirJuego(validForm);

        var tituloRepetidoForm = new JuegoForm(
                "Pepe el cazador", // título ya registrado
                "Otra descripción.",
                "Otro Desarrollador",
                LocalDate.now(),
                19.99,
                0,
                ClasificacionType.PEGI_12,
                List.of("Ingles"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION)
                ;

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(tituloRepetidoForm));
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_TituloMayor100Caracteres() {
        var form = new JuegoForm(
                "a".repeat(101), // 101 caracteres, máximo 100
                "Descripción válida.",
                "Valve",
                LocalDate.now(),
                29.99,
                0,

                ClasificacionType.PEGI_18,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    // ── Descripción ────────────────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioValido_DescripcionNula_Permitida() throws ValidationException {
        var form = new JuegoForm(
                "Portal 3",
                null, // descripción opcional
                "Valve",
                LocalDate.now(),
                19.99,
                0,

                ClasificacionType.PEGI_7,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        var juego = jc.anadirJuego(form);

        assertNotNull(juego);
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_DescripcionMayor2000Caracteres() {
        var form = new JuegoForm(
                "Team Fortress 3",
                "a".repeat(2001), // 2001 caracteres, máximo 2000
                "Valve",
                LocalDate.now(),
                0.0,
                0,

                ClasificacionType.PEGI_12,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    // ── Desarrollador ──────────────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_DesarrolladorObligatorio() {
        var form = new JuegoForm(
                "Dota 3",
                "Descripción.",
                "", // desarrollador obligatorio
                LocalDate.now(),
                0.0,
                0,
                ClasificacionType.PEGI_12,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_DesarrolladorMenor2Caracteres() {
        var form = new JuegoForm(
                "CS2 Legacy",
                "Descripción.",
                "V", // 1 carácter, mínimo 2
                LocalDate.now(),
                14.99,
                0,

                ClasificacionType.PEGI_16,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_DesarrolladorMayor100Caracteres() {
        var form = new JuegoForm(
                "Aperture Science Simulator",
                "Descripción.",
                "a".repeat(101), // 101 caracteres, máximo 100
                LocalDate.now(),
                9.99,
                0,
                ClasificacionType.PEGI_3,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    // ── Fecha de lanzamiento ───────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_FechaLanzamientoObligatoria() {
        var form = new JuegoForm(
                "Left 4 Dead 3",
                "Descripción.",
                "Valve",
                null, // fecha de lanzamiento obligatoria
                19.99,
                0,

                ClasificacionType.PEGI_18,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    @Test
    public void crearJuego_FormularioValido_FechaLanzamientoFutura_Permitida() throws ValidationException {
        var form = new JuegoForm(
                "Artifact 2",
                "El regreso.",
                "Valve",
                LocalDate.now().plusMonths(6), // fecha futura válida (preventa)
                9.99,
                0,
                ClasificacionType.PEGI_7,
                List.of("Español"),
                EstadoJuegoType.PREVENTA
                , CategoriaType.ACCION);

        var juego = jc.anadirJuego(form);

        assertNotNull(juego);
    }

    // ── Precio base ────────────────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioValido_PrecioBaseCero_Permitido() throws ValidationException {
        var form = new JuegoForm(
                "Dota 2 Free",
                "Gratis para todos.",
                "Valve",
                LocalDate.now(),
                0.0, // juego gratuito
                0,
                ClasificacionType.PEGI_12,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        var juego = jc.anadirJuego(form);

        assertNotNull(juego);
        assertEquals(0.0, juego.getPrecioBase(), 0.001);
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_PrecioBaseNegativo() {
        var form = new JuegoForm(
                "SteamVR Ultimate",
                "Descripción.",
                "Valve",
                LocalDate.now(),
                -1.0, // precio negativo no permitido
                0,
                ClasificacionType.PEGI_7,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_PrecioBaseSuperaMaximo() {
        var form = new JuegoForm(
                "Valve Ultra Edition",
                "Descripción.",
                "Valve",
                LocalDate.now(),
                1000.0, // supera el máximo 999.99
                0,
                ClasificacionType.PEGI_18,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    // ── Descuento actual ───────────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioValido_DescuentoCero_PorDefecto() throws ValidationException {
        var form = new JuegoForm(
                "Steam Deck: The Game",
                "Descripción.",
                "Valve",
                LocalDate.now(),
                49.99,
                0, // descuento por defecto

                ClasificacionType.PEGI_3,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        var juego = jc.anadirJuego(form);

        assertNotNull(juego);
        assertEquals(0, juego.getProcentajeDescuento(), 0.001);
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_DescuentoNegativo() {
        var form = new JuegoForm(
                "Ricochet 2",
                "Descripción.",
                "Valve",
                LocalDate.now(),
                4.99,
                -1, // descuento negativo no permitido
                ClasificacionType.PEGI_3,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_DescuentoMayor100() {
        var form = new JuegoForm(
                "Ricochet 3",
                "Descripción.",
                "Valve",
                LocalDate.now(),
                4.99,
                101, // descuento supera 100
                ClasificacionType.PEGI_3,
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    // ── Clasificación por edad ─────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_ClasificacionEdadObligatoria() {
        var form = new JuegoForm(
                "Valve Classics",
                "Descripción.",
                "Valve",
                LocalDate.now(),
                9.99,
                0,
                null, // clasificación por edad obligatoria
                List.of("Español"),
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    // ── Idiomas disponibles ────────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioValido_IdiomasNulos_Permitido() throws ValidationException {
        var form = new JuegoForm(
                "Steam Workshop Creator",
                "Descripción.",
                "Valve",
                LocalDate.now(),
                0.0,
                0,
                ClasificacionType.PEGI_3,
                null, // idiomas opcionales
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        var juego = jc.anadirJuego(form);

        assertNotNull(juego);
    }

    @Test
    public void crearJuego_FormularioInvalido_LanzaValidationException_IdiomasArrayVacio() {
        var form = new JuegoForm(
                "Steam Remote Play",
                "Descripción.",
                "Valve",
                LocalDate.now(),
                0.0,
                0,
                ClasificacionType.PEGI_3,
                List.of(), // array vacío: si se proporciona debe tener al menos uno
                EstadoJuegoType.DISPONIBLE
                , CategoriaType.ACCION);

        assertThrows(ValidationException.class,
                () -> jc.anadirJuego(form));
    }

    // ── Estado ─────────────────────────────────────────────────────────────

    @Test
    public void crearJuego_FormularioValido_EstadoDisponible_PorDefecto() throws ValidationException {
        var juego = jc.anadirJuego(validForm);

        assertEquals(EstadoJuegoType.DISPONIBLE, juego.getEstado());
    }

    // =====================================================
    // Listar catálogo
    // =====================================================

    @Test
    public void listarCatalogo_SinJuegos_RetornaListaVacia() throws ValidationException {
        var catalogo = jc.catalogoCompleto(0);

        assertNotNull(catalogo);
    }

    @Test
    public void listarCatalogo_ConJuegos_RetornaJuegosDisponibles() throws ValidationException {
        jc.anadirJuego(validForm);

        var catalogo = jc.catalogoCompleto(0);

        assertNotNull(catalogo);
        assertFalse(catalogo.isEmpty());
    }

    // =====================================================
    // Buscar juegos
    // =====================================================

    @Test
    public void buscarJuegos_TextoCoincidente_RetornaResultados() throws ValidationException {
        jc.anadirJuego(validForm);


        List<JuegoDto> resultados = jc.buscar("Pepe el cazador", CategoriaType.ACCION,
                0d, 1000d, ClasificacionType.PEGI_12, EstadoJuegoType.DISPONIBLE);

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void buscarJuegos_TextoSinCoincidencia_RetornaListaVacia() throws ValidationException {
        jc.anadirJuego(validForm);

        List<JuegoDto> resultados = jc.buscar("Pepe el cazadordresre", CategoriaType.ACCION,
                0d, 1000d, ClasificacionType.PEGI_12, EstadoJuegoType.DISPONIBLE);

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
    }

    // =====================================================
    // Aplicar descuento
    // =====================================================

    @Test
    public void aplicarDescuento_IdValido_DescuentoValido_RetornaJuegoActualizado() throws ValidationException {
        var juego = repo.crear(validForm).get();

        var actualizado = jc.aplicarDescuento(juego.getId());

        assertNotNull(actualizado);
        assertEquals(juego.getPrecioBase()* juego.getProcentajeDescuento()/100, actualizado, 0.001);
    }

    @Test
    public void aplicarDescuento_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> jc.aplicarDescuento(9999L)); // ID que no existe
    }

    @Test
    public void aplicarDescuento_DescuentoFueraDeRango_LanzaValidationException() throws ValidationException {
        var juego = jc.anadirJuego(validForm);

        assertThrows(ValidationException.class,
                () -> jc.aplicarDescuento(juego.getId())); // supera el máximo
    }

    // =====================================================
    // Cambiar estado
    // =====================================================

    @Test
    public void cambiarEstado_IdValido_EstadoValido_RetornaJuegoConNuevoEstado() throws ValidationException {
        var juego = repo.crear(validForm).get();

        var actualizado = jc.cambiarEstado(juego.getId(), EstadoJuegoType.NO_DISPONIBLE);

        assertNotNull(actualizado);
        assertEquals(EstadoJuegoType.NO_DISPONIBLE, actualizado.getEstado());
    }

    @Test
    public void cambiarEstado_IdInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> jc.cambiarEstado(9999L, EstadoJuegoType.DISPONIBLE)); // ID que no existe
    }

}
