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
        if (comrobarFormatoNombreUser()==true){
            errores.add(new ErrorDto("nombre usuario", ErrorType.FORMATO_INVALIDO));
        }
        if (comprobarNoEmpiezaPorNumero()==true){
            errores.add(new ErrorDto("nombre usuario", ErrorType.FORMATO_INVALIDO));
        }


//giones y guiones bajos
        //email
        if (email.isBlank()){
            errores.add(new ErrorDto("Email", ErrorType.REQUERIDO));
        }

        return errores;
    }
    private boolean comrobarFormatoNombreUser(String nombreUsuario){
      boolean  nombreUsuarioCorrecto = false;
        for (int i = 0; i < nombreUsuario.length() ; i++) {
            char c = nombreUsuario.charAt(i);
            boolean alfanumerico =  Character.isLetterOrDigit(c);
            if (c!='-'||c != '_'|| !alfanumerico ){
            break;
            }
            nombreUsuarioCorrecto = true;
        }
        return nombreUsuarioCorrecto;
    }
}
