package modelo.dto;

import modelo.entidad.JuegoEntidad;
import modelo.entidad.UsuarioEntidad;

import java.util.Date;

public class ResenhaDto {
    private int id;
    private UsuarioEntidad usuaro;
    private JuegoEntidad nombreJuego;
    private boolean recomendado;
    private String texto;
    private Date fechaPublicacion;
    private Date fechaEdit;
    //Estado enum
}
