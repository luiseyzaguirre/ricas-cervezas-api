package cl.bender.ricascervezas.api.exception;

public class BeerInternalServerError extends BeerException {

    public BeerInternalServerError(String message, Throwable mostSpecificCause) {
        super(message, mostSpecificCause);
    }
}
