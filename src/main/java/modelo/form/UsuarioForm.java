package modelo.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioForm {
    private String nombreUsuario;
    private String email;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;

    public List<ErrorDto> validarUsuario(){
        List<ErrorDto>errores=new ArrayList<ErrorDto>();
        //nombre usuario
        if (nombreUsuario.isBlank()){
            errores.add(new ErrorDto("nobre usuario", ErrorType.REQUERIDO));
        }
        //pendiente generar la funcion
        if (buscarUsuario()){
            errores.add(new ErrorDto("nombre usuario", ErrorType.DUPLICADO));
        }
        if (nombreUsuario.length()<3){
            errores.add(new ErrorDto("nombre usuario", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (nombreUsuario.length()>20){
            errores.add(new ErrorDto("nombre usuario", ErrorType.VALOR_DEMASIADO_ALTO));
        }
//giones y guiones bajos


    }
}
