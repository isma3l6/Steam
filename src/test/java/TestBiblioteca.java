import controlador.BibliotecaControlador;
import controlador.CompraControlador;
import controlador.JuegoControlador;
import controlador.UsuarioControlador;
import excepciones.ValidationException;
import modelo.dto.BibliotecaDto;
import modelo.entidad.*;
import modelo.form.CompraForm;
import modelo.form.JuegoForm;
import modelo.form.UsuarioForm;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.BibliotecaRepoInMemory;
import repositorio.inmemory.CompraRepoInMemory;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBiblioteca {
    private UsuarioRepoInMemory ur=new UsuarioRepoInMemory();
    private UsuarioControlador usuarioController=new UsuarioControlador(ur);
    private JuegoRepoInMemory jr=new JuegoRepoInMemory();
    private JuegoControlador juegoController=new JuegoControlador(jr);

    private BibliotecaRepoInMemory br=new BibliotecaRepoInMemory();
    private BibliotecaControlador bibliotecaController=new BibliotecaControlador(br,jr,ur);

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
    // Obtener juegos de usuario
    // =====================================================

    @Test
    void agregar()throws ValidationException{
        var b=bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());
        assertEquals(juegoValido.getId(),b.getIdJuego());
    }

    @Test
    public void obtenerJuegosUsuario_UsuarioValido_RetornaListaVacia() throws ValidationException {
        var biblioteca = bibliotecaController.verBiblioteca(usuarioValido.getId(),"");

        assertNotNull(biblioteca);
        assertTrue(biblioteca.isEmpty());
    }

    @Test
    public void obtenerJuegosUsuario_UsuarioInvalido_LanzaValidationException() {
        assertThrows(ValidationException.class,
                () -> {
                    bibliotecaController.verBiblioteca(9999L,""); // ID que no existe
                });
    }

    // =====================================================
    // Agregar juego a biblioteca
    // =====================================================

    @Test
    public void agregarJuegoBiblioteca_UsuarioYJuegoValidos_RetornaTrue() throws ValidationException {
        var resultado = bibliotecaController.agregarJuego(
                usuarioValido.getId(), juegoValido.getId());

        assertTrue(resultado!=null);
    }

    @Test
    public void agregarJuegoBiblioteca_VerificaEntradaEnBiblioteca() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        var biblioteca = bibliotecaController.verBiblioteca(usuarioValido.getId(),"");

        assertFalse(biblioteca.isEmpty());
        assertEquals(juegoValido.getId(), biblioteca.get(0).getIdJuego());
    }

    @Test
    public void agregarJuegoBiblioteca_TiempoJuegoInicialCero() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        var entrada = bibliotecaController.verBiblioteca(usuarioValido.getId(),"").get(0);

        assertEquals(0.0, entrada.getHorasJugadas(), 0.001);
    }

    @Test
    public void agregarJuegoBiblioteca_EstadoInstalacionPorDefectoNoInstalado() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        var entrada = bibliotecaController.verBiblioteca(usuarioValido.getId(),"");

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

        assertTrue(resultado!=null);
    }

    // =====================================================
    // Eliminar juego de biblioteca
    // =====================================================

    @Test
    public void eliminarJuegoBiblioteca_EntradaExistente_EliminaCorrectamente() throws ValidationException {
        bibliotecaController.agregarJuego(usuarioValido.getId(), juegoValido.getId());

        bibliotecaController.eliminarJuego(usuarioValido.getId(), juegoValido.getId());

        var biblioteca = bibliotecaController.verBiblioteca(usuarioValido.getId(),"");
        assertTrue(biblioteca.isEmpty());
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

    @Test
    public void consultaUltimaSesion_LanzaUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class,
                () -> bibliotecaController.consultarUltimaSesion(usuarioValido.getId(), juegoValido.getId()));
    }

}
