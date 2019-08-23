package cl.bender.ricascervezas.api.exception;

public class BeerNotFoundException extends BeerException {
    public BeerNotFoundException(String message) {
        super(message);
    }
}
