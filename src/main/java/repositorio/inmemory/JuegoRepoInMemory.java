package repositorio.inmemory;

import modelo.entidad.*;
import modelo.form.JuegoForm;
import repositorio.interfaz.IJuegoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JuegoRepoInMemory implements IJuegoRepo {

    private List<JuegoEntidad> juegos = new ArrayList<>();
    private int size = 0;
    private int idCounter = 1;

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
    public Optional<JuegoEntidad> actualizar(int id, JuegoForm form) {

        for (int i = 0; i < size; i++) {

            if (juegos.get(i).getId() == id) {

                JuegoEntidad actualizado = new JuegoEntidad(
                        id,
                        form.getTitulo(),
                        form.getDesarrollador(),
                        form.getDescripcion(),
                        form.getFechaLanzamiento(),
                        form.getPrecioBase(),
                        juegos.get(i).getCategoriaType(), // mantiene categoría actual
                        form.getPorcentajeDescuento(),
                        form.getClasificaionEdad(),
                        form.getEstadoJuego()
                );

                juegos.set(i, actualizado);
                return Optional.of(actualizado);
            }
        }

        return null;
    }


    //   DELETE

    @Override
    public boolean eliminar(int id) {
        return juegos.remove(juegos.stream().filter(j -> j.getId() == id).findFirst());

    }
}