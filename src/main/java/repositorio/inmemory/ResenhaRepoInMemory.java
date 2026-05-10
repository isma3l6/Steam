package repositorio.inmemory;

import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.EstadoResenhaType;
import modelo.entidad.ResenhaEntidad;
import modelo.entidad.UsuarioEntidad;
import modelo.form.ResenhaForm;
import repositorio.interfaz.IResenhaRepo;

import java.time.LocalDate;
import java.util.*;

public class ResenhaRepoInMemory implements IResenhaRepo {

    private final List<ResenhaEntidad> resenhas = new ArrayList<>();
    private static Long idCounter = 1L;


    //CREATE

    @Override
    public Optional<ResenhaEntidad> crear(ResenhaForm form) {

        if (200 <= resenhas.size()) {
            throw new RuntimeException("Capacidad máxima alcanzada");
        }

        ResenhaEntidad nueva = new ResenhaEntidad(
                idCounter++,
                form.getIdUsuario(),
                form.getIdJuego(),
                form.isRecomendado(),
                form.getCuerpoResena(),
                LocalDate.now(),
                LocalDate.now(),
                EstadoResenhaType.PUBLICADA

        );

        resenhas.add(nueva);

        return Optional.of(nueva);
    }

    public Optional<ResenhaEntidad> obtenerPorId(long id) {
        for (ResenhaEntidad r : resenhas) {
            if (r.getId() == id) {
                return Optional.of(r);
            }
        }
        return Optional.empty();
    }

    //READ BY USUARIO + JUEGO

    @Override
    public Optional<ResenhaEntidad> obtenerPorUsuarioYJuego(long idUsuario, long idJuego) {
        ResenhaEntidad resultado=null;
        for (ResenhaEntidad r : resenhas) {
            if (r.getUsuaroId() == idUsuario &&
                    r.getNombreJuegoId() == idJuego) {
                return Optional.of(r);
            }

        }
        return Optional.ofNullable(resultado);
    }


    //READ ALL

    @Override
    public List<ResenhaEntidad> obtenerTodas() {

        return resenhas.stream().toList();
    }


    //UPDATE

    @Override
    public Optional<ResenhaEntidad> actualizar(long id, ResenhaForm form) {
        for (ResenhaEntidad r : resenhas) {
            if (r.getId() == id) {
                ResenhaEntidad actualizada = new ResenhaEntidad(
                        id,
                        form.getIdUsuario(),
                        form.getIdJuego(),
                        form.isRecomendado(),
                        form.getCuerpoResena(),
                        r.getFechaPublicacion(), // mantener fecha original
                        LocalDate.now(),
                        EstadoResenhaType.PUBLICADA
                );
                r = actualizada;
                return Optional.of(actualizada);
            }
        }
        return null;
    }

    //DELETE
    @Override
    public boolean eliminar(long id) {
        for (ResenhaEntidad r : resenhas) {
            if (r.getId() == id) {
                // desplazamiento a la izquierda
                resenhas.remove(r);
                return true;
            }
        }
        return false;
    }

    //EXISTE RESEÑA
    @Override
    public boolean existeResena(long idUsuario, long idJuego) {
        return obtenerPorUsuarioYJuego(idUsuario, idJuego).isPresent();
    }
}