package controlador;

import modelo.entidad.EstadoResenhaType;
import modelo.entidad.ResenhaEntidad;
import modelo.form.ResenhaForm;
import repositorio.inmemory.ResenhaRepoInMemory;
import repositorio.inmemory.BibliotecaRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ResenhaControlador {

    private final ResenhaRepoInMemory resenhaRepo;
    private final BibliotecaRepoInMemory bibliotecaRepo;
    private final UsuarioRepoInMemory usuarioRepo;

    public ResenhaControlador(ResenhaRepoInMemory resenhaRepo,
                             BibliotecaRepoInMemory bibliotecaRepo,
                             UsuarioRepoInMemory usuarioRepo) {
        this.resenhaRepo = resenhaRepo;
        this.bibliotecaRepo = bibliotecaRepo;
        this.usuarioRepo = usuarioRepo;
    }


     //ESCRIBIR RESEÑA

    public String escribirResenha(ResenhaForm form) {

        // 1. Validaciones de formato
        var errores = form.validarResena();
        if (!errores.isEmpty()) {
            return "Errores de validación: " + errores.toString();
        }

        // 2. Usuario existe
        var usuario = usuarioRepo.obtenerPorId(form.getIdUsuario());
        if (usuario == null) return "Error: Usuario no encontrado";

        // 3. Juego en biblioteca
        if (!bibliotecaRepo.tieneJuego(form.getIdUsuario(), form.getIdJuego())) {
            return "Error: El usuario no posee este juego";
        }

        // 4. Reseña duplicada
        if (resenhaRepo.existeResena(form.getIdUsuario(), form.getIdJuego())) {
            return "Error: Ya existe una reseña para este juego";
        }

        // 5. Crear reseña
        var resenha = resenhaRepo.crear(form);
        return "Reseña creada exitosamente con ID: " + resenha.getId();
    }


       //ELIMINAR RESEÑA

    public String eliminarResenha(long idResenha, long idUsuario) {
        var resenha = resenhaRepo.obtenerPorUsuarioYJuego(idUsuario, idResenha);
        if (resenha == null) return "Error: Reseña no encontrada o no pertenece al usuario";

        boolean eliminado = resenhaRepo.eliminar(resenha.getId());
        return eliminado ? "Reseña eliminada correctamente" : "Error al eliminar reseña";
    }


       //OCULTAR RESEÑA

    public String ocultarResenha(long idResenha, long idUsuario) {
        var resenha = resenhaRepo.obtenerPorUsuarioYJuego(idUsuario, idResenha);
        if (resenha == null) return "Error: Reseña no encontrada o no pertenece al usuario";

        // Marcamos cuerpo como oculto (ejemplo simple)
        resenha.setEstadoResenhaType(EstadoResenhaType.OCULTA);
        return "Reseña ocultada correctamente";
    }

    /* =========================================
       4️⃣ VER RESEÑAS DE UN JUEGO
    ========================================= */
    public List<ResenhaEntidad> verResenasPorJuego(long idJuego, String filtro, String orden) {
        List<ResenhaEntidad> resultado = new ArrayList<>();
        for (ResenhaEntidad r : resenhaRepo.obtenerTodas()) {
            if (r.getNombreJuegoId() == idJuego) {

                // Filtro: positivas o negativas
                if ("positivas".equalsIgnoreCase(filtro) && !r.isRecomendado()) continue;
                if ("negativas".equalsIgnoreCase(filtro) && r.isRecomendado()) continue;

                resultado.add(r);
            }
        }

        // Ordenar
        if ("recientes".equalsIgnoreCase(orden)) {
            resultado.sort(Comparator.comparing(ResenhaEntidad::getFechaPublicacion).reversed());
        }
        // Orden por "útiles" se podría agregar si existiera contador de votos

        return resultado;
    }

    /* =========================================
       5️⃣ VER RESEÑAS DE UN USUARIO
    ========================================= */
    public List<ResenhaEntidad> verResenasPorUsuario(long idUsuario, String filtroEstado) {
        List<ResenhaEntidad> resultado = new ArrayList<>();
        for (ResenhaEntidad r : resenhaRepo.obtenerTodas()) {
            if (r.getUsuaroId() == idUsuario) {
                // Filtro de estado opcional: si quisiéramos oculto, eliminado, etc.
                if (filtroEstado != null && filtroEstado.equalsIgnoreCase("oculto") &&
                        !r.getEstadoResenhaType().equals(EstadoResenhaType.PUBLICADA)) continue;

                resultado.add(r);
            }
        }

        // Orden por fecha descendente
        resultado.sort(Comparator.comparing(ResenhaEntidad::getFechaPublicacion).reversed());

        return resultado;
    }
}