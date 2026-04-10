package repositorio.interfaz;

import modelo.entidad.ResenhaEntidad;
import modelo.form.ResenhaForm;

import java.util.Optional;

public interface IResenhaRepo {

   Optional<ResenhaEntidad> crear(ResenhaForm form);

    Optional<ResenhaEntidad> obtenerPorUsuarioYJuego(long idUsuario, long idJuego);

    ResenhaEntidad[] obtenerTodas();

    Optional<ResenhaEntidad> actualizar(long id, ResenhaForm form);

    boolean eliminar(long id);

    boolean existeResena(long idUsuario, long idJuego);
}