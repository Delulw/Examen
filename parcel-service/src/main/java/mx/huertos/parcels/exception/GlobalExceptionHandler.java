package mx.huertos.parcels.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException exception, HttpServletRequest request) {
        return error(HttpStatus.NOT_FOUND, "Recurso no encontrado", exception.getMessage(),
                request, Map.of());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiError> handleInvalidInput(
            InvalidInputException exception, HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, "Solicitud inválida", exception.getMessage(),
                request, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fields.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error(HttpStatus.BAD_REQUEST, "Error de validación",
                "Revisa los campos indicados y vuelve a intentarlo.", request, fields);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMalformedBody(
            HttpMessageNotReadableException exception, HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, "Solicitud inválida",
                "El cuerpo JSON no es válido o contiene un valor no permitido.", request, Map.of());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, "Parámetro inválido",
                "El parámetro '" + exception.getName() + "' tiene un formato no válido.",
                request, Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
            Exception exception, HttpServletRequest request) {
        LOGGER.error("Error inesperado al procesar {}", request.getRequestURI(), exception);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno",
                "Ocurrió un error inesperado. Intenta nuevamente más tarde.", request, Map.of());
    }

    private ResponseEntity<ApiError> error(
            HttpStatus status, String title, String message, HttpServletRequest request,
            Map<String, String> fields) {
        ApiError body = new ApiError(Instant.now(), status.value(), title, message,
                request.getRequestURI(), fields);
        return ResponseEntity.status(status).body(body);
    }
}
