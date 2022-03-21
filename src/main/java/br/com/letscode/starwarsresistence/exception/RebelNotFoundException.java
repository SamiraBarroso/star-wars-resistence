package br.com.letscode.starwarsresistence.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RebelNotFoundException extends RuntimeException  {
    public RebelNotFoundException(String message) {
        super(message);
    }
}
