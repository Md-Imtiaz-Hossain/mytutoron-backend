package imtiaz.sktech.mytutoron.exception;

import lombok.extern.slf4j.Slf4j;
import imtiaz.sktech.mytutoron.exception.custom.AlreadyExistsException;
import imtiaz.sktech.mytutoron.exception.custom.InsufficientException;
import imtiaz.sktech.mytutoron.exception.custom.LimitExceedException;
import imtiaz.sktech.mytutoron.exception.custom.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(LimitExceedException.class)
    public ResponseEntity<Object> handleLimitExceedExceptions(LimitExceedException exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        return this.buildErrorResponse(exception, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        return this.buildErrorResponse(exception, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(InsufficientException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleInsufficientException(InsufficientException exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        return this.buildErrorResponse(exception, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        return this.buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus, WebRequest request) {
        return this.buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus, WebRequest request) {
        var errorResponse = new ErrorResponse(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
