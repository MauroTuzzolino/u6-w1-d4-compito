package maurotuzzolino.u6_w1_d4_compito.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}