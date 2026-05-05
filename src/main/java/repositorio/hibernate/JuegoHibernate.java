package repositorio.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modelo.entidad.CategoriaType;
import modelo.entidad.JuegoEntidad;
import modelo.form.JuegoForm;
import repositorio.interfaz.IJuegoRepo;
import transaction.ISesionManager;

import java.util.List;
import java.util.Optional;
/**
public class JuegoHibernate implements IJuegoRepo {
    private ISesionManager sesionManager;

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm form) {
        var session = sesionManager.getSession();
        JuegoEntidad nuevo = new JuegoEntidad(0,
                form.getTitulo(),
                form.getDesarrollador(),
                form.getDescripcion(),
                form.getFechaLanzamiento(),
                form.getPrecioBase(),
                CategoriaType.ACCION, // puedes adaptar si viene en form
                form.getPorcentajeDescuento(),
                form.getClasificaionEdad(),
                form.getEstadoJuego()
        );
        session.persist(nuevo);
        return Optional.of(nuevo);
    }

    @Override
    public Optional<JuegoEntidad> obtenerPorId(long id) {
        var session = sesionManager.getSession();
        return
                Optional.of(session.find(JuegoEntidad.class, id));
    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {
        var session = sesionManager.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<JuegoEntidad> cq = cb.createQuery(JuegoEntidad.class);
        Root<JuegoEntidad> root = cq.from(JuegoEntidad.class);
        cq.select(root);
        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<JuegoEntidad> actualizar(int id, JuegoForm form) {

        return Optional.empty();
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }
}*/
