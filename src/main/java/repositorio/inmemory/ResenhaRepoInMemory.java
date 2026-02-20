package repositorio.inmemory;

import modelo.entidad.EstadoResenhaType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.ResenhaEntidad;
import modelo.entidad.UsuarioEntidad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResenhaRepoInMemory {

    private List<ResenhaEntidad> reseñas = new ArrayList<>();
    private Long contadorId = 1L;

    // CREATE
    public ResenhaEntidad crearReseña(UsuarioEntidad usuario,
                                       JuegoEntidad juego,
                                       boolean recomendado,
                                       String texto,
                                       double horasJugadas) {

        ResenhaEntidad nueva = new ResenhaEntidad(
                contadorId++,
                usuario.getId(),
                juego.getId(),
                recomendado,
                texto,
                horasJugadas,
                EstadoResenhaType.PUBLICADA
        );

        reseñas.add(nueva);
        return nueva;
    }

    // READ BY ID
    public ResenhaEntidad buscarPorId(Long id) {
        return reseñas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // READ ALL
    public List<ResenhaEntidad> listarTodas() {
        return reseñas;
    }

    // READ por usuario
    public List<ResenhaEntidad> listarPorUsuario(UsuarioEntidad usuario) {
        return reseñas.stream()
                .filter(r -> r.getUsuario().getId().equals(usuario.getId()))
                .collect(Collectors.toList());
    }

    // READ por juego
    public List<ResenhaEntidad> listarPorJuego(JuegoEntidad juego) {
        return reseñas.stream()
                .filter(r -> r.getJuego().getId().equals(juego.getId()))
                .collect(Collectors.toList());
    }

    // UPDATE
    public void editarResenha(Long id, String nuevoTexto, boolean recomendado, double horas) {
        ResenhaEntidad reseña = buscarPorId(id);
        if (resenha != null) {
            resenha.editarResenha(nuevoTexto, recomendado, horas);
        } else {
            throw new RuntimeException("Reseña no encontrada");
        }
    }

    // Cambiar estado (ocultar o eliminar)
    public void cambiarEstado(Long id, EstadoResenhaType estado) {
        ResenhaEntidad reseña = buscarPorId(id);
        if (reseña != null) {
            reseña.setEstadoResenhaType(estado);
        }
    }

    // DELETE físico
    public void eliminar(Long id) {
        reseñas.removeIf(r -> r.getId().equals(id));
    }
}
