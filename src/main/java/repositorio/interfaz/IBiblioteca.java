package repositorio.interfaz;

import modelo.dto.BibliotecaDto;

import java.util.List;

public interface IBiblioteca {

    List <BibliotecaDto> mostrarBiblioteca();

    BibliotecaDto anhadirJuego();

    BibliotecaDto elliminarJuegp();

    List<BibliotecaDto>actualizarTiempoJuego();

    BibliotecaDto consultarUltimaSesion();

    List<BibliotecaDto> filtrarBiblioteca();

    List<BibliotecaDto> verestadisticas();


}
