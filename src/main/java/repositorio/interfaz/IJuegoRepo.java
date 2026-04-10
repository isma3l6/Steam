package repositorio.interfaz;

import modelo.entidad.JuegoEntidad;
import modelo.form.JuegoForm;

import java.util.List;
import java.util.Optional;

public interface IJuegoRepo {

    Optional<JuegoEntidad> crear(JuegoForm form);

    Optional<JuegoEntidad> obtenerPorId(long id);

    List<JuegoEntidad> obtenerTodos();

    Optional<JuegoEntidad> actualizar(int id, JuegoForm form);

    boolean eliminar(int id);
}