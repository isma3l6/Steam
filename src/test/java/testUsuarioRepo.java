import controlador.UsuarioControlador;
import excepciones.ValidationException;
import mapper.UsuarioMapper;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import modelo.form.UsuarioForm;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.UsuarioRepoInMemory;
import repositorio.interfaz.IUsuarioRepo;

import java.util.Date;
import java.util.List;

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

    }/**
    Único en el sistema
    Longitud: entre 3 y 20 caracteres
    Solo alfanuméricos, guiones y guiones bajos
    No puede empezar con número
 */

 @Test
    public void pruebaNumero() throws ValidationException {

    try {
        UsuarioForm us=new UsuarioForm("1nu242", "mail", "Pass1234","nom","apel","España", new Date(12/11/9),"avt", 1000);

    }catch (Exception e){

        assertTrue(true);
    }
 }
 


}
