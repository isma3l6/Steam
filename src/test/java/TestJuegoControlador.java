import controlador.JuegoControlador;

import excepciones.ValidationException;
import modelo.dto.JuegoDto;
import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;
import modelo.entidad.JuegoEntidad;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import modelo.form.JuegoForm;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.JuegoRepoInMemory;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestJuegoControlador {
    private final JuegoRepoInMemory repo = new JuegoRepoInMemory();
    private final JuegoControlador jc = new JuegoControlador(repo);

    @Test
    public void testCrearBien() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", new Date(12 / 4 / 9), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);

        var creacionbien = jc.anadirJuego(j);
        assertEquals(j.getTitulo(), creacionbien.getTitulo());
    }

    //Fecha
    @Test
    public void testFechaObligatoria() {
        try {

            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", null, 15.75, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            var creacionbien = jc.anadirJuego(j);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("fecha", ErrorType.REQUERIDO)), e.getErrores());
        }
    }

    @Test
    public void testCreaFechaFutura() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", new Date(12 / 4 / 2030), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);

        var creacionbien = jc.anadirJuego(j);
        assertEquals(j.getTitulo(), creacionbien.getTitulo());
    }

    //Nombre
    @Test
    public void testNoCreaNombreDuplicado() {

        var jValido = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", new Date(12 / 4 / 2030), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
        var ej = repo.crear(jValido);

        try {

            JuegoDto creacionbien = jc.anadirJuego(jValido);

        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("juego", ErrorType.DUPLICADO)), e.getErrores());
        }
    }

    @Test
    public void testNombreDemasiadoLargo() {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador mimiiimiimimimimimimimimimimimimimiim mimiiimiimimimimimimimimimimimimimiim mimiiimiimimimimimimimimimimimimimiim mimiiimiimimimimimimimimimimimimimiim mimiiimiimimimimimimimimimimimimimiim",
                    "El cazador se llama Pepe",
                    "MembrilloGames", new Date(12 / 4 / 2015), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);


        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("titulo", ErrorType.VALOR_DEMASIADO_ALTO)), e.getErrores());

        }
    }

    @Test
    public void testNombreMuyCorto() {
        try {
            JuegoForm j = new JuegoForm(" ", "El cazador se llama Pepe",
                    "MembrilloGames", new Date(12 / 4 / 2015), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);

        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("titulo", ErrorType.REQUERIDO)), e.getErrores());
        }
    }

    //descripcion
    @Test
    public void testCreaSinDescripcion() throws ValidationException {

        JuegoForm j = new JuegoForm("Pepe el cazador", "",
                "MembrilloGames", new Date(12 / 4 / 2015), 5, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
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
                    "", new Date(12 / 4 / 2015), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);
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
                    new Date(12 / 4 / 2015), 5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);
        } catch (ValidationException e) {
            assertEquals(List.of(new ErrorDto("desarrollador", ErrorType.VALOR_DEMASIADO_ALTO)), e.getErrores());
        }
    }

    //Precio
    @Test
    public void testPrecioNegativo() {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", new Date(12 / 4 / 2015), -5, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);

        } catch (ValidationException e) {
            assertEquals(e.getErrores(), List.of(new ErrorDto("precio base", ErrorType.VALOR_DEMASIADO_BAJO)));
        }
    }

    @Test
    public void testPrecioDemasiadoAlto() {
        try {
            JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                    "MembrilloGames", new Date(12 / 4 / 2015), 1000, 0,
                    ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);

        } catch (ValidationException e) {
            assertEquals(e.getErrores(), List.of(new ErrorDto("precio base", ErrorType.VALOR_DEMASIADO_ALTO)));
        }
    }

    @Test
    public void testPrecioJusto() throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", new Date(12 / 4 / 2015), 999.99, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
        JuegoDto creacionbien = jc.anadirJuego(j);


    }
    //  Descuento actual:
    //Opcional
    //  Rango: 0 a 100
    //   Solo números enteros
    //   Valor por defecto: 0
    //  // Clasificación por edad:
    //  Obligatoria
    //   Debe ser uno de: {PEGI_3, PEGI_7, PEGI_12, PEGI_16, PEGI_18}
    // // Idiomas disponibles:
    //Opcional
    // Al menos un idioma si se proporciona
    // Longitud máxima: 200 caracteres
    // //  Estado:
    //  Valor por defecto: DISPONIBLE
    //   Debe ser uno de: {DISPONIBLE, PREVENTA, ACCESO_ANTICIPADO, NO_DISPONIBLE}


}
