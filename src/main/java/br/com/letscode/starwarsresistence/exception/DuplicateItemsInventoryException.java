package br.com.letscode.starwarsresistence.exception;

public class DuplicateItemsInventoryException extends RuntimeException {
    public DuplicateItemsInventoryException(String message) {
        super(message);
    }
}