package repositorio.interfaz;

import modelo.entidad.ResenhaEntidad;
import modelo.form.ResenhaForm;

public interface IResenhaRepo {

    ResenhaEntidad crear(ResenhaForm form);

    ResenhaEntidad obtenerPorUsuarioYJuego(long idUsuario, long idJuego);

    ResenhaEntidad[] obtenerTodas();

    ResenhaEntidad actualizar(long id, ResenhaForm form);

    boolean eliminar(long id);

    boolean existeResena(long idUsuario, long idJuego);
}