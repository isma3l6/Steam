package repositorio.inmemory;

import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.InstalacionType;
import modelo.form.BibliotecaForm;
import repositorio.interfaz.IBibliotecaRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BibliotecaRepoInMemory implements IBibliotecaRepo {

    private static final List<BibliotecaEntidad> bibliotecas = new ArrayList<>();
    private static Long idCounter = 1L;

    @Override
    public Optional<BibliotecaEntidad> crear(BibliotecaForm form) {

        var biblioteca = new BibliotecaEntidad(
                idCounter++,
                form.getIdUsario(),
                form.getIdJuego(),
                form.getFechaAdquisicion(),
                (int) form.getTiempoJugado(),
                new Date(),
                InstalacionType.INSTALADO
        );

        bibliotecas.add(biblioteca);

        return Optional.of(biblioteca);
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorId(Long id) {
        return bibliotecas.stream()
                .filter(b -> b.getId() == id)
                .findFirst();
    }

    @Override
    public List<BibliotecaEntidad> obtenerTodos() {
        return new ArrayList<>(bibliotecas);
    }

    @Override
    public Optional<BibliotecaEntidad> actualizar(Long id, BibliotecaForm form) {

        var bibliotecaOpt = obtenerPorId(id);

        if (bibliotecaOpt.isEmpty()) {
            throw new IllegalArgumentException("Biblioteca no encontrada");
        }

        var bibliotecaActualizada = new BibliotecaEntidad(
                id,
                form.getIdUsario(),
                form.getIdJuego(),
                form.getFechaAdquisicion(),
                (int) form.getTiempoJugado(),
                new Date(),
                InstalacionType.INSTALADO
        );

        bibliotecas.removeIf(b -> b.getId() == id);
        bibliotecas.add(bibliotecaActualizada);

        return Optional.of(bibliotecaActualizada);
    }

    @Override
    public boolean eliminar(Long id) {
        return bibliotecas.removeIf(b -> b.getId() == id);
    }

    public boolean tieneJuego(long idUsuario, long idJuego) {

        return bibliotecas.stream()
                .anyMatch(registro -> registro.getIdUsuario() == idUsuario && registro.getIdJuego() == idJuego
                );
    }
}

