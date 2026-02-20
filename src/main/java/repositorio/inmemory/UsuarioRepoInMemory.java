package repositorio.inmemory;

import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepoInMemory {

    private List<UsuarioEntidad> usuarios = new ArrayList<>();
    private Long contadorId = 1L;

    // CREATE
    public UsuarioEntidad crear(UsuarioForm usuario) {

        // Validar username único
        boolean existe = usuarios.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(usuario.getUsername()));

        if (existe) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        usuario = new Usuario(
                contadorId++,
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getNombreReal(),
                usuario.getPais(),
                usuario.getFechaNacimiento(),
                usuario.getAvatar(),
                usuario.getSaldo(),
                usuario.getEstado()
        );

        usuarios.add(usuario);
        return usuario;
    }

    // READ BY ID
    public UsuarioEntidad buscarPorId(Long id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // READ ALL
    public List<UsuarioEntidad> listarTodos() {
        return usuarios;
    }

    // UPDATE
    public void actualizar(Long id, UsuarioForm datosActualizados) {
        UsuarioEntidad usuario = buscarPorId(id);

        if (usuario != null) {
            usuario.setNombreUsuario(datosActualizados.getNombreUsuario());
            usuario.setEmail(datosActualizados.getEmail());
            usuario.setContraseña(datosActualizados.getContraseña());
            usuario.setNombre(datosActualizados.getNombre());
            usuario.setPais(datosActualizados.getPais());
            usuario.setAvatar(datosActualizados.getAvatar());
            usuario.setSaldo(datosActualizados.getSaldo());
            usuario.setEstadoType(datosActualizados.getEstadoType());
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    // DELETE
    public void eliminar(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }
}