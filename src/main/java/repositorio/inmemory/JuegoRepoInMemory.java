package repositorio.inmemory;

import excepciones.ValidationException;
import modelo.entidad.*;
import modelo.form.JuegoForm;
import repositorio.interfaz.IJuegoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JuegoRepoInMemory implements IJuegoRepo {

    private final List<JuegoEntidad> juegos = new ArrayList<>();
    private long idCounter = 1;

    // CREATE
    @Override
    public Optional<JuegoEntidad> crear(JuegoForm form) {


        JuegoEntidad nuevo = new JuegoEntidad(
                idCounter++,
                form.getTitulo(),
                form.getDesarrollador(),
                form.getDescripcion(),
                form.getFechaLanzamiento(),
                form.getPrecioBase(),
                CategoriaType.ACCION, // puedes adaptar si viene en form
                form.getPorcentajeDescuento(),
                form.getClasificaionEdad(),
                form.getEstadoJuego()
        );

        juegos.add(nuevo);
        return Optional.of(nuevo);
    }


    //READ BY ID

    @Override
    public Optional<JuegoEntidad> obtenerPorId(long id) {

        return juegos.stream().filter(j -> j.getId() == id).findFirst();
    }


    //READ ALL

    @Override
    public List<JuegoEntidad> obtenerTodos() {


        return juegos.stream().toList();
    }


    //  UPDATE

    @Override
    public Optional<JuegoEntidad> actualizar(long id, JuegoForm form) {
        JuegoEntidad actualizado=null;

        for (JuegoEntidad j:juegos) {

            if (j.getId() == id) {

                 actualizado = new JuegoEntidad(
                        id,
                        form.getTitulo(),
                        form.getDesarrollador(),
                        form.getDescripcion(),
                        form.getFechaLanzamiento(),
                        form.getPrecioBase(),
                        j.getCategoriaType(), // mantiene categoría actual
                        form.getPorcentajeDescuento(),
                        form.getClasificaionEdad(),
                        form.getEstadoJuego()
                );
                juegos.remove(j);

               juegos.add(actualizado);
                return Optional.of(actualizado);
            }
        }
        return Optional.empty();
    }


    //   DELETE

    @Override
    public boolean eliminar(long id) {
        return juegos.remove(juegos.stream().filter(j -> j.getId() == id).findFirst().get());

    }
}