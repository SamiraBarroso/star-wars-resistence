package br.com.letscode.starwarsresistence.exception;

public class InvalidReportException extends RuntimeException {
    public InvalidReportException(String message) {
        super(message);
    }
}