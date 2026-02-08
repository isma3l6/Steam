import modelo.form.UsuarioForm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestControlerUser {
    @Test
    public void registraUsuarioCorrecto() {
        UsuarioServiceFake service = new UsuarioServiceFake();
        UsuarioController controller = new UsuarioController(service);

        UsuarioForm form = new UsuarioForm();
        form.setUsername("juan123");
        form.setPassword("Password1");

        boolean resultado = controller.registrar(form);

        assertTrue(resultado);
    }

    @Test
    void noRegistraUsuarioInvalido() {
        UsuarioServiceFake service = new UsuarioServiceFake();
        UsuarioController controller = new UsuarioController(service);

        UsuarioForm form = new UsuarioForm();
        form.setUsername("");
        form.setPassword("123");

        boolean resultado = controller.registrar(form);

        assertFalse(resultado);
    }
}

