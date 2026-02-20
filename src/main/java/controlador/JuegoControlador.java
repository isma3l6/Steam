package controlador;

import modelo.dto.JuegoDto;
import modelo.entidad.ClasificacionType;
import modelo.entidad.EstadoJuegoType;
import modelo.entidad.JuegoEntidad;
import modelo.form.JuegoForm;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.interfaz.IJuego;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class JuegoControlador  {
    private JuegoRepoInMemory juegoRepo;

    public JuegoControlador(JuegoRepoInMemory juegoRepo) {
        this.juegoRepo = juegoRepo;
    }

    // 🔹 Añadir juego
    public String añadirJuego(String titulo, String descripcion,
                              String desarrollador,
                              LocalDate fechaLanzamiento,
                              double precioBase) {

        if (titulo == null || titulo.isEmpty())
            return "Título obligatorio";

        if (precioBase <= 0)
            return "Precio inválido";

        JuegoForm juego = new JuegoForm(null, titulo, descripcion,
                desarrollador, fechaLanzamiento,
                precioBase, "General",
                ClasificacionType.PEGI_12, List.of("Español"));

        juegoRepo.crear(juego);
        return "Juego creado con ID: " + juego.getId();
    }

    // 🔹 Buscar juegos
    public List<JuegoEntidad> buscarPorTexto(String texto) {
        return juegoRepo.listarTodos().stream()
                .filter(j -> j.getTitulo().toLowerCase().contains(texto.toLowerCase()))
                .collect(Collectors.toList());
    }

    // 🔹 Aplicar descuento
    public String aplicarDescuento(Long id, double porcentaje) {

       JuegoEntidad juegoOpt = juegoRepo.buscarPorId(id);
        if (juegoOpt==null)
            return "Juego no encontrado";

        if (porcentaje < 0 || porcentaje > 100)
            return "Descuento inválido";

        juegoOpt.setProcentajeDescuento(porcentaje);

        return "Descuento aplicado correctamente";
    }

    // 🔹 Cambiar estado
    public String cambiarEstado(Long id, EstadoJuegoType nuevoEstado) {

        JuegoEntidad juegoOpt = juegoRepo.buscarPorId(id);
        if (juegoOpt==null)
            return "Juego no encontrado";

        juegoOpt.setEstadoJuegoType(nuevoEstado);

        return "Estado actualizado a " + nuevoEstado;
    }
}
