package repositorio.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;
import repositorio.interfaz.IUsuarioRepo;
import transaction.ISesionManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UsuarioHibernate implements IUsuarioRepo {
    private  ISesionManager sesionManager;
    public UsuarioHibernate(ISesionManager sesionManager){
        this.sesionManager = sesionManager;
    }
    @Override
    public Optional<UsuarioEntidad> crear(UsuarioForm form) {
        var session = sesionManager.getSession();
        UsuarioEntidad nuevo = new UsuarioEntidad (
                0l,
                form.getNombreUsuario(),
                form.getEmail(),
                form.getContrasena(),
                form.getNombre(),
                form.getApellido(),
                form.getPais(),
                form.getFechaNacimiento(),
                LocalDate.now(),form.getAvatr(),
                form.getSaldo(),
                EstadoUserType.ACTIVA);
        session.persist(nuevo);
        return Optional.of(nuevo);
    }

    @Override
    public Optional<UsuarioEntidad> obtenerPorId(long id) {
        var session = sesionManager.getSession();
        return
                Optional.of(session.find(UsuarioEntidad.class, id));
    }

    @Override
    public List<UsuarioEntidad> obtenerTodos() {

        var session = sesionManager.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntidad> cq = cb.createQuery(UsuarioEntidad.class);
        Root<UsuarioEntidad> root = cq.from(UsuarioEntidad.class);
        cq.select(root);
        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<UsuarioEntidad> actualizar(long id, UsuarioForm form) {
        var session = sesionManager.getSession();
        var usuarioEntidad = obtenerPorId(id);
        if (usuarioEntidad.isEmpty()) {
            return Optional.empty();
        }
        else {
            session.merge(new UsuarioEntidad(
                    id,
                    form.getNombreUsuario(),
                    form.getEmail(),
                    form.getContrasena(),
                    form.getNombre(),
                    form.getApellido(),
                    form.getPais(),
                    form.getFechaNacimiento(),
                    LocalDate.now(), form.getAvatr(),
                    form.getSaldo(),
                    usuarioEntidad.get().getEstadoType()));

            return this.obtenerPorId(id);
        }
    }

    @Override
    public boolean eliminar(long id) {

        var session = sesionManager.getSession();
        var usuarioEntidad = obtenerPorId(id);
        if (usuarioEntidad.isEmpty()) {
            return false;
        }
        else {
            session.remove(usuarioEntidad.get());
            return true;
        }
    }

    @Override
    public Optional<UsuarioEntidad> buscarUsuarioPorNombre(String nombreUsuario) {
        var session = sesionManager.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntidad> cq = cb.createQuery(UsuarioEntidad.class);
        Root<UsuarioEntidad> root = cq.from(UsuarioEntidad.class);

        cq.select(root).where(cb.equal(root.get("nombre Usuario"), nombreUsuario));

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public Optional<UsuarioEntidad> buscarUsuarioPorCorreo(String email) {
        var session = sesionManager.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntidad> cq = cb.createQuery(UsuarioEntidad.class);
        Root<UsuarioEntidad> root = cq.from(UsuarioEntidad.class);

        cq.select(root).where(cb.equal(root.get("email"), email));

        return session.createQuery(cq).getResultStream().findFirst();
    }
}
