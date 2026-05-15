package controlador;

import excepciones.ValidationException;
import mapper.JuegoMapper;
import modelo.dto.JuegoDto;
import modelo.entidad.*;
import modelo.form.*;
import repositorio.interfaz.IJuegoRepo;
import transaction.ITransactionManager;

import java.util.*;


public class JuegoControlador {

    private final IJuegoRepo repo;
    public ITransactionManager transactionManager;

    public JuegoControlador(IJuegoRepo repo, ITransactionManager transactionManager) {
        this.repo = repo;
        this.transactionManager = transactionManager;
    }


    //AÑADIR JUEGO AL CATÁLOGO

    public JuegoDto anadirJuego(JuegoForm form) throws ValidationException {

        var errores = form.validarJuego();

        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }
        var juegoCreado = transactionManager.inTransaction(() -> {
            if (repo.obtenerTodos().stream().anyMatch(j -> j.getTitulo().equals(form.getTitulo()))) {
                errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));
                throw new ValidationException(errores);
            }
            return repo.crear(form).orElse(null);
        });

        return JuegoMapper.toDTO(juegoCreado);
    }


    //BUSCAR JUEGOS


    public List<JuegoDto> buscar(
            String texto,
            CategoriaType categoria,
            Double precioMin,
            Double precioMax,
            ClasificacionType clasificacion,
            EstadoJuegoType estado
    ) throws ValidationException {


        var resultados = transactionManager.inTransaction(() -> {
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
        });
        return resultados;
    }


    //CONSULTAR CATÁLOGO COMPLETO (PAGINADO)

    public List<JuegoDto> catalogoCompleto(int orden) throws ValidationException {

        List<JuegoDto> resultado = transactionManager.inTransaction(() -> {
            List<JuegoEntidad> juegosArray = repo.obtenerTodos();


            List<JuegoDto> resultados = new ArrayList<>();
            for (JuegoEntidad j : juegosArray) {
                if (j != null) {
                    resultados.add(JuegoMapper.toDTO(j));
                }
            }
            return resultados;
        });


        // ORDEN


        switch (orden) {

            //alfabeticamente
            case 1:
                return resultado.stream().sorted(Comparator.comparing(JuegoDto::getTitulo)).toList();


            //precio
            case 2:
                return resultado.stream().sorted(Comparator.comparing(JuegoDto::getPrecioBase)).toList();


            //fecha
            case 3:
                return resultado.stream().sorted(Comparator.comparing(JuegoDto::getFechaLanzamiento)).toList();
            default:
                return resultado;
        }


    }


    //CONSULTAR DETALLES DE JUEGO

    public JuegoDto detallesJuego(Long id) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        var juego = transactionManager.inTransaction(() -> repo.obtenerPorId(id).orElse(null));


        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        return JuegoMapper.toDTO(juego);
    }


    //APLICAR DESCUENTO

    public Double aplicarDescuento(Long id) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();

        //var juego = transactionManager.inTransaction(() -> repo.obtenerPorId(id).orElse(null));
        try {
            var juego = transactionManager.inTransaction(() -> repo.obtenerPorId(id).orElse(null));
            return juego.getPrecioBase() * (juego.getProcentajeDescuento() / 100);
        } catch (NullPointerException e) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

/**
 if (juego == null) {
 errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
 throw new ValidationException(errores);
 }*/

       // return juego.getPrecioBase() * (juego.getProcentajeDescuento() / 100);


    }


    //CAMBIAR ESTADO DEL JUEGO

    public JuegoDto cambiarEstado(Long id, EstadoJuegoType nuevoEstado) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        try{

            var juego = transactionManager.inTransaction(() -> repo.obtenerPorId(id).orElse(null));



            if (juego == null) {
                errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
                throw new ValidationException(errores);
            }

            juego.setEstadoJuegoType(nuevoEstado);

            var actualizado = transactionManager.inTransaction(() -> repo.actualizar(juego.getId()
                    , new JuegoForm(juego.getTitulo()
                            , juego.getEstadoJuegoType())).orElse(null));


            if (actualizado == null) {
                errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
                throw new ValidationException(errores);
            }


            return JuegoMapper.toDTO(actualizado);
        }
        catch (NullPointerException e){
            errores.add(new ErrorDto("juego",ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
    }

    public JuegoDto ActualizarPorcentajeDescuento(Long id, int nuevoPrecio) throws ValidationException {
        var juego = transactionManager.inTransaction(() -> repo.obtenerPorId(id).orElse(null));


        List<ErrorDto> errores = new ArrayList<>();
        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        juego.setProcentajeDescuento(nuevoPrecio);

        var actualizado = transactionManager.inTransaction(() -> repo.actualizar(juego.getId(),
                new JuegoForm(juego.getTitulo(), juego.getProcentajeDescuento())).orElse(null));

        return JuegoMapper.toDTO(actualizado);

    }
}