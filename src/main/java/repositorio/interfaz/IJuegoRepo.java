package repositorio.interfaz;

import modelo.entidad.JuegoEntidad;
import modelo.form.JuegoForm;

public interface IJuegoRepo {

    JuegoEntidad crear(JuegoForm form);

    JuegoEntidad obtenerPorId(long id);

    JuegoEntidad[] obtenerTodos();

    JuegoEntidad actualizar(int id, JuegoForm form);

    boolean eliminar(int id);
}