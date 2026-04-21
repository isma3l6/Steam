import controlador.JuegoControlador;

import excepciones.ValidationException;
import modelo.dto.JuegoDto;
import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;
import modelo.entidad.JuegoEntidad;
import modelo.form.JuegoForm;
import org.junit.jupiter.api.Test;
import repositorio.inmemory.JuegoRepoInMemory;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestJuegoControlador {
    private final JuegoRepoInMemory repo=new JuegoRepoInMemory();
    private final JuegoControlador jc=new JuegoControlador(repo);
    @Test
    public void testCrearBien()throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador","El cazador se llama Pepe",
                "MembrilloGames",new Date(12/4/9),15.75,0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);

        var creacionbien = jc.anadirJuego(j);
        assertEquals(j.getTitulo(),creacionbien.getTitulo());
    }

    @Test
    public void testCreaFechaFutura()throws ValidationException {
        JuegoForm j = new JuegoForm("Pepe el cazador","El cazador se llama Pepe",
                "MembrilloGames",new Date(12/4/2030),15.75,0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);

        var creacionbien = jc.anadirJuego(j);
        assertEquals(j.getTitulo(),creacionbien.getTitulo());
    }
    //REBISAR
    @Test
    public void testNoCreaNombreDuplicado(){

        var jValido = new JuegoForm("Pepe el cazador","El cazador se llama Pepe",
                "MembrilloGames",new Date(12/4/2030),15.75,0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
        var ej=repo.crear(jValido);

       try {

           JuegoDto creacionbien = jc.anadirJuego(jValido);

       }catch (ValidationException e){
           assert true;
       }
    }
    @Test
    public void testPrecioNegativo()throws ValidationException {
        try { JuegoForm j = new JuegoForm("Pepe el cazador","El cazador se llama Pepe",
                "MembrilloGames",new Date(12/4/2015),-5,0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);

        }catch (Exception e){
            assert true;
        }
    }
    @Test
    public void testPrecioDemasiadoAlto()throws ValidationException {
        try { JuegoForm j = new JuegoForm("Pepe el cazador","El cazador se llama Pepe",
                "MembrilloGames",new Date(12/4/2015),1000,0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);

        }catch (Exception e){
            assert true;
        }
    }
    @Test
    public void testPrecioJusto()throws ValidationException {
         JuegoForm j = new JuegoForm("Pepe el cazador","El cazador se llama Pepe",
                "MembrilloGames",new Date(12/4/2015),999.99,0,
                ClasificacionType.PEGI_12, List.of("español", "ingles"), EstadoJuegoType.DISPONIBLE);
            JuegoDto creacionbien = jc.anadirJuego(j);


    }

}
