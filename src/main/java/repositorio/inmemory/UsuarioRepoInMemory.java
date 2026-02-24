package repositorio.inmemory;

import modelo.entidad.EstadoUserType;
import modelo.entidad.UsuarioEntidad;
import modelo.form.UsuarioForm;
import repositorio.interfaz.IUsuarioRepo;

import java.util.Date;
import java.util.Objects;

public class UsuarioRepoInMemory implements IUsuarioRepo {

    private UsuarioEntidad[] usuarios = new UsuarioEntidad[100];
    private int size = 0;
    private long idCounter = 1;

       //CREATE

    @Override
    public UsuarioEntidad crear(UsuarioForm form) {

        if (size >= usuarios.length) {
            throw new RuntimeException("Capacidad máxima alcanzada");
        }

        UsuarioEntidad nuevo = new UsuarioEntidad(
                idCounter++,
                form.getNombreUsuario(),
                form.getEmail(),
                form.getContrasena(),
                form.getNombre(),
                form.getApellido(),
                form.getFechaNacimiento(),
                new Date(),
                form.getAvatr(),
                0.0,
                EstadoUserType.ACTIVA
        );

        usuarios[size] = nuevo;
        size++;

        return nuevo;
    }

      // READ BY ID
    @Override
    public UsuarioEntidad obtenerPorId(long id) {

        for (int i = 0; i < size; i++) {
            if (usuarios[i].getId() == id) {
                return usuarios[i];
            }
        }
        return null;
    }

       //READ ALL
    @Override
    public UsuarioEntidad[] obtenerTodos() {

        UsuarioEntidad[] copia = new UsuarioEntidad[size];

        for (int i = 0; i < size; i++) {
            copia[i] = usuarios[i];
        }

        return copia;
    }


       //UPDATE

    @Override
    public UsuarioEntidad actualizar(long id, UsuarioForm form) {

        for (int i = 0; i < size; i++) {

            if (usuarios[i].getId() == id) {

                UsuarioEntidad actualizado = new UsuarioEntidad(
                        id,
                        form.getNombreUsuario(),
                        form.getEmail(),
                        form.getContrasena(),
                        form.getNombre(),
                        form.getApellido(),
                        form.getFechaNacimiento(),
                        usuarios[i].getFechaRegistro(), // mantiene fecha registro
                        form.getAvatr(),
                        usuarios[i].getSaldo(), // mantiene saldo
                        usuarios[i].getEstadoType()
                );

                usuarios[i] = actualizado;
                return actualizado;
            }
        }

        return null;
    }


       //DELETE

    @Override
    public boolean eliminar(long id) {

        for (int i = 0; i < size; i++) {

            if (usuarios[i].getId() == id) {

                // desplazamiento a la izquierda
                for (int j = i; j < size - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }

                usuarios[size - 1] = null;
                size--;

                return true;
            }
        }

        return false;
    }
//BUSCAR POR NOMBREUSUARIO
    public boolean buscarUsuarioPorNombre(String nombreUsuario) {


        for (int i = 0; i < size; i++) {
            if (Objects.equals(usuarios[i].getNombreUsuario(), nombreUsuario)) {
                return false;
            }
        }
        return true;
    }
//BUSCAR POR EMAIL
    public boolean buscarUsuarioPorCorreo(String email) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(usuarios[i].getEmail(), email)) {
                return false;
            }
        }
        return true;
    }
}