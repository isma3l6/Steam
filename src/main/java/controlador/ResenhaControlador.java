package controlador;

import modelo.dto.ResenhaDto;
import modelo.entidad.EstadoResenhaType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.ResenhaEntidad;
import modelo.entidad.UsuarioEntidad;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.ResenhaRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;
import repositorio.interfaz.IResenha;

import java.util.List;
import java.util.stream.Collectors;

public class ResenhaControlador {

    private ResenhaRepoInMemory resenhaRepo;
    private UsuarioRepoInMemory usuarioRepo;
    private JuegoRepoInMemory juegoRepo;

    public ReseñaControlador(ResenhaRepoInMemory reseñaRepo,
                            UsuarioRepoInMemory usuarioRepo,
                            JuegoRepoInMemory juegoRepo) {
        this.resenhaRepo = resenhaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
    }

    // 🔹 Escribir reseña
    public String escribirReseña(Long usuarioId, Long juegoId,
                                 boolean recomendado,
                                 String texto) {

        UsuarioEntidad usuario = usuarioRepo.buscarPorId(usuarioId);
        JuegoEntidad juego = juegoRepo.buscarPorId(juegoId);

        if (usuario==null || juego==null)
            return "Usuario o juego no encontrado";

        ResenhaEntidad resenha = new ResenhaEntidad(null,
                usuario, juego,
                recomendado, texto, 0);

        resenhaRepo.crearReseña(resenha);

        return "Reseña creada con ID: " + resenha.getId();
    }

    // 🔹 Ver reseñas de juego
    public List<ResenhaEntidad> verReseñasJuego(Long juegoId) {

        return resenhaRepo.listarTodas().stream()
                .filter(r -> r.getJuego().getId().equals(juegoId)
                        && r.getEstado() == EstadoResenhaType.PUBLICADA)
                .collect(Collectors.toList());
    }

    // 🔹 Eliminar reseña
    public String eliminarReseña(Long reseñaId) {

        ResenhaEntidad resenha = resenhaRepo.buscarPorId(resenhaId);

        if (resenha==null)
            return "Reseña no encontrada";

        resenha.get().setEstado(EstadoResenhaType.ELIMINADA);

        return "Reseña eliminada";
    }

}
