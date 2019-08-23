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
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BeerItemControllerSearchBeersTest {

    @Mock
    BeerItemServiceImpl beerItemService;
    @InjectMocks
    BeerItemController beerItemController;

    @Test
    public void GIVEN_five_exists_beer_WHEN_search_beers_THEN_shouldbe_return_five_beers_AND_status_ok()
            throws BeerNotFoundException, BeerInternalServerError {
        //GIVEN five beers
       List<BeerItem> fiveBeers = getFiveBeerRegistered();
        //WHEN search beers
        Mockito.when(beerItemService.searchBeers()).thenReturn(fiveBeers);
        ResponseEntity<?> response = beerItemController.searchBeers();
        //THEN should be return the same five beers
        assertEquals(fiveBeers, response.getBody());
        //AND status ok
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void GIVEN_there_are_NO_beers_WHEN_search_beers_THEN_shouldbe_return_NO_beers_AND_status_ok()
            throws BeerNotFoundException, BeerInternalServerError {
        //GIVEN there_are no beers
        List<BeerItem> noBeers = getNoBeers();
        //WHEN search beers
        Mockito.when(beerItemService.searchBeers()).thenReturn(noBeers);
        ResponseEntity<?> response = beerItemController.searchBeers();
        //THEN should be return no beers
        assertEquals(noBeers, response.getBody());
        //AND status ok
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void GIVEN_lot_of_beers_WHEN_search_beers_THEN_shouldbe_return_status_internal_server_error()
            throws BeerNotFoundException, BeerInternalServerError  {
        //GIVEN
        BeerItem anyBeer = getAnyBeer();
        //WHEN search beers
        Mockito.when(beerItemService.searchBeers()).
                thenThrow(new BeerInternalServerError("Any Error", new Exception("Internal Server Error")));
        ResponseEntity<?> response = beerItemController.searchBeers();
        //THEN should be return no bears
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

    private List<BeerItem> getNoBeers() {
        return new ArrayList<>();
    }

    private List<BeerItem> getFiveBeerRegistered() {
        List<BeerItem> beers = new ArrayList<>();
        for(Integer i =1; i<5 ;i++){
            BeerItem beer = new BeerItem();
            beer.setId(i);
            beer.setBrewery("Bendel"+i);
            beer.setCountry("Chile"+i);
            beer.setName("God Beer"+i);
            beer.setPrice(i.doubleValue());
            beers.add(beer);
        }
        return beers;
    }

}
