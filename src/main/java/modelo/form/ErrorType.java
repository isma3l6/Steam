package modelo.form;

public enum ErrorType {
    REQUERIDO("El campo es obligatorio"),
    FORMATO_INVALIDO("El formato es inválido"),
    VALOR_DEMASIADO_ALTO("El valor es demasiado alto"),
    VALOR_DEMASIADO_BAJO("El valor es demasiado bajo"),
    NO_ENCONTRADO("No se encontró "),
    DUPLICADO("El elemento está duplicado"),
    PORCENTAJE_INVALIDO("El porcentaje se pasa del rango"),
    CUENTA_BLOQUEADA("La cuenta se haya bloqueada"),
    ERROR_EN_BASE("error al insertar en la base de datos"),
    INVALIDO("invalido")
   ;

    private final String mensaje;

    ErrorType(String mensaje) {
        this.mensaje = mensaje;
    }
}
