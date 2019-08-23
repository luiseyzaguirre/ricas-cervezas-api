package cl.bender.ricascervezas.api.exception;

public class BeerException extends Exception {
    public BeerException(String message) {
        super(message);
    }
    public BeerException(String message, Throwable mostSpecificCause) {
        super(message, mostSpecificCause);
    }
}
