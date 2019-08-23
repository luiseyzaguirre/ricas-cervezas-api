package cl.bender.ricascervezas.api.controller;

import cl.bender.ricascervezas.api.exception.BeerInternalServerError;
import cl.bender.ricascervezas.api.exception.BeerNotFoundException;
import cl.bender.ricascervezas.api.model.BeerItem;
import cl.bender.ricascervezas.api.service.BeerItemServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BeerItemControllerSearchBeerByIdTest {

    @Mock
    BeerItemServiceImpl beerItemService;
    @InjectMocks
    BeerItemController beerItemController;

    @Test
    public void GIVEN_id_exists_beer_WHEN_search_by_id_THEN_shouldbe_return_beer_AND_status_ok()
            throws BeerNotFoundException, BeerInternalServerError {
        //GIVEN a exists beer
        BeerItem beerRegistered = getBeerRegistered();
        //WHEN search this beer by id
        Mockito.when(beerItemService.searchBeerById(beerRegistered.getId())).thenReturn(beerRegistered);
        ResponseEntity<?> response = beerItemController.searchBeerById(beerRegistered.getId());
        //THEN should be return the same beer
        assertEquals(beerRegistered, response.getBody());
        //AND status ok
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void GIVEN_id_not_exists_beer_WHEN_search_by_id_THEN_shouldbe_return_a_error_message_AND_status_not_found()
            throws BeerNotFoundException, BeerInternalServerError {
        //GIVEN not exists beer
        BeerItem beerNotRegistered = getBeerNotRegistered();
        //WHEN search this beer by id
        Mockito.when(beerItemService.searchBeerById(beerNotRegistered.getId()))
                .thenThrow(new BeerNotFoundException(getErrorNotFound(beerNotRegistered)));
        ResponseEntity<?> response = beerItemController.searchBeerById(beerNotRegistered.getId());
        //THEN should be return the same beer
        assertEquals(getErrorNotFound(beerNotRegistered), response.getBody());
        //AND status not found
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void GIVEN_any_beer_WHEN_search_THEN_shouldbe_return_status_internal_server_error()
            throws BeerNotFoundException, BeerInternalServerError  {
        //GIVEN any beer
        BeerItem anyBeer = getAnyBeer();
        //WHEN search this beer by id
        Mockito.when(beerItemService.searchBeerById(anyBeer.getId())).
                thenThrow(new BeerInternalServerError("Any Error", new Exception("Internal Server Error")));
        ResponseEntity<?> response = beerItemController.searchBeerById(anyBeer.getId());
        //THEN should be return any message in body
        assertNotNull(response.getBody());
        //AND status internal server error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    private BeerItem getAnyBeer() {
        return getBeerNotRegistered();
    }

    private BeerItem getBeerNotRegistered() {
        BeerItem beer = new BeerItem();
        beer.setId(10000);
        beer.setBrewery("Brewery Not Registered");
        beer.setCountry("Country not Registered");
        beer.setName("Name not registered");
        beer.setPrice(00.00);
        return beer;
    }


    private String getErrorNotFound(BeerItem beerRegistered) {
        return "El Id de la cerveza no existe ".concat(beerRegistered.getId().toString());
    }

    private BeerItem getBeerRegistered() {
        BeerItem beer = new BeerItem();
        beer.setId(1);
        beer.setBrewery("Bendel");
        beer.setCountry("Chile");
        beer.setName("God Beer");
        beer.setPrice(10.00);
        return beer;
    }

}
