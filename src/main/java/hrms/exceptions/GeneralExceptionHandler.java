package hrms.exceptions;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.validation.FieldError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hrms.core.utils.results.ErrorDataResult;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions,
                                                             @NotNull HttpHeaders headers,
                                                             @NotNull HttpStatus status,
                                                             @NotNull WebRequest request) {
        Map<String, String> validationErrors = new HashMap<String, String>();

        for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Verification Error");

        return errors;
    }

    @ExceptionHandler
    public ResponseEntity<?> jobNotFoundException(JobNotFoundException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

}
