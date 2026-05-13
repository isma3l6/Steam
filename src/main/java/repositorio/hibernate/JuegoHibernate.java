package repositorio.hibernate;

import excepciones.ValidationException;
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

public class JuegoHibernate implements IJuegoRepo {
    private  ISesionManager sesionManager;

    public void JuegoRepoHibernate (ISesionManager sesionManager) {
        this.sesionManager = sesionManager;

    }

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm form) {
        var session = sesionManager.getSession();
        JuegoEntidad nuevo = new JuegoEntidad(-1l,
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
    public Optional<JuegoEntidad> actualizar(long id, JuegoForm form) {
        var session = sesionManager.getSession();
        var juegoEntidad = obtenerPorId(id);
        if (juegoEntidad.isEmpty()) {
            return Optional.empty();
        }
        else {
            session.merge(new JuegoEntidad(id, form.getTitulo(),
                    form.getDesarrollador(),
                    form.getDescripcion(),
                    form.getFechaLanzamiento(),
                    form.getPrecioBase(),
                    CategoriaType.ACCION, // puedes adaptar si viene en form
                    form.getPorcentajeDescuento(),
                    form.getClasificaionEdad(),
                    form.getEstadoJuego()
            ));
        }
        return this.obtenerPorId(id);
    }

    @Override
    public boolean eliminar(long id) {
        var session = sesionManager.getSession();
        var juegoEntidad = obtenerPorId(id);
        if (juegoEntidad.isEmpty()) {
            return false;
        }
        else {
            session.remove(juegoEntidad.get());
            return true;
        }

    }


}
