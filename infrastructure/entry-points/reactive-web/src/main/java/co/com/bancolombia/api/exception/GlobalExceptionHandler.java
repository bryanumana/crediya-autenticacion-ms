package co.com.bancolombia.api.exception;

import co.com.bancolombia.model.exceptions.DatabaseConnectionException;
import co.com.bancolombia.model.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Serializable>> handleValidationException(ValidationException ex) {
        log.warn("Error de validación: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<Map<String, Serializable>> handleValidationException(DatabaseConnectionException ex) {
        log.warn("Error de conexión a la base de datos: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "message", "No se pudo establecer conexión con la base de datos"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        log.error("Error inesperado: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "status", 500,
                        "message", "Ocurrió un error inesperado en el servidor"));
    }


}
