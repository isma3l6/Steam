import controlador.BibliotecaControlador;
import controlador.JuegoControlador;
import controlador.UsuarioControlador;
import excepciones.ValidationException;
import modelo.dto.BibliotecaDto;
import modelo.entidad.*;
import modelo.form.JuegoForm;
import modelo.form.UsuarioForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.BibliotecaRepoInMemory;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;
import transaction.ITransactionManager;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBiblioteca {
    private UsuarioRepoInMemory ur;
    private UsuarioControlador usuarioController;
    private JuegoRepoInMemory jr;
    private JuegoControlador juegoController;
    UsuarioEntidad usuarioValido;
    JuegoEntidad juegoValido;
    private BibliotecaRepoInMemory br;
    private BibliotecaControlador bibliotecaController;
    public ITransactionManager transactionManager;

    @BeforeEach
    public void setUp() {
        ur = new UsuarioRepoInMemory();
        jr = new JuegoRepoInMemory();
        br = new BibliotecaRepoInMemory();


        juegoController = new JuegoControlador(jr, transactionManager);
        usuarioController = new UsuarioControlador(ur, transactionManager);
        bibliotecaController = new BibliotecaControlador(br, jr, ur);
        usuarioValido = ur.crear(new UsuarioForm("nuevo",
                "mail",
                "Pass12345",
                "nom",
                "apel",
                "pais",
                LocalDate.of(2026, 04, 24),
                "avtydrr",
                1000)).get();


        juegoValido = jr.crear(new JuegoForm("Pepe el cazador", "El cazador se llama Pepe",
                "MembrilloGames", LocalDate.of(12, 4, 9), 15.75, 0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE, CategoriaType.ACCION)).get();
    }

    // =====================================================
    // Obtener juegos de usuario
    // =====================================================


    @Test
    public void obtenerJuegosUsuario_UsuarioValido_RetornaLista() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());


        var biblioteca = bibliotecaController.verBiblioteca(usuarioValido.getId(), "");

        assertNotNull(biblioteca);
        assertTrue(!biblioteca.isEmpty());
    }

    @Test
    public void obtenerJuegosUsuario_UsuarioInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> {
                    bibliotecaController.verBiblioteca(9999L, ""); // ID que no existe
                });
    }

    // =====================================================
    // Agregar juego a biblioteca
    // =====================================================

    @Test
    public void agregarJuegoBiblioteca_UsuarioYJuegoValidos_RetornaTrue() throws ValidationException {
        var resultado = bibliotecaController.agregarJuego(
                usuarioValido.getId(), juegoValido.getId());

        assertTrue(resultado != null);
    }

    @Test
    public void agregarJuegoBiblioteca_VerificaEntradaEnBiblioteca() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        var biblioteca = bibliotecaController.verBiblioteca(usuarioValido.getId(), "");

        assertFalse(biblioteca.isEmpty());
        assertEquals(juegoValido.getTitulo(), biblioteca.get(0).getJuego().getTitulo());
    }

    @Test
    public void agregarJuegoBiblioteca_TiempoJuegoInicialCero() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        var entrada = bibliotecaController.verBiblioteca(usuarioValido.getId(), "").get(0);

        assertEquals(0.0, entrada.getHorasJugadas(), 0.001);
    }

    @Test
    public void agregarJuegoBiblioteca_EstadoInstalacionPorDefectoNoInstalado() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        var entrada = bibliotecaController.verBiblioteca(usuarioValido.getId(), "");

        assertEquals(InstalacionType.NO_INSTALADO, entrada.getFirst().getInstalacionType());
    }

    @Test
    public void agregarJuegoBiblioteca_UsuarioInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> bibliotecaController.agregarJuego(9999L, juegoValido.getId())); // usuario no existe
    }

    @Test
    public void agregarJuegoBiblioteca_JuegoInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> bibliotecaController.agregarJuego(usuarioValido.getId(), 9999L)); // juego no existe
    }

    @Test
    public void agregarJuegoBiblioteca_JuegoDuplicado_LanzaValidationException() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        // Añadir el mismo juego por segunda vez debe lanzar excepción
        assertThrows(ValidationException.class,
                () -> bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId()));
    }

    @Test
    public void agregarJuegoBiblioteca_OtroUsuarioMismoJuego_Permitido() throws ValidationException {
        // Crear un segundo usuario
        var usuario2 = ur.crear(new UsuarioForm(
                "usuario2",
                "usuario2@gmail.com",
                "12345678Aa@",
                "Usuario",
                " Dos",
                "España",
                LocalDate.now().minusYears(25),
                null,
                0));


        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        // El mismo juego puede estar en la biblioteca de otro usuario
        var resultado = bibliotecaController.agregarJuego(usuario2.get().getId(), juegoValido.getId());

        assertTrue(resultado != null);
    }

    // =====================================================
    // Eliminar juego de biblioteca
    // =====================================================

    @Test
    public void eliminarJuegoBiblioteca_EntradaExistente_EliminaCorrectamente() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        bibliotecaController.eliminarJuego(usuarioValido.getId(), juegoValido.getId());

        assertThrows(ValidationException.class,
                () -> bibliotecaController.verBiblioteca(usuarioValido.getId(), ""));

        /** var biblioteca = bibliotecaController.verBiblioteca(usuarioValido.getId(),"");
         assertTrue(biblioteca.isEmpty());*/
    }

    @Test
    public void eliminarJuegoBiblioteca_EntradaInexistente_LanzaValidationException() {
        // El juego nunca fue agregado a la biblioteca
        assertThrows(ValidationException.class,
                () -> bibliotecaController.eliminarJuego(usuarioValido.getId(), juegoValido.getId()));
    }

    @Test
    public void eliminarJuegoBiblioteca_UsuarioInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> bibliotecaController.eliminarJuego(9999L, juegoValido.getId()));
    }

    // =====================================================
    // Actualizar tiempo de juego
    // =====================================================

    @Test
    public void actualizarTiempoJuego_EntradaValida_TiempoActualizado() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        BibliotecaDto actualizado = bibliotecaController.actualizarHoras(
                usuarioValido.getId(), juegoValido.getId(), 10);

        assertNotNull(actualizado);
        assertEquals(10.0, actualizado.getHorasJugadas(), 0.1);
    }

    @Test
    public void actualizarTiempoJuego_TiempoNegativo_LanzaValidationException() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        // Tiempo negativo no permitido
        assertThrows(ValidationException.class,
                () -> bibliotecaController.actualizarHoras(usuarioValido.getId(), juegoValido.getId(), -1));
    }

    @Test
    public void actualizarTiempoJuego_UsuarioInexistente_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> bibliotecaController.actualizarHoras(9999L, juegoValido.getId(), 5));
    }

    @Test
    public void actualizarTiempoJuego_JuegoNoEnBiblioteca_LanzaValidationException() {
        // El juego existe pero no está en la biblioteca del usuario
        assertThrows(ValidationException.class,
                () -> bibliotecaController.actualizarHoras(
                        usuarioValido.getId(), juegoValido.getId(), 5));
    }

    // =====================================================
    // Consulta última sesión
    // =====================================================


}
