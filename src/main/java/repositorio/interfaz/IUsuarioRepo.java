package repositorio.interfaz;

import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepo {

    Optional<UsuarioEntidad> crear(UsuarioForm form);

    Optional<UsuarioEntidad> obtenerPorId(long id);

    List<UsuarioEntidad> obtenerTodos();

    Optional<UsuarioEntidad> actualizar(long id, UsuarioForm form);

    boolean eliminar(long id);
    Optional<UsuarioEntidad>buscarUsuarioPorNombre(String nombreUsuario);
}
