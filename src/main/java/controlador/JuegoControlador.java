package controlador;

import excepciones.ValidationException;
import mapper.JuegoMapper;
import modelo.dto.JuegoDto;
import modelo.entidad.*;
import modelo.form.*;
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

    public JuegoDto anadirJuego(JuegoForm form) throws ValidationException {

        var errores = form.validarJuego();

        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }
        if(repo.obtenerTodos().stream().anyMatch(j->j.getTitulo().equals(form.getTitulo()))) {
            errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));
            throw new ValidationException(errores);
        }
        JuegoEntidad juego = repo.crear(form).get();


        JuegoEntidad juegoAnadido = repo.obtenerPorId(juego.getId()).get();


        return JuegoMapper.toDTO(juegoAnadido);
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

        List<JuegoEntidad> juegos = repo.obtenerTodos();
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

    public List<JuegoDto> catalogoCompleto(int orden) {

       List<JuegoEntidad> juegosArray = repo.obtenerTodos();


        List<JuegoDto> resultados = new ArrayList<>();
        for (JuegoEntidad j : juegosArray) {
            if (j != null) {
                resultados.add(JuegoMapper.toDTO(j));
            }
        }


        // ORDEN



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

    public JuegoDto detallesJuego(Long id) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();

        JuegoEntidad juego = repo.obtenerPorId(id).orElse(null);

        if (juego ==null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        return JuegoMapper.toDTO(juego);
    }


    //APLICAR DESCUENTO

    public Double aplicarDescuento(Long id) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();

        JuegoEntidad juego = repo.obtenerPorId(id).orElse(null);

        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }



        return  juego.getPrecioBase() * (juego.getProcentajeDescuento() / 100);


    }


    //CAMBIAR ESTADO DEL JUEGO

    public JuegoDto cambiarEstado(Long id, EstadoJuegoType nuevoEstado) throws ValidationException {

        JuegoEntidad juego = repo.obtenerPorId(id).orElse(null);
        List<ErrorDto> errores = new ArrayList<>();
        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        juego.setEstadoJuegoType(nuevoEstado);
        var actualizado = repo.actualizar(juego.getId(), new JuegoForm(juego.getTitulo(), juego.getEstadoJuegoType())).get();

        return JuegoMapper.toDTO(actualizado);
    }
    public JuegoDto ActualizarPorcentajeDescuento(Long id, int nuevoPrecio) throws ValidationException {
        JuegoEntidad juego = repo.obtenerPorId(id).orElse(null);
        List<ErrorDto> errores = new ArrayList<>();
        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        juego.setProcentajeDescuento(nuevoPrecio);

        var actualizado = repo.actualizar(juego.getId(), new JuegoForm(juego.getTitulo(), juego.getProcentajeDescuento())).get();

        return JuegoMapper.toDTO(actualizado);

    }
}