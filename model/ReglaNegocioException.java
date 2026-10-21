package model;

/** Se lanza cuando una operación no cumple las condiciones de validez. */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
