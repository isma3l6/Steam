package controlador;

import excepciones.ValidationException;
import mapper.JuegoMapper;
import mapper.ResenhaMapper;
import mapper.UsuarioMapper;
import modelo.dto.ResenhaDto;
import modelo.dto.UsuarioDto;
import modelo.entidad.EstadoResenhaType;
import modelo.entidad.ResenhaEntidad;
import modelo.entidad.UsuarioEntidad;
import modelo.form.ErrorDto;
import modelo.form.ErrorType;
import modelo.form.ResenhaForm;
import repositorio.inmemory.JuegoRepoInMemory;
import repositorio.inmemory.ResenhaRepoInMemory;
import repositorio.inmemory.BibliotecaRepoInMemory;
import repositorio.inmemory.UsuarioRepoInMemory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ResenhaControlador {

    private final ResenhaRepoInMemory resenhaRepo;
    private final BibliotecaRepoInMemory bibliotecaRepo;
    private final UsuarioRepoInMemory usuarioRepo;
    private final JuegoRepoInMemory juegoRepo;

    public ResenhaControlador(ResenhaRepoInMemory resenhaRepo,
                              BibliotecaRepoInMemory bibliotecaRepo,
                              UsuarioRepoInMemory usuarioRepo, JuegoRepoInMemory juegoRepo) {
        this.resenhaRepo = resenhaRepo;
        this.bibliotecaRepo = bibliotecaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo=juegoRepo;
    }


    //ESCRIBIR RESEÑA

    public ResenhaDto escribirResenha(ResenhaForm form) throws ValidationException {

        // 1. Validaciones de formato
        var errores = form.validarResena();
        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }

        // 2. Usuario existe
        var usuario = UsuarioMapper.toDTO( usuarioRepo.obtenerPorId(form.getIdUsuario()).orElse(null));

        var juego= JuegoMapper.toDTO(juegoRepo.obtenerPorId(form.getIdJuego()).orElse(null));

        if (usuario == null) {
            errores.add(new ErrorDto("Usuario", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        if (juego == null) {
            errores.add(new ErrorDto("juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        // 3. Juego en biblioteca
        if (!bibliotecaRepo.tieneJuego(form.getIdUsuario(), form.getIdJuego())) {
            errores.add(new ErrorDto("Biblioteca", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        // 4. Reseña duplicada
        if (resenhaRepo.existeResena(form.getIdUsuario(), form.getIdJuego())) {
            errores.add(new ErrorDto("Reseña", ErrorType.DUPLICADO));
            throw new ValidationException(errores);
        }

        // 5. Crear reseña

        return ResenhaMapper.toDTO(resenhaRepo.crear(form).get(),usuario,juego);
    }


    //ELIMINAR RESEÑA

    public boolean eliminarResenha(long idResenha, long idUsuario) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        var resenha=resenhaRepo.obtenerTodas().stream().filter(r->r.getId()==idResenha && r.getUsuaroId()==idUsuario).findFirst().orElse(null);
        if (resenha == null) {
            errores.add(new ErrorDto("Reseña", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        return resenhaRepo.eliminar(resenha.getId());

    }


    //OCULTAR RESEÑA

    public ResenhaDto ocultarResenha(long idResenha, long idUsuario) throws ValidationException {
        List<ErrorDto> errores = new ArrayList<>();
        var resenha = resenhaRepo.obtenerPorUsuarioYJuego(idUsuario, idResenha).orElse(null);

        if (resenha==null) {

            errores.add(new ErrorDto("Reseña", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        var usuario = UsuarioMapper.toDTO( usuarioRepo.obtenerPorId(idUsuario).orElse(null));

        if(usuario==null||usuario.getId()==resenha.getUsuaroId()){
            errores.add(new ErrorDto("Reseña", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        var juego= JuegoMapper.toDTO(juegoRepo.obtenerPorId(resenha.getNombreJuegoId()).orElse(null));

        if (juego==null) {

            errores.add(new ErrorDto("Juego", ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }
        resenha.setEstadoResenhaType(EstadoResenhaType.OCULTA);
        var resenhaForm = new ResenhaForm(idResenha, idUsuario, resenha.isRecomendado(), resenha.getTexto(), resenha.getHorasJugadas());


        return ResenhaMapper.toDTO(resenhaRepo.actualizar(idResenha, resenhaForm).get(),usuario,juego);
    }

    /* =========================================
        VER RESEÑAS DE UN JUEGO
    ========================================= */
    public List<ResenhaDto> verResenasPorJuego(long idJuego, String filtro, String orden) throws ValidationException {

        List<ErrorDto> errores = new ArrayList<>();
        List<ResenhaDto> resultados=new ArrayList<>();

        if (juegoRepo.obtenerPorId(idJuego).isEmpty()){
            errores.add(new ErrorDto("juego",ErrorType.NO_ENCONTRADO));
            throw new ValidationException(errores);
        }

        var resultado=resenhaRepo.obtenerTodas().stream().filter(r->idJuego==r.getNombreJuegoId()).toList();

        for (ResenhaEntidad r:resultado){

             var usuario=UsuarioMapper.toDTO(usuarioRepo.obtenerPorId(r.getUsuaroId()).get());

             var juego=JuegoMapper.toDTO(juegoRepo.obtenerPorId(r.getNombreJuegoId()).get());


            resultados.add(ResenhaMapper.toDTO(r,usuario,juego));

        }



        // Ordenar
        if ("recientes".equalsIgnoreCase(orden)) {
            return resultados.stream().sorted(Comparator.comparing(ResenhaDto::getFechaPublicacion).reversed()).toList();
        }
        // Orden por "útiles" se podría agregar si existiera contador de votos

        return resultados;
    }

    /* =========================================
       5️ VER RESEÑAS DE UN USUARIO
    ========================================= */
    public List<ResenhaDto> verResenasPorUsuario(long idUsuario, String filtroEstado) throws ValidationException {
        List<ErrorDto>errores=new ArrayList<>();

        List<ResenhaDto>resenas=new ArrayList<>();
        var usuario=usuarioRepo.obtenerPorId(idUsuario).orElse(null);
        if (usuario==null){
            throw new ValidationException(List.of(new ErrorDto("Usuario",ErrorType.NO_ENCONTRADO)));
        }
        var resultados=resenhaRepo.obtenerTodas().stream().filter(r ->r.getUsuaroId()==idUsuario ).toList();

        for (ResenhaEntidad r : resultados) {
                // Filtro de estado opcional: si quisiéramos oculto, eliminado, etc.
                if (filtroEstado != null && filtroEstado.equalsIgnoreCase("oculto") &&
                        !r.getEstadoResenhaType().equals(EstadoResenhaType.PUBLICADA)) continue;

                resenas.add(ResenhaMapper.toDTO(r,
                        UsuarioMapper.toDTO(usuario),
                        JuegoMapper.toDTO(juegoRepo.obtenerPorId(r.getNombreJuegoId()).get())));
            }


        // Orden por fecha descendente
        return resenas.stream().sorted(Comparator.comparing(ResenhaDto::getFechaPublicacion).reversed()).toList();


    }
}