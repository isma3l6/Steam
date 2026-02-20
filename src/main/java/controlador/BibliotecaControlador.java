package controlador;

import modelo.dto.BibliotecaDto;
import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;
import modelo.form.BibliotecaForm;
import repositorio.inmemory.BibiliotecaRepoInMemory;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;
import repositorio.interfaz.IBiblioteca;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class BibliotecaControlador  {
    private BibiliotecaRepoInMemory bibliotecaRepo;
    private UsuarioRepoInMemory usuarioRepo;
    private JuegoRepoInMemory juegoRepo;

    public BibliotecaController(BibiliotecaRepoInMemory bibliotecaRepo,
                                UsuarioRepoInMemory usuarioRepo,
                                JuegoRepoInMemory juegoRepo) {
        this.bibliotecaRepo = bibliotecaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
    }

    // 🔹 Añadir juego a biblioteca
    public String añadirJuego(Long usuarioId, Long juegoId) {

        UsuarioEntidad usuario = usuarioRepo.buscarPorId(usuarioId);
        JuegoEntidad juego = juegoRepo.buscarPorId(juegoId);

        if (usuario==null || juego==null)
            return "Usuario o Juego no encontrado";

        BibliotecaForm entrada = new BibliotecaForm(null, usuario.get(), juego.get());
        bibliotecaRepo.adquirirJuego(entrada);

        return "Juego añadido a biblioteca";
    }

    // 🔹 Ver biblioteca
    public List<BibliotecaEntidad> verBiblioteca(Long usuarioId) {

        return bibliotecaRepo.listarTodos().stream()
                .filter(b -> b.getUsuario().getId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    // 🔹 Actualizar tiempo de juego
    public String actualizarTiempo(Long usuarioId, Long juegoId, double horas) {

        BibliotecaEntidad entrada = bibliotecaRepo.buscar(usuarioId, juegoId);

        if (entrada==null)
            return "Entrada no encontrada";

        if (horas <= 0)
            return "Horas inválidas";

        entrada.setTiempoJuegoTotal(b.getTiempoJuegoTotal() + horas);
        entrada.setUltimaFechaJuego(LocalDateTime.now());

        return "Tiempo total: " + entrada.getTiempoJuegoTotal();
    }
}
