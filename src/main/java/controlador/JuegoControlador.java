package controlador;

import modelo.entidad.*;
import modelo.form.JuegoForm;
import repositorio.interfaz.IJuegoRepo;

import java.text.DecimalFormat;
import java.util.*;

public class JuegoControlador {

    private final IJuegoRepo repo;

    public JuegoControlador(IJuegoRepo repo) {
        this.repo = repo;
    }


      //AÑADIR JUEGO AL CATÁLOGO

    public Object añadirJuego(JuegoForm form) {

        var errores = form.validarJuego();

        if (!errores.isEmpty()) {
            return errores;
        }

        JuegoEntidad juego = repo.crear(form);

        return "Juego creado exitosamente con ID: " + juego.getId();
    }


       //BUSCAR JUEGOS

    public List<Map<String, Object>> buscar(
            String texto,
            CategoriaType categoria,
            Double precioMin,
            Double precioMax,
            ClasificacionType clasificacion,
            EstadoJuegoType estado
    ) {

        JuegoEntidad[] juegos = repo.obtenerTodos();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (JuegoEntidad j : juegos) {

            if (j == null) continue;

            if (texto != null &&
                    !j.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                continue;
            }

            if (categoria != null &&
                    j.getCategoriaType() != categoria) {
                continue;
            }

            if (precioMin != null &&
                    j.getPrecioBase() < precioMin) {
                continue;
            }

            if (precioMax != null &&
                    j.getPrecioBase() > precioMax) {
                continue;
            }

            if (clasificacion != null &&
                    j.getClasificacionType() != clasificacion) {
                continue;
            }

            if (estado != null &&
                    j.getEstadoJuegoType() != estado) {
                continue;
            }

            Map<String, Object> resumen = new HashMap<>();
            resumen.put("ID", j.getId());
            resumen.put("Título", j.getTitulo());
            resumen.put("Desarrollador", j.getDesarrollador());
            resumen.put("Precio Base", j.getPrecioBase());
            resumen.put("Descuento", j.getProcentajeDescuento());
            resumen.put("Clasificación", j.getClasificacionType());
            resumen.put("Imagen", "imagen_placeholder.jpg");

            resultado.add(resumen);
        }

        return resultado;
    }


       //CONSULTAR CATÁLOGO COMPLETO (PAGINADO)

    public Map<String, Object> catalogoCompleto(String orden,
                                                int pagina,
                                                int tamañoPagina) {

        JuegoEntidad[] juegosArray = repo.obtenerTodos();
        List<JuegoEntidad> juegos = new ArrayList<>();

        for (JuegoEntidad j : juegosArray) {
            if (j != null) {
                juegos.add(j);
            }
        }

        // ORDEN
        if (orden != null) {
            switch (orden.toLowerCase()) {
                case "alfabetico":
                    juegos.sort(Comparator.comparing(JuegoEntidad::getTitulo));
                    break;

                case "precio":
                    juegos.sort(Comparator.comparing(JuegoEntidad::getPrecioBase));
                    break;

                case "fecha":
                    juegos.sort(Comparator.comparing(JuegoEntidad::getFechaLanzamiento));
                    break;
            }
        }

        int total = juegos.size();
        int desde = pagina * tamañoPagina;
        int hasta = Math.min(desde + tamañoPagina, total);

        List<JuegoEntidad> paginaContenido =
                (desde < total) ? juegos.subList(desde, hasta)
                        : new ArrayList<>();

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("pagina", pagina);
        resultado.put("tamañoPagina", tamañoPagina);
        resultado.put("totalJuegos", total);
        resultado.put("contenido", paginaContenido);

        return resultado;
    }


      //CONSULTAR DETALLES DE JUEGO

    public Object detallesJuego(int id) {

        JuegoEntidad juego = repo.obtenerPorId(id);

        if (juego == null) {
            return "Juego no encontrado";
        }

        Map<String, Object> detalles = new HashMap<>();
        detalles.put("ID", juego.getId());
        detalles.put("Título", juego.getTitulo());
        detalles.put("Descripción", juego.getDescripcion());
        detalles.put("Desarrollador", juego.getDesarrollador());
        detalles.put("Fecha Lanzamiento", juego.getFechaLanzamiento());
        detalles.put("Precio Base", juego.getPrecioBase());
        detalles.put("Descuento", juego.getProcentajeDescuento());
        detalles.put("Categoría", juego.getCategoriaType());
        detalles.put("Clasificación", juego.getClasificacionType());
        detalles.put("Estado", juego.getEstadoJuegoType());

        // Simulación estadísticas
        detalles.put("Ventas totales", new Random().nextInt(10000));
        detalles.put("Valoración media", 4.5);
        detalles.put("Reseñas destacadas", "Muy positivo");

        return detalles;
    }


     //APLICAR DESCUENTO

    public String aplicarDescuento(int id, double porcentaje) {

        if (porcentaje < 0 || porcentaje > 100) {
            return "El descuento debe estar entre 0 y 100";
        }

        JuegoEntidad juego = repo.obtenerPorId(id);

        if (juego == null) {
            return "Juego no encontrado";
        }

        juego.setProcentajeDescuento(porcentaje);

        double precioFinal =
                juego.getPrecioBase() * (1 - porcentaje / 100);

        DecimalFormat df = new DecimalFormat("#0.00");

        return "Precio final actualizado: " + df.format(precioFinal) + " €";
    }


       //CAMBIAR ESTADO DEL JUEGO

    public String cambiarEstado(int id, EstadoJuegoType nuevoEstado) {

        JuegoEntidad juego = repo.obtenerPorId(id);

        if (juego == null) {
            return "Juego no encontrado";
        }

        juego.setEstadoJuegoType(nuevoEstado);

        return "Estado actualizado a: " + nuevoEstado;
    }
}