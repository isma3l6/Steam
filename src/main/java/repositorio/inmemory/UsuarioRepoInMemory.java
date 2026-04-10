package repositorio.inmemory;

import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;
import repositorio.interfaz.IUsuarioRepo;

import java.util.*;

public class UsuarioRepoInMemory implements IUsuarioRepo {
    private List<UsuarioEntidad> usuarios = new ArrayList<>()
    private int size = 0;
    private long idCounter = 1;


    //CREATE

    @Override
    public Optional<UsuarioEntidad> crear(UsuarioForm form) {

        UsuarioEntidad nuevo = new UsuarioEntidad(
                idCounter++,
                form.getNombreUsuario(),
                form.getEmail(),
                form.getContrasena(),
                form.getNombre(),
                form.getApellido(),
                form.getPais(),
                form.getFechaNacimiento(),
                new Date(),
                form.getAvatr(),
                0.0,
                EstadoUserType.ACTIVA
        );

        usuarios.add(nuevo);

        return Optional.of(nuevo);
    }

    // READ BY ID
    @Override
    public Optional<UsuarioEntidad> obtenerPorId(long id) {

        for (int i = 0; i < size; i++) {
            if (usuarios.get(i).getId() == id) {
                return Optional.of(usuarios.get(i));
            }
        }
        return null;
    }

    //READ ALL
    @Override
    public List<UsuarioEntidad> obtenerTodos() {

        return usuarios.stream().toList();
    }


    //UPDATE

    @Override
    public Optional<UsuarioEntidad> actualizar(long id, UsuarioForm form) {

        for (int i = 0; i < size; i++) {

            if (usuarios.get(i).getId() == id) {

                UsuarioEntidad actualizado = new UsuarioEntidad(
                        id,
                        form.getNombreUsuario(),
                        form.getEmail(),
                        form.getContrasena(),
                        form.getNombre(),
                        form.getApellido(),
                        form.getPais(),
                        form.getFechaNacimiento(),
                        usuarios.get(i).getFechaRegistro(), // mantiene fecha registro
                        form.getAvatr(),
                        usuarios.get(i).getSaldo(), // mantiene saldo
                        usuarios.get(i).getEstadoType()
                );

                usuarios[i] = actualizado;
                return Optional.of(actualizado);
            }
        }

        return null;
    }


    //DELETE

    @Override
    public boolean eliminar(long id) {

        return usuarios.remove(usuarios.stream().filter(u -> u.getId() == id).findFirst());

    }

    //BUSCAR POR NOMBREUSUARIO
    public boolean buscarUsuarioPorNombre(String nombreUsuario) {
        return  usuarios.stream().filter(u -> u.getNombreUsuario() == nombreUsuario).findFirst().isPresent();
    }

    //BUSCAR POR EMAIL
    public boolean buscarUsuarioPorCorreo(String email) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(usuarios.get(i).getEmail(), email)) {
                return false;
            }
        }
        return true;
    }
}