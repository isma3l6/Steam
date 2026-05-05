package controlador;

import excepciones.ValidationException;
import mapper.BibliotecaMapper;
import mapper.JuegoMapper;
import mapper.UsuarioMapper;
import modelo.dto.BibliotecaDto;
import modelo.entidad.BibliotecaEntidad;
import modelo.entidad.InstalacionType;
import modelo.form.BibliotecaForm;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import repositorio.interfaz.IBibliotecaRepo;
import repositorio.interfaz.IJuegoRepo;
import repositorio.interfaz.IUsuarioRepo;

import java.time.LocalDate;
import java.util.*;

public class BibliotecaControlador {

    private final IBibliotecaRepo repo;
    private final IJuegoRepo juegoRepo;

    private final IUsuarioRepo usuarioRepo;


    public BibliotecaControlador(IBibliotecaRepo repo, IJuegoRepo juegoRepo, IUsuarioRepo usuarioRepo) {
        this.repo = repo;
        this.juegoRepo =
                juegoRepo;
        this.usuarioRepo = usuarioRepo;

    }


    // VER BIBLIOTECA PERSONAL

    public List<BibliotecaDto> verBiblioteca(Long usuarioId, String orden) throws ValidationException {

        List<ErrorDto> errores = new ArrayList<>();

        List<BibliotecaDto> resultado = new ArrayList<>();

        var lista = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId)
                .toList();

        var u = usuarioRepo.obtenerPorId(usuarioId).orElse(null);


        if (u == null) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);

        }


        if (!lista.isEmpty()) {
            errores.add(new ErrorDto("id", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);

        }
        for (BibliotecaEntidad b : lista) {
            resultado.add(BibliotecaMapper.toDTO(b, UsuarioMapper.toDTO(u), JuegoMapper.toDTO(juegoRepo.obtenerPorId(b.getIdJuego()).get())));
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
        var duplicado = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId).findFirst().orElse(null);

        if (duplicado != null) {
            errores.add(new ErrorDto("juego", ErrorType.DUPLICADO));
            throw new ValidationException(errores);
        }
        var nuevo=repo.crear(new BibliotecaForm(usuarioId,juegoId,0)).orElse(null);

        if (nuevo == null) {
            errores.add(new ErrorDto("base",ErrorType.ERROR_EN_BASE));
        throw new ValidationException(errores);
        }
        var usuario = UsuarioMapper.toDTO(usuarioRepo.obtenerPorId(usuarioId).get());

        var juego = JuegoMapper.toDTO(juegoRepo.obtenerPorId(juegoId).get());

        if (nuevo!=null) {
            return BibliotecaMapper.toDTO(nuevo, usuario, juego);
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
        e.setJugadoPorUltimavez(LocalDate.now());
        var resultado = repo.actualizar(e.getId(), new BibliotecaForm( e.getIdUsuario(), e.getIdJuego(), e.getHorasJugadas()));
        var usuario = UsuarioMapper.toDTO(usuarioRepo.obtenerPorId(usuarioId).get());
        var juego = JuegoMapper.toDTO(juegoRepo.obtenerPorId(juegoId).get());
        return BibliotecaMapper.toDTO(resultado.get(), usuario, juego);
    }


    // CONSULTAR ÚLTIMA SESIÓN

    public BibliotecaDto consultarUltimaSesion(Long usuarioId, Long juegoId) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        var biblioteca = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId &&
                        b.getIdJuego() == juegoId)
                .findFirst();

        if (biblioteca.isEmpty()) {
            errores.add(new ErrorDto("Biblioteca", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        var fecha = biblioteca.get().getJugadoPorUltimavez();

        if (fecha == null) {
            errores.add(new ErrorDto("fecha", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        var usuario = UsuarioMapper.toDTO(usuarioRepo.obtenerPorId(usuarioId).get());
        var juego = JuegoMapper.toDTO(juegoRepo.obtenerPorId(juegoId).get());
        return BibliotecaMapper.toDTO(biblioteca.get(), usuario, juego);
    }


    // ESTADÍSTICAS

    public List<BibliotecaDto> estadisticas(Long usuarioId) {
        List<BibliotecaDto> bibliotecaDtos = new ArrayList<>();
        var lista = repo.obtenerTodos().stream()
                .filter(b -> b.getIdUsuario() == usuarioId)
                .toList();
        var usuario = UsuarioMapper.toDTO(usuarioRepo.obtenerPorId(usuarioId).get());


        for (BibliotecaEntidad b : lista) {
            if (b != null) {
                var juego = JuegoMapper.toDTO(juegoRepo.obtenerPorId(b.getIdJuego()).get());
                bibliotecaDtos.add(BibliotecaMapper.toDTO(b, usuario, juego));
            }
        }

        return bibliotecaDtos;

    }
}