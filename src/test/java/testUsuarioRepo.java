import controlador.UsuarioControlador;
import excepciones.ValidationException;
import mapper.UsuarioMapper;
import modelo.form.UsuarioForm;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.UsuarioRepoInMemory;
import repositorio.interfaz.IUsuarioRepo;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class testUsuarioRepo {
    private final UsuarioRepoInMemory repo=new UsuarioRepoInMemory();
    private final UsuarioControlador uc=new UsuarioControlador(repo);
    @Test
    public void pruebaanadir(){
        UsuarioForm u=new UsuarioForm("nu", "mail", "pass","nom","apel","pais", new Date(12/11/9),"avt", 1000);
        var res=repo.crear(u).get();
        var b=repo.obtenerPorId(res.getId()).get();
        assertEquals(res,b);

    }
    @Test
    public void pruebaanadirdesdecontrolador() throws ValidationException {

        UsuarioForm us=new UsuarioForm("nu242", "mail", "Pass1234","nom","apel","España", new Date(12/11/9),"avt", 1000);

        var a =uc.registrar(us);

        assertEquals(a.getEmail(),us.getEmail());

    }
    @Test
    public void consultarPerfil()throws ValidationException{
        var a =repo.crear(new UsuarioForm("nu241", "mail1", "Pass1234","nom","apel","España", new Date(12/11/9),"avt", 1000));
        repo.crear(new UsuarioForm("nu242", "mail", "Pass1234","nom","apel","España", new Date(12/11/9),"avt", 1000));

        var res=uc.consultarPerfilPorId(a.get().getId());

        assertEquals(a.get().getNombreUsuario(),res.getNombreUsuario());

    }
    @Test
    void consultarPerfilPorNombre()throws ValidationException{
        var a =repo.crear(new UsuarioForm("nu241", "mail1", "Pass1234","nom","apel","España", new Date(12/11/9),"avt", 1000));
        repo.crear(new UsuarioForm("nu242", "mail", "Pass1234","nom","apel","España", new Date(12/11/9),"avt", 1000));

        var res=uc.consultarPerfilPorNombre(a.get().getNombreUsuario());

        assertEquals(a.get().getNombreUsuario(),res.getNombreUsuario());

    }
    @Test
    void actualizarSaldo() throws ValidationException{
        var a =repo.crear(new UsuarioForm("nu241", "mail1", "Pass1234","nom","apel","España", new Date(12/11/9),"avt", 100)).get();

        var u=uc.anadirSaldo(a.getId(), 12);
        assertTrue(u.getSaldo()!=a.getSaldo());

    }




}
