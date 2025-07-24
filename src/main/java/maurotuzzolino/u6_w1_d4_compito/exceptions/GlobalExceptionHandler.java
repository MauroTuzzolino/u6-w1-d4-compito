package maurotuzzolino.u6_w1_d4_compito.exceptions;

import maurotuzzolino.u6_w1_d4_compito.payloads.ErrorPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorPayload> handleNotFound(NotFoundException ex) {
        ErrorPayload payload = new ErrorPayload(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(payload, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorPayload> handleBadRequest(BadRequestException ex) {
        ErrorPayload payload = new ErrorPayload(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorPayload> handleGeneric(Exception ex) {
        ErrorPayload payload = new ErrorPayload(
                "Errore interno del server: " + ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}