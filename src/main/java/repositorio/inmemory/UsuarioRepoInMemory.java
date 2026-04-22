package repositorio.inmemory;

import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;
import repositorio.interfaz.IUsuarioRepo;

import java.util.*;

public class UsuarioRepoInMemory implements IUsuarioRepo {
    private List<UsuarioEntidad> usuarios = new ArrayList<>();
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

        return usuarios.stream().filter(u->u.getId()==id).findFirst();
    }

    //READ ALL
    @Override
    public List<UsuarioEntidad> obtenerTodos() {

        return usuarios.stream().toList();
    }


    //UPDATE

    @Override
    public Optional<UsuarioEntidad> actualizar(long id, UsuarioForm form) {



        for (UsuarioEntidad u:usuarios) {

            if (u.getId() == id) {

                UsuarioEntidad actualizado = new UsuarioEntidad(
                        u.getId(),
                        form.getNombreUsuario(),
                        form.getEmail(),
                        form.getContrasena(),
                        form.getNombre(),
                        form.getApellido(),
                        form.getPais(),
                        form.getFechaNacimiento(),
                        u.getFechaRegistro(), // mantiene fecha registro
                        form.getAvatr(),
                        form.getSaldo(), // mantiene saldo
                        u.getEstadoType()
                );
                usuarios.remove(u);

                usuarios.add(actualizado);
                return Optional.of(actualizado);
            }
        }

        return Optional.empty();
    }


    //DELETE

    @Override
    public boolean eliminar(long id) {

        return usuarios.remove(usuarios.stream().filter(u -> u.getId() == id).findFirst().get());

    }

    //BUSCAR POR NOMBREUSUARIO
    @Override
    public Optional<UsuarioEntidad> buscarUsuarioPorNombre(String nombreUsuario) {
        return  usuarios.stream().filter(u -> Objects.equals(u.getNombreUsuario(), nombreUsuario)).findFirst();
    }

    //BUSCAR POR EMAIL
    public Optional<UsuarioEntidad> buscarUsuarioPorCorreo(String email) {
        return  usuarios.stream().filter(u -> Objects.equals(u.getEmail(), email)).findFirst();

    }
}