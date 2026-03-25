package controlador;

import excepciones.ValidationException;
import modelo.entidad.*;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
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

    public Object anadirJuego(JuegoForm form) {

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

    public Map<String, Object> catalogoCompleto(int orden,
                                                int pagina,
                                                int tamanoPagina) {

        JuegoEntidad[] juegosArray = repo.obtenerTodos();
        List<JuegoEntidad> juegos = new ArrayList<>();

        for (JuegoEntidad j : juegosArray) {
            if (j != null) {
                juegos.add(j);
            }
        }

        // ORDEN

        switch (orden) {
            //alfabeticamente
            case 1:
                juegos.sort(Comparator.comparing(JuegoEntidad::getTitulo));
                break;
            //precio
            case 2:
                juegos.sort(Comparator.comparing(JuegoEntidad::getPrecioBase));
                break;
            //fecha
            case 3:
                juegos.sort(Comparator.comparing(JuegoEntidad::getFechaLanzamiento));
                break;
        }


        int total = juegos.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, total);

        List<JuegoEntidad> paginaContenido =
                (desde < total) ? juegos.subList(desde, hasta)
                        : new ArrayList<>();

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("pagina", pagina);
        resultado.put("tamañoPagina", tamanoPagina);
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

    public String aplicarDescuento(int id, double porcentaje) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        if (porcentaje < 0 || porcentaje > 100) {
            errores.add(new ErrorDto("porcentaje", ErrorType.PORCENTAJE_INVALIDO));
            throw new ValidationException(errores);
        }

        JuegoEntidad juego = repo.obtenerPorId(id);

        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        juego.setProcentajeDescuento(porcentaje);

        double precioFinal =
                juego.getPrecioBase() * (1 - porcentaje / 100);

        DecimalFormat df = new DecimalFormat("#0.00");

        return "Precio final actualizado: " + df.format(precioFinal) + " €";
    }


    //CAMBIAR ESTADO DEL JUEGO

    public String cambiarEstado(int id, EstadoJuegoType nuevoEstado) throws ValidationException {

        JuegoEntidad juego = repo.obtenerPorId(id);
        List<ErrorDto> errores = new ArrayList<>();
        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        juego.setEstadoJuegoType(nuevoEstado);

        return "Estado actualizado a: " + nuevoEstado;
    }
}