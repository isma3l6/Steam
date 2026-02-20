package repositorio.inmemory;

import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.InstalacionType;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BibiliotecaRepoInMemory {
    private List<BibliotecaEntidad> biblioteca = new ArrayList<>();
    private Long contadorId = 1L;

    // CREATE (comprar juego)
    public BibliotecaEntidad adquirirJuego(UsuarioEntidad usuario, JuegoEntidad juego) {

        BibliotecaEntidad nuevo = new BibliotecaEntidad(
                contadorId++,
                usuario,
                juego,
                LocalDate.now(),
                0,
                null,
                EstadoInstalacion.NO_INSTALADO
        );

        biblioteca.add(nuevo);
        return nuevo;
    }

    // READ BY ID
    public BibliotecaEntidad buscarPorId(Long id) {
        return biblioteca.stream()
                .filter(j -> j.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // READ ALL
    public List<BibliotecaEntidad> listarTodos() {
        return biblioteca;
    }

    // READ por usuario
    public List<BibliotecaEntidad> listarPorUsuario(UsuarioForm usuario) {
        return biblioteca.stream()
                .filter(j -> j.getUsuario().getId().equals(usuario.getId()))
                .collect(Collectors.toList());
    }

    // UPDATE horas jugadas
    public void registrarHoras(Long id, double horas) {
        BibliotecaEntidad juego = buscarPorId(id);
        if (juego != null) {
            juego.setHorasJugadas(juego.getHorasJugadas() + horas);
        } else {
            throw new RuntimeException("Juego no encontrado en biblioteca");
        }
    }

    // UPDATE estado instalación
    public void cambiarEstadoInstalacion(Long id, InstalacionType estado) {
        BibliotecaEntidad juego = buscarPorId(id);
        if (juego != null) {
            juego.setEstadoInstalacion(estado);
        }
    }

    // DELETE (eliminar de biblioteca)
    public void eliminar(Long id) {
        biblioteca.removeIf(j -> j.getId().equals(id));
    }
}
