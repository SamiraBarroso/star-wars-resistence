package br.com.letscode.starwarsresistence.handler;

import br.com.letscode.starwarsresistence.exception.DuplicateItemsInventoryException;
import br.com.letscode.starwarsresistence.exception.InvalidNegotiationException;
import br.com.letscode.starwarsresistence.exception.InvalidReportException;
import br.com.letscode.starwarsresistence.exception.RebelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

record Error(
        String title,
        String details,
        String developerMessage
) {
}
@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler({RebelNotFoundException.class,})
    public ResponseEntity<Error> hadlerRebelNotFound(RebelNotFoundException rebelNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new Error("Rebel not found", rebelNotFoundException.getMessage(), rebelNotFoundException.getClass().getName()));
    }

    @ExceptionHandler(InvalidReportException.class)
    public ResponseEntity<Error> handlerInvalidReportException(InvalidReportException invalidReportException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).
                body(new Error("Invalid report Exception", invalidReportException.getMessage(), invalidReportException.getClass().getName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(DuplicateItemsInventoryException.class)
    public ResponseEntity<Error> handlerDuplicateItemsInventoryException(DuplicateItemsInventoryException duplicateItemsInventoryException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).
                body(new Error("Duplicate items inventory Exception", duplicateItemsInventoryException.getMessage(),duplicateItemsInventoryException.getClass().getName() ));
    }

    @ExceptionHandler(InvalidNegotiationException.class)
    public ResponseEntity<Error> handlerInvalidTradeException(InvalidNegotiationException invalidNegotiationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).
                body(new Error("Invalid trade Exception", invalidNegotiationException.getMessage(),invalidNegotiationException.getClass().getName()));
    }
}
