package pl.adamd.todosy.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {

    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException validException){
        List<String> errors = validException.getBindingResult().getFieldErrors().stream().map(
                FieldError::getDefaultMessage).collect(Collectors.toList());

        return new ResponseEntity<>(ge)
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors){
        Map<String, List<String>> errorR
    }
}
