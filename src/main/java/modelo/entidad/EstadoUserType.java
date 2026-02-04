package modelo.dto;

public enum EstadoUserType {
    ACTIVA("Activa"),
    SUSPENDIDA("Suspendida"),
    BANEADA("Baneada");

    private final String mensaje;

    EstadoUserType(String mensaje) {
        this.mensaje = mensaje;
    }

}
