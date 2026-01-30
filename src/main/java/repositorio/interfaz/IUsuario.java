package repositorio.interfaz;

import modelo.dto.UsuarioDto;

public interface IUsuario {
    UsuarioDto registrarUsuario();

    UsuarioDto mostrarPerfil();

    UsuarioDto anhadirDinero();

    UsuarioDto ConsultarSaldo();
}
