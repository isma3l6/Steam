package repositorio.inmemory;

import modelo.entidad.EstadoResenhaType;
import modelo.entidad.ResenhaEntidad;
import modelo.form.ResenhaForm;
import repositorio.interfaz.IResenhaRepo;

import java.util.*;

public class ResenhaRepoInMemory implements IResenhaRepo {


    private ResenhaEntidad[] resenas = new ResenhaEntidad[200];
    private int size = 0;
    private long idCounter = 1L;


       //CREATE

    @Override
    public Optional <ResenhaEntidad> crear(ResenhaForm form) {

        if (size >= resenas.length) {
            throw new RuntimeException("Capacidad máxima alcanzada");
        }

        ResenhaEntidad nueva = new ResenhaEntidad(
                idCounter++,
                form.getIdUsuario(),
                form.getIdJuego(),
                form.isRecomendado(),
                form.getCuerpoResena(),
                new Date() ,// fecha de creación
                new Date(),
                EstadoResenhaType.PUBLICADA

        );

        resenas[size] = nueva;
        size++;

        return Optional.of(nueva);
    }
public Optional<ResenhaEntidad> obtenerPorId(long id){
    for (int i = 0; i < size; i++) {
        if (resenas[i].getId() == id) {
            return Optional.of(resenas[i]);
        }
    }
    return null;}

       //READ BY USUARIO + JUEGO

    @Override
    public Optional<ResenhaEntidad> obtenerPorUsuarioYJuego(long idUsuario, long idJuego) {
        for (int i = 0; i < size; i++) {
            if (resenas[i].getUsuaroId() == idUsuario &&
                    resenas[i].getNombreJuegoId() == idJuego) {
                return Optional.of(resenas[i]);
            }
        }
        return null;
    }


       //READ ALL

    @Override
    public ResenhaEntidad[] obtenerTodas() {
        ResenhaEntidad[] copia = new ResenhaEntidad[size];
        for (int i = 0; i < size; i++) {
            copia[i] = resenas[i];
        }
        return copia;
    }


       //UPDATE

    @Override
    public Optional<ResenhaEntidad> actualizar(long id, ResenhaForm form) {
        for (int i = 0; i < size; i++) {
            if (resenas[i].getId() == id) {
                ResenhaEntidad actualizada = new ResenhaEntidad(
                        id,
                        form.getIdUsuario(),
                        form.getIdJuego(),
                        form.isRecomendado(),
                        form.getCuerpoResena(),
                        resenas[i].getFechaPublicacion(), // mantener fecha original
                        new Date(),
                        EstadoResenhaType.PUBLICADA
                );
                resenas[i] = actualizada;
                return Optional.of(actualizada);
            }
        }
        return null;
    }

    //DELETE
    @Override
    public boolean eliminar(long id) {
        for (int i = 0; i < size; i++) {
            if (resenas[i].getId() == id) {
                // desplazamiento a la izquierda
                for (int j = i; j < size - 1; j++) {
                    resenas[j] = resenas[j + 1];
                }
                resenas[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

       //EXISTE RESEÑA
    @Override
    public boolean existeResena(long idUsuario, long idJuego) {
        return obtenerPorUsuarioYJuego(idUsuario, idJuego) != null;
    }
}