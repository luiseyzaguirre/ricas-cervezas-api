package cl.bender.ricascervezas.api.service;

import cl.bender.ricascervezas.api.exception.BeerAlreadyExists;
import cl.bender.ricascervezas.api.exception.BeerInternalServerError;
import cl.bender.ricascervezas.api.exception.BeerInvalidRequest;
import cl.bender.ricascervezas.api.exception.BeerNotFoundException;
import cl.bender.ricascervezas.api.model.BeerItem;
import java.util.List;

public interface BeerItemService {
    public List<BeerItem> searchBeers() throws BeerInternalServerError;
    public BeerItem searchBeerById(Integer id) throws BeerInternalServerError, BeerNotFoundException;
    public BeerItem addBeers(BeerItem beerItem) throws BeerAlreadyExists, BeerInvalidRequest, BeerInternalServerError;
}
