package controlador;

import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.InstalacionType;
import repositorio.interfaz.IBibliotecaRepo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BibliotecaControlador {

    private final IBibliotecaRepo repo;

    public BibliotecaControlador(IBibliotecaRepo repo) {
        this.repo = repo;
    }


    // VER BIBLIOTECA PERSONAL

    public List<BibliotecaEntidad> verBiblioteca(Long usuarioId, String orden) {

        var lista = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId)
                .collect(Collectors.toList());

        if (orden != null) {
            switch (orden.toLowerCase()) {
                case "alfabetico" ->
                        lista.sort(Comparator.comparing(b -> b.getIdJuego()));

                case "tiempo" ->
                        lista.sort(Comparator.comparing(BibliotecaEntidad::getHorasJugadas).reversed());

                case "ultima" ->
                        lista.sort(Comparator.comparing(BibliotecaEntidad::getJugadoPorUltimavez,
                                Comparator.nullsLast(Comparator.reverseOrder())));

                case "fecha" ->
                        lista.sort(Comparator.comparing(BibliotecaEntidad::getFechaAdquisicion));
            }
        }

        return lista;
    }


       // AÑADIR JUEGO

    public String agregarJuego(Long usuarioId, Long juegoId) {

        boolean duplicado = repo.obtenerTodos().stream()
                .anyMatch(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId);

        if (duplicado) {
            return "El juego ya existe en la biblioteca";
        }

        var entidad = new BibliotecaEntidad(
                new Random().nextLong(),
                usuarioId,
                juegoId,
                new Date(),
                0,
                null,
                InstalacionType.NO_INSTALADO
        );

        repo.obtenerTodos().add(entidad);

        return "Juego añadido correctamente";
    }


     // ELIMINAR JUEGO

    public String eliminarJuego(Long usuarioId, Long juegoId) {

        var biblioteca = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId)
                .findFirst();

        if (biblioteca.isEmpty()) {
            return "El juego no existe en la biblioteca";
        }

        repo.eliminar(biblioteca.get().getId());

        return "Juego eliminado correctamente";
    }


       // ACTUALIZAR TIEMPO DE JUEGO

    public String actualizarHoras(Long usuarioId, Long juegoId, int horas) {

        if (horas <= 0) {
            return "Las horas deben ser positivas";
        }

        var biblioteca = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId)
                .findFirst();

        if (biblioteca.isEmpty()) {
            return "Juego no encontrado en biblioteca";
        }

        var entidad = biblioteca.get();
        entidad.setHorasJugadas(entidad.getHorasJugadas() + horas);
        entidad.setJugadoPorUltimavez(new Date());

        return "Nuevo tiempo total: " + entidad.getHorasJugadas() + " horas";
    }


    // CONSULTAR ÚLTIMA SESIÓN

    public String consultarUltimaSesion(Long usuarioId, Long juegoId) {

        var biblioteca = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId)
                .findFirst();

        if (biblioteca.isEmpty()) {
            return "Juego no encontrado";
        }

        var fecha = biblioteca.get().getJugadoPorUltimavez();

        if (fecha == null) {
            return "Nunca jugado";
        }

        long diferencia = System.currentTimeMillis() - fecha.getTime();
        long dias = diferencia / (1000 * 60 * 60 * 24);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return "Última sesión: hace " + dias +
                " días (" + formato.format(fecha) + ")";
    }


       // ESTADÍSTICAS

    public Map<String, Object> estadisticas(Long usuarioId) {

        var lista = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId)
                .collect(Collectors.toList());

        int totalJuegos = lista.size();
        int horasTotales = lista.stream()
                .mapToInt(BibliotecaEntidad::getHorasJugadas)
                .sum();

        long instalados = lista.stream()
                .filter(b -> b.getInstalacionType() == InstalacionType.INSTALADO)
                .count();

        var masJugado = lista.stream()
                .max(Comparator.comparing(BibliotecaEntidad::getHorasJugadas))
                .orElse(null);

        long nuncaJugados = lista.stream()
                .filter(b -> b.getHorasJugadas() == 0)
                .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("Total juegos", totalJuegos);
        stats.put("Horas totales", horasTotales);
        stats.put("Juegos instalados", instalados);
        stats.put("Juego más jugado", masJugado);
        stats.put("Nunca jugados", nuncaJugados);

        return stats;
    }
}