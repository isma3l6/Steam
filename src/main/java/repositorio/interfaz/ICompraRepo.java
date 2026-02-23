package repositorio.interfaz;

import java.util.List;
import java.util.Optional;

import modelo.entidad.CompraEntidad;
import modelo.form.CompraForm;

public interface ICompraRepo {

    /**
     * Crear una nueva compra
     */
    Optional<CompraEntidad> crear(CompraForm form);

    /**
     * Obtener una compra por su ID
     */
    Optional<CompraEntidad> obtenerPorId(Long id);

    /**
     * Obtener todas las compras
     */
    List<CompraEntidad> obtenerTodos();

    /**
     * Actualizar una compra existente
     */
    Optional<CompraEntidad> actualizar(Long id, CompraForm form);

    /**
     * Eliminar una compra por su ID
     */
    boolean eliminar(Long id);
}