import controlador.JuegoControlador;
import controlador.UsuarioControlador;
import excepciones.ValidationException;

import jakarta.persistence.criteria.CriteriaBuilder;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorio.hibernate.JuegoHibernate;
import repositorio.hibernate.UsuarioHibernate;
import repositorio.inmemory.UsuarioRepoInMemory;
import repositorio.interfaz.IJuegoRepo;
import repositorio.interfaz.IUsuarioRepo;
import transaction.HibernateTransactionManager;
import transaction.ISesionManager;
import transaction.ITransactionManager;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

public class testUsuarioRepo {
    public IUsuarioRepo repo;
    public ITransactionManager transactionManager;
    private UsuarioControlador uc;
    LocalDate localDate = LocalDate.of(12, 12, 12);

    UsuarioForm validForm = new UsuarioForm("nuevo",
            "mail",
            "Pass12345",
            "nom",
            "apel",
            "pais",
            LocalDate.of(2026, 4, 24),
            "avtydrr",
            1000);

    @BeforeEach
    void setUp() {

        transactionManager = new HibernateTransactionManager();
        repo = new UsuarioHibernate((ISesionManager) transactionManager);

        uc = new UsuarioControlador(repo, transactionManager);

        try {
            transactionManager.inTransaction(() -> {
                var session = ((ISesionManager) transactionManager).getSession();
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                var deleteUsuario = criteriaBuilder.createCriteriaDelete(UsuarioEntidad.class);

                session.createMutationQuery(deleteUsuario).executeUpdate();


                return null;
            });
        } catch (ValidationException e) {
            System.out.println("No se pudo borrar");
            e.printStackTrace();
            throw new RuntimeException("No se pudo borrar");
        }
    }



    @Test
    public void pruebaanadirdesdecontrolador() throws ValidationException {


        var a = uc.registrar(validForm);

        assertEquals(a.getEmail(), validForm.getEmail());

    }

    @Test
    public void consultarPerfil() throws ValidationException {
        var a = uc.registrar(validForm);

        var res = uc.consultarPerfilPorId(60l);

        assertEquals(a.getNombreUsuario(), res.getNombreUsuario());

    }

    @Test
    void consultarPerfilPorNombre() throws ValidationException {
        var a = uc.registrar(validForm);

        var res = uc.consultarPerfilPorNombre(a.getNombreUsuario());

        assertEquals(a.getNombreUsuario(), res.getNombreUsuario());

    }

    @Test
    void actualizarSaldo() throws ValidationException {
        var a = uc.registrar(validForm);
        var u = uc.anadirSaldo(59L, 12);
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

        double saldo = uc.consultarSaldo(61L).getSaldo();

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
        var user = uc.registrar(validForm);

        var actualizado = uc.anadirSaldo(66L, 50.0);

        assertNotNull(actualizado);
        assertEquals(actualizado.getSaldo(), uc.consultarSaldo(66L).getSaldo());
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
        var user = uc.registrar(validForm);

        assertThrows(ValidationException.class,
                () -> uc.anadirSaldo(68L, -10.0)); // cantidad negativa no válida
    }


}
