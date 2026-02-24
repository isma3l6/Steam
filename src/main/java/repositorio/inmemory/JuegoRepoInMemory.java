package repositorio.inmemory;

import modelo.entidad.*;
import modelo.form.JuegoForm;
import repositorio.interfaz.IJuegoRepo;



public class JuegoRepoInMemory implements IJuegoRepo {

    private JuegoEntidad[] juegos = new JuegoEntidad[100];
    private int size = 0;
    private int idCounter = 1;

      // CREATE
    @Override
    public JuegoEntidad crear(JuegoForm form) {

        if (size >= juegos.length) {
            throw new RuntimeException("Capacidad máxima alcanzada");
        }

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

        juegos[size] = nuevo;
        size++;

        return nuevo;
    }


       //READ BY ID

    @Override
    public JuegoEntidad obtenerPorId(long id) {

        for (int i = 0; i < size; i++) {
            if (juegos[i].getId() == id) {
                return juegos[i];
            }
        }

        return null;
    }


       //READ ALL

    @Override
    public JuegoEntidad[] obtenerTodos() {

        JuegoEntidad[] copia = new JuegoEntidad[size];

        for (int i = 0; i < size; i++) {
            copia[i] = juegos[i];
        }

        return copia;
    }


     //  UPDATE

    @Override
    public JuegoEntidad actualizar(int id, JuegoForm form) {

        for (int i = 0; i < size; i++) {

            if (juegos[i].getId() == id) {

                JuegoEntidad actualizado = new JuegoEntidad(
                        id,
                        form.getTitulo(),
                        form.getDesarrollador(),
                        form.getDescripcion(),
                        form.getFechaLanzamiento(),
                        form.getPrecioBase(),
                        juegos[i].getCategoriaType(), // mantiene categoría actual
                        form.getPorcentajeDescuento(),
                        form.getClasificaionEdad(),
                        form.getEstadoJuego()
                );

                juegos[i] = actualizado;
                return actualizado;
            }
        }

        return null;
    }


    //   DELETE

    @Override
    public boolean eliminar(int id) {

        for (int i = 0; i < size; i++) {

            if (juegos[i].getId() == id) {

                // desplazamiento a la izquierda
                for (int j = i; j < size - 1; j++) {
                    juegos[j] = juegos[j + 1];
                }

                juegos[size - 1] = null;
                size--;

                return true;
            }
        }

        return false;
    }
}