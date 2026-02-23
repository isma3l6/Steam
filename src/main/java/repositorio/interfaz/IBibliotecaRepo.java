package repositorio.interfaz;


import modelo.entidad.BibliotecaEntidad;
import modelo.form.BibliotecaForm;

import java.util.List;
import java.util.Optional;

    public interface IBibliotecaRepo {

        Optional<BibliotecaEntidad> crear(BibliotecaForm form);

        Optional<BibliotecaEntidad> obtenerPorId(Long id);

        List<BibliotecaEntidad> obtenerTodos();

        Optional<BibliotecaEntidad> actualizar(Long id, BibliotecaForm form);

        boolean eliminar(Long id);
    }

