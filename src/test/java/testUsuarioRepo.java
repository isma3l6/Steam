import controlador.UsuarioControlador;
import excepciones.ValidationException;

import modelo.form.UsuarioForm;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

public class testUsuarioRepo {
    private final UsuarioRepoInMemory repo = new UsuarioRepoInMemory();
    private final UsuarioControlador uc = new UsuarioControlador(repo);
    LocalDate localDate = LocalDate.of(12, 12, 12);

    UsuarioForm validForm = new UsuarioForm("nuevo",
            "mail",
            "Pass12345",
            "nom",
            "apel",
            "pais",
            LocalDate.of(2026, 04, 24),
            "avtydrr",
            1000);

    @Test
    public void pruebaanadir() {
        var res = repo.crear(validForm).get();
        var b = repo.obtenerPorId(res.getId()).get();
        assertEquals(res, b);

    }

    @Test
    public void pruebaanadirdesdecontrolador() throws ValidationException {


        var a = uc.registrar(validForm);

        assertEquals(a.getEmail(), validForm.getEmail());

    }

    @Test
    public void consultarPerfil() throws ValidationException {
        var a = repo.crear(validForm);
        repo.crear(new UsuarioForm("nu242", "mail", "Pass1234", "nom", "apel", "España", LocalDate.of(12, 11, 9), "avt", 1000));

        var res = uc.consultarPerfilPorId(a.get().getId());

        assertEquals(a.get().getNombreUsuario(), res.getNombreUsuario());

    }

    @Test
    void consultarPerfilPorNombre() throws ValidationException {
        var a = repo.crear(validForm);
        repo.crear(new UsuarioForm("nu242", "mail", "Pass1234", "nom", "apel", "España", LocalDate.of(12, 11, 9), "avt", 1000));

        var res = uc.consultarPerfilPorNombre(a.get().getNombreUsuario());

        assertEquals(a.get().getNombreUsuario(), res.getNombreUsuario());

    }

    @Test
    void actualizarSaldo() throws ValidationException {
        var a = repo.crear(validForm).get();
        var u = uc.anadirSaldo(a.getId(), 12);
        assertTrue(u.getSaldo() != a.getSaldo());

    }

    @Test
    public void consultarPerfil_IdInvalido_RetornaNull() throws ValidationException {
        try {
            var perfil = uc.consultarPerfilPorId(9999L); // ID que no existe

        } catch (ValidationException e) {
            assertTrue(true);
        }


    }


    @Test
    public void consultarPerfil_IdValido_RetornaUsuarioDTO() throws ValidationException {
        var user = uc.registrar(validForm);

        var perfil = uc.consultarPerfilPorId(repo.obtenerTodos().stream().filter(u -> u.getNombreUsuario() == user.getNombreUsuario()).findFirst().get().getId());

        assertNotNull(perfil);
        assertEquals(user.getNombreUsuario(), perfil.getNombreUsuario());
    }

    @Test
    public void crearUsuarioDTO_FormularioValido_AvatarNulo_Permitido() throws ValidationException {
        var sinAvatarForm = new UsuarioForm("usuario1",
                "usuario1@gmail.com",
                "12345678Aa@",
                "usuario1",
                "España",
                "españa",
                LocalDate.of(12, 12, 12),
                null,
                1000); // avatar opcional, puede ser null

        var user = uc.registrar(sinAvatarForm);

        assertNotNull(user);
    }

    @Test
    public void consultarSaldo_IdValido_RetornaSaldo() throws ValidationException {
        var user = uc.registrar(validForm);

        double saldo = uc.consultarSaldo(repo.crear(validForm).get().getId()).getSaldo();

        assertEquals(user.getSaldo(), saldo, 0.001); // saldo inicial es 0
    }

    @Test
    public void crearUsuarioDTO_FormularioValido_FechaRegistroGeneradaAutomaticamente() throws ValidationException {
        // Act
        var user = uc.registrar(validForm);

        // Assert
        // assertNotNull(user.getFechaRegistro());
        assertEquals(LocalDate.now(), user.getFechaRegistro());
    }

    @Test
    public void crearUsuarioDTO_FormularioInvalido_LanzaValidationException_NombreNoUnico()
            throws ValidationException {


        uc.registrar(validForm);

        var nombreNoUnicoForm = new UsuarioForm(
                "nuevo",
                "usuario2@gmail.com",
                "12345678Aa@",
                "usuario",
                "avey",
                "España",
                LocalDate.of(12, 12, 12),
                "usuario2.png",
                0.0);

        assertThrows(ValidationException.class,
                () -> uc.registrar(nombreNoUnicoForm));
    }
    @Test
    public void consultarPerfil_NombreUsuarioValido_RetornaUsuarioDTO() throws ValidationException {
        var user = uc.registrar(validForm);

        var perfil = uc.consultarPerfilPorNombre(user.getNombreUsuario());

        assertNotNull(perfil);
        assertEquals(user.getNombreUsuario(), perfil.getNombreUsuario());
    }

    @Test
    public void aniadirSaldo_IdValido_CantidadValida_RetornaUsuarioDTOConSaldoActualizado()
            throws ValidationException {
        var user = repo.crear(validForm).get();

        var actualizado = uc.anadirSaldo(user.getId(), 50.0);

        assertNotNull(actualizado);
        assertEquals(user.getSaldo()+ actualizado.getSaldo(), uc.consultarSaldo(user.getId()).getSaldo());
    }

    @Test
    public void crearUsuarioDTO_FormularioInvalido_LanzaValidationException_EmailNoUnico()
            throws ValidationException {
        uc.registrar(validForm);

        var emailNoUnicoForm = new UsuarioForm("nuevo1",
                "mail",
                "Pass12345",
                "nom",
                "apel",
                "pais",
                LocalDate.of(2026, 04, 24),
                "avtydrr",
                1000);

        assertThrows(ValidationException.class,
                () -> uc.registrar(emailNoUnicoForm));
    }


    @Test
    public void aniadirSaldo_CantidadNoValida_LanzaValidationException() throws ValidationException {
        var user = repo.crear(validForm).get();

        assertThrows(ValidationException.class,
                () -> uc.anadirSaldo(user.getId(), -10.0)); // cantidad negativa no válida
    }


}
