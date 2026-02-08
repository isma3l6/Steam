import modelo.form.UsuarioForm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestForms {


    class UsuarioFormTest {

        @Test
        void usuarioValido() {
            UsuarioForm form = new UsuarioForm();
            form.getUsername("juan123");
            form.getPassword("Password1");

            List<String> errores = form.validar();

            assertTrue(errores.isEmpty());
        }

        @Test
        void usuarioSinNombre() {
            UsuarioForm form = new UsuarioForm();
            form.setUsername("");
            form.setPassword("Password1");

            List<String> errores = form.validar();

            assertFalse(errores.isEmpty());
        }
    }


}
