package repositorio.inmemory;

import modelo.dto.JuegoDto;
import modelo.entidad.EstadoJuegoType;
import modelo.entidad.JuegoEntidad;
import modelo.form.JuegoForm;
import repositorio.interfaz.IJuego;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JuegoRepoInMemory  {

    private static  final List<JuegoEntidad> juegos=new ArrayList<>();
    private static Long contadorId=1L;


    // CREATE
    public JuegoEntidad crear(JuegoForm juego) {

        JuegoEntidad j = new JuegoEntidad(
                contadorId++,
                juego.getTitulo(),
                juego.getDescripcion(),
                juego.getDesarrollador(),
                juego.getFechaLanzamiento(),
                juego.getPrecioBase(),
                juego.getDescuento(),
                juego.getCategoria(),
                juego.getClasificacionEdad(),
                juego.getIdiomasDisponibles(),
                juego.getEstado()
        );

        juegos.add(j);
        return j;
    }

    // READ BY ID
    public JuegoEntidad buscarPorId(Long id) {
        return juegos.stream()
                .filter(j -> j.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // READ ALL
    public List<JuegoEntidad> listarTodos() {
        return juegos;
    }

    // UPDATE
    public void actualizar(Long id, JuegoForm nuevosDatos) {
        JuegoEntidad juego = buscarPorId(id);

        if (juego != null) {
            juego.setTitulo(nuevosDatos.getTitulo());
            juego.setDescripcion(nuevosDatos.getDescripcion());
            juego.setDesarrollador(nuevosDatos.getDesarrollador());
            juego.setPrecioBase(nuevosDatos.getPrecioBase());
            juego.setProcentajeDescuento(nuevosDatos.getDescuento());
            juego.setCategoriaType(nuevosDatos.getCategoria());
            juego.setClasificacionType(nuevosDatos.getClasificacionEdad());
            juego.setIdiomas(nuevosDatos.getIdiomasDisponibles());
            juego.setEstadoJuegoType(nuevosDatos.getEstado());
        } else {
            throw new RuntimeException("Juego no encontrado");
        }
    }

    // DELETE
    public void eliminar(Long id) {
        juegos.removeIf(j -> j.getId().equals(id));
    }
}
