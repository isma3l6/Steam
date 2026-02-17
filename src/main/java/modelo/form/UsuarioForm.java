package modelo.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioForm {
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String pais;
    private Date fechaNacimiento;
    private String avatr;

    public List<ErrorDto> validarUsuario() {
        List<ErrorDto> errores = new ArrayList<ErrorDto>();
        //nombre usuario
        if (nombreUsuario.isBlank()) {
            errores.add(new ErrorDto("nobre usuario", ErrorType.REQUERIDO));
        }
        //pendiente generar la funcion
        if (buscarUsuario()) {
            errores.add(new ErrorDto("nombre usuario", ErrorType.DUPLICADO));
        }
        if (nombreUsuario.length() < 3) {
            errores.add(new ErrorDto("nombre usuario", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (nombreUsuario.length() > 20) {
            errores.add(new ErrorDto("nombre usuario", ErrorType.VALOR_DEMASIADO_ALTO));
        }
        if (comrobarFormatoNombreUser(nombreUsuario) == false) {
            errores.add(new ErrorDto("nombre usuario", ErrorType.FORMATO_INVALIDO));
        }
        if (comprobarEmpiezaPorNumero(nombreUsuario) == true) {
            errores.add(new ErrorDto("nombre usuario", ErrorType.FORMATO_INVALIDO));
        }


        //email
        if (email.isBlank()) {
            errores.add(new ErrorDto("Email", ErrorType.REQUERIDO));
        }
        if (buscarUsuario()) {
            errores.add(new ErrorDto("Email duplicado", ErrorType.DUPLICADO));
        }
        if (comprobarFormatoEmail(email) == true) {

        }

        //Contraseña
        if (contrasena.isBlank()){
            errores.add(new ErrorDto("Contraseña ", ErrorType.REQUERIDO));
        }
        if (contrasena.length() < 8 ){
            errores.add(new ErrorDto("Contraseña ", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (!comprobarFormatoContraseña(contrasena)){
            errores.add(new ErrorDto("Contraseña", ErrorType.FORMATO_INVALIDO));
        }
        //Nombre real
        if (nombre.isBlank()||apellido.isBlank()){
            errores.add(new ErrorDto("nombre y apellido", ErrorType.REQUERIDO));
        }if (nombre.length() < 2){
            errores.add(new ErrorDto("Nombre ", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        //Existen apellidos con un solo caracter como el apellido "O" en China
        if (apellido.length()< 1){
            errores.add(new ErrorDto("Apellido ", ErrorType.VALOR_DEMASIADO_BAJO));
        }
        if (apellido.length()>50 || nombre.length()>50){
            errores.add(new ErrorDto("nombre ", ErrorType.VALOR_DEMASIADO_ALTO));
        }

        //Pais
        if (pais.isBlank()){
            errores.add(new ErrorDto("pais", ErrorType.REQUERIDO));
        }
        if (paisAutorizado(pais) ){
            errores.add(new ErrorDto("pais", ErrorType.FORMATO_INVALIDO));
        }

        //Fecha nacimiento
        if (fechaNacimiento == null){
            errores.add(new ErrorDto("fecha nacimiento", ErrorType.REQUERIDO));
        }
        //Mallorde 13 y no puede ser futura

        //avatar
        if (avatr.length()>100){
            errores.add(new ErrorDto("pais", ErrorType.FORMATO_INVALIDO));
        }

        //Saldo defecto 0.00 positivo maximo dos decimales


        return errores;
    }

    private boolean paisAutorizado(String pais) {
       return true;
    }

    private boolean comprobarFormatoContraseña(String contrasena) {
        boolean contrasenaBien = false;
        for (int i = 0; i < contrasena.length(); i++) {
            char a = contrasena.charAt(i);
            boolean isDigito = Character.isDigit(a);
            boolean isMayuscula = Character.isUpperCase(a);
            boolean isMinuscula = Character.isLowerCase(a);

            if (!isDigito||!isMayuscula||!isMinuscula){
                contrasenaBien = false;
            }
            else {
                contrasenaBien = true;
            }


        }
        return contrasenaBien;
    }

    private boolean comprobarFormatoEmail(String email) {
        String[] partes = toString().split("@");
        String parteLocal = partes[0];
        String parteDominio = partes[1];


        for (int i = 0; i < parteLocal.length(); i++) {
            char e = parteLocal.charAt(i);
            char d = parteDominio.charAt(i);
            boolean alfanumerico = Character.isLetterOrDigit(e);
            boolean alfanumericoDominio =Character.isLetterOrDigit(d);
            if (!alfanumerico || e != '!' || e != '#' || e != '$' || e != '%' || e != '&' || e != '‘' || e != '*' || e != '+') {
                return false;

            }
            if (!alfanumericoDominio || d !='.'){
                return false;
            }

        }
        return true;
    }

    private boolean comprobarEmpiezaPorNumero(String nombreUsuario) {
        char primerDigito = nombreUsuario.charAt(0);
        boolean empiezaNumero = Character.isDigit(primerDigito);
        if (!empiezaNumero) {
            return true;
        } else {
            return false;
        }
    }

    private boolean comrobarFormatoNombreUser(String nombreUsuario) {
        boolean nombreUsuarioCorrecto = false;
        for (int i = 0; i < nombreUsuario.length(); i++) {
            char c = nombreUsuario.charAt(i);
            boolean alfanumerico = Character.isLetterOrDigit(c);
            if (!alfanumerico && (c != '-' || c != '_')) {
                nombreUsuarioCorrecto = false;
                break;
            }
            nombreUsuarioCorrecto = true;
        }
        return nombreUsuarioCorrecto;
    }

}
