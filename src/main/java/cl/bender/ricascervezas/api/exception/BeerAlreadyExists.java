package cl.bender.ricascervezas.api.exception;

public class BeerAlreadyExists extends BeerException {
    public BeerAlreadyExists(String message) {
        super(message);
    }
}
