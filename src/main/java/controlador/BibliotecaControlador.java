package controlador;

import excepciones.ValidationException;
import mapper.BibliotecaMapper;
import modelo.dto.BibliotecaDto;
import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.InstalacionType;
import modelo.form.BibliotecaForm;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
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

    public List<BibliotecaDto> verBiblioteca(Long usuarioId, String orden) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        List<BibliotecaDto> resultado = new ArrayList<>();
        var lista = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId)
                .toList();

        if (lista.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);

        }
        for (BibliotecaEntidad b : lista) {
            resultado.add(BibliotecaMapper.toDTO(b));
        }


        switch (orden.toLowerCase()) {
            case "alfabetico":
                return resultado.stream().sorted(Comparator.comparing(b -> b.getIdJuego())).toList();

            case "tiempo":
                return resultado.stream().sorted(Comparator.comparing(BibliotecaDto::getHorasJugadas).reversed()).toList();

            case "ultima":
                return resultado.stream().sorted(Comparator.comparing(BibliotecaDto::getJugadoPorUltimavez,
                        Comparator.nullsLast(Comparator.reverseOrder()))).toList();

            case "fecha":
                return resultado.stream().sorted(Comparator.comparing(BibliotecaDto::getFechaAdquisicion)).toList();

            default:
                return resultado.stream().toList();
        }


    }

    // AÑADIR JUEGO

    public BibliotecaDto agregarJuego(Long usuarioId, Long juegoId) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        boolean duplicado = repo.obtenerTodos().stream()
                .anyMatch(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId);

        if (duplicado) {
            errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));
            throw new ValidationException(errores);
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

        boolean resultado = repo.obtenerTodos().add(entidad);
        if (resultado) {
            return BibliotecaMapper.toDTO(entidad);
        } else {
            errores.add(new ErrorDto("Servidor", ErrorType.ERROR_EN_BASE));
            throw new ValidationException(errores);
        }
    }


    // ELIMINAR JUEGO

    public Boolean eliminarJuego(Long usuarioId, Long juegoId) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        var biblioteca = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId)
                .findFirst();

        if (biblioteca.isEmpty()) {
            errores.add(new ErrorDto("biblioteca", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        var a = repo.eliminar(biblioteca.get().getId());

        if (a) {
            return true;
        } else {
            return false;
        }
    }


    // ACTUALIZAR TIEMPO DE JUEGO

    public BibliotecaDto actualizarHoras(Long usuarioId, Long juegoId, int horas) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        if (horas <= 0) {
            errores.add(new ErrorDto("horas", ErrorType.VALOR_DEMASIADO_BAJO));
            throw new ValidationException(errores);
        }

        var biblioteca = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId)
                .findFirst();

        if (biblioteca.isEmpty()) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
        }

        var e = biblioteca.get();
        e.setHorasJugadas(e.getHorasJugadas() + horas);
        e.setJugadoPorUltimavez(new Date());
       var resultado =repo.actualizar(e.getId(), new BibliotecaForm(e.getId(), e.getIdUsuario(), e.getIdJuego(), e.getFechaAdquisicion(), e.getHorasJugadas()));

        return BibliotecaMapper.toDTO(resultado.get());
    }


    // CONSULTAR ÚLTIMA SESIÓN

    public BibliotecaDto consultarUltimaSesion(Long usuarioId, Long juegoId) throws ValidationException{
List<ErrorDto>errores=new ArrayList<>();
        var biblioteca = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId)
                .findFirst();

        if (biblioteca.isEmpty()) {
            errores.add(new ErrorDto("Biblioteca",ErrorType.NO_ENCONTRADO));
throw new ValidationException(errores);       }

        var fecha = biblioteca.get().getJugadoPorUltimavez();

        if (fecha == null) {
            errores.add(new ErrorDto("fecha",ErrorType.NO_ENCONTRADO));
          throw new ValidationException(errores);
        }

        return BibliotecaMapper.toDTO(biblioteca.get());
    }


    // ESTADÍSTICAS

    public List<BibliotecaDto> estadisticas(Long usuarioId) {
List<BibliotecaDto>bibliotecaDtos=new ArrayList<>();
        var lista = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId)
                .toList();
        for (BibliotecaEntidad b: lista){
            if (b!=null){
                bibliotecaDtos.add(BibliotecaMapper.toDTO(b));
            }
        }

        return bibliotecaDtos;

    }
}