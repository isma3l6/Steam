package repositorio.interfaz;

import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;

public interface IUsuarioRepo {

    UsuarioEntidad crear(UsuarioForm form);

    UsuarioEntidad obtenerPorId(long id);

    UsuarioEntidad[] obtenerTodos();

    UsuarioEntidad actualizar(long id, UsuarioForm form);

    boolean eliminar(long id);
}
