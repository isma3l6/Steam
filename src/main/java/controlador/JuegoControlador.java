package controlador;

import excepciones.ValidationException;
import mapper.JuegoMapper;
import modelo.dto.JuegoDto;
import modelo.entidad.*;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import modelo.form.JuegoForm;
import repositorio.interfaz.IJuegoRepo;

import java.text.DecimalFormat;
import java.util.*;

import static java.util.Arrays.sort;

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
    //Añadir formBusquda

    public List<JuegoDto> buscar(
            String texto,
            CategoriaType categoria,
            Double precioMin,
            Double precioMax,
            ClasificacionType clasificacion,
            EstadoJuegoType estado
    ) {

        JuegoEntidad[] juegos = repo.obtenerTodos();
        List<JuegoDto> resultado = new ArrayList<>();

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
            resultado.add(JuegoMapper.toDTO(j));
        }

        return resultado;
    }


    //CONSULTAR CATÁLOGO COMPLETO (PAGINADO)

    public List<JuegoDto> catalogoCompleto(int orden,
                                           int pagina,
                                           int tamanoPagina) {

        JuegoEntidad[] juegosArray = repo.obtenerTodos();


        List<JuegoDto> resultados = new ArrayList<>();
        for (JuegoEntidad j : juegosArray) {
            if (j != null) {
                resultados.add(JuegoMapper.toDTO(j));
            }
        }


        // ORDEN

        // Pelea futura ;)

        switch (orden) {

            //alfabeticamente
            case 1:
                return resultados.stream().sorted(Comparator.comparing(JuegoDto::getTitulo)).toList();



            //precio
            case 2:
                return resultados.stream().sorted(Comparator.comparing(JuegoDto::getPrecioBase)).toList();


            //fecha
            case 3:
                return resultados.stream().sorted(Comparator.comparing(JuegoDto::getFechaLanzamiento)).toList();
            default:
                return resultados;
        }


    }


    //CONSULTAR DETALLES DE JUEGO

    public JuegoDto detallesJuego(int id) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();

        JuegoEntidad juego = repo.obtenerPorId(id);

        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        return JuegoMapper.toDTO(juego);
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