package lt.techin.excursion_backend.validation;

import jakarta.validation.ConstraintViolationException;
import lt.techin.excursion_backend.controller.BaseController;
import lt.techin.excursion_backend.dto.ApiResponse;
import lt.techin.excursion_backend.exception.ApiErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach((violation) -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

        return badRequest(errors, "Validation failed");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((error) -> errors.put(error.getField(), error.getDefaultMessage()));

        return badRequest(errors, "validation failed");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return badRequest(null, ex.getMessage());
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ApiResponse<String>> handleApiErrorException(ApiErrorException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ApiResponse<>(null, ex.getMessage(), false));
    }
}
