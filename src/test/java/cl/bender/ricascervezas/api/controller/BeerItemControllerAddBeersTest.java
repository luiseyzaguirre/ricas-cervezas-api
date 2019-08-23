package cl.bender.ricascervezas.api.controller;
import cl.bender.ricascervezas.api.exception.BeerAlreadyExists;
import cl.bender.ricascervezas.api.exception.BeerInternalServerError;
import cl.bender.ricascervezas.api.exception.BeerInvalidRequest;
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
public class BeerItemControllerAddBeersTest {

    @Mock
    BeerItemServiceImpl beerItemService;
    @InjectMocks
    BeerItemController beerItemController;

    @Test
    public void GIVEN_new_beer_WHEN_add_beer_THEN_shouldbe_return_a_new_beer_AND_status_created()
            throws BeerInvalidRequest, BeerInternalServerError, BeerAlreadyExists {
        //GIVEN a new beer
        BeerItem newBeer = getNewBeer();
        //WHEN add the new beer
        Mockito.when(beerItemService.addBeers(newBeer)).thenReturn(getBeerExpected());
        ResponseEntity<?> response = beerItemController.addBeers(newBeer);
        //THEN should be return a beer distinct null
        BeerItem returnedBeer = (BeerItem) response.getBody();
        assertNotNull(returnedBeer);
        //AND create expected Beer Item
        BeerItem expectedBeer = getBeerExpected();

        //AND new Id should be equals expected Id
        assertEquals(expectedBeer.getId(),returnedBeer.getId());
        //AND new Brewery should be equals expected Brewery
        assertEquals(expectedBeer.getBrewery(),returnedBeer.getBrewery());
        //AND new Country should be equals expected Country
        assertEquals(expectedBeer.getCountry(),returnedBeer.getCountry());
        //AND new Name should be equals expected Name
        assertEquals(expectedBeer.getName(),returnedBeer.getName());
        //AND new Price should be equals expected Price
        assertEquals(expectedBeer.getPrice(),returnedBeer.getPrice());
        //AND status created
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }
    @Test
    public void GIVEN_a_exists_beer_WHEN_add_beer_THEN_shouldbe_return_message_AND_status_conflict()
            throws BeerInvalidRequest, BeerInternalServerError, BeerAlreadyExists {
        //GIVEN a beer already registered
        BeerItem beerAlreadyRegistered = getBeerAlreadyRegistered();
        //WHEN add this beer
        Mockito.when(beerItemService.addBeers(beerAlreadyRegistered))
                .thenThrow(new BeerAlreadyExists(getErrorAlreadyRegistered(beerAlreadyRegistered)));
        ResponseEntity<?> response = beerItemController.addBeers(beerAlreadyRegistered);
        //THEN should be return a message with error
        assertEquals(getErrorAlreadyRegistered(beerAlreadyRegistered),response.getBody());
        //AND status conflict
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    private String getErrorAlreadyRegistered(BeerItem beerAlreadyRegistered) {
        return "El ID de la cerveza ya existe ".concat(beerAlreadyRegistered.getId().toString());
    }

    @Test
    public void GIVEN_bad_new_beer_WHEN_add_beer_THEN_shouldbe_return_invalid_request_AND_status_bad_request()
            throws BeerInvalidRequest, BeerInternalServerError, BeerAlreadyExists {
        //GIVEN a new bad beer
        BeerItem newBadBeer = getNewBadBeer();
        //WHEN add new bad beer
        Mockito.when(beerItemService.addBeers(newBadBeer)).
                thenThrow(new BeerInvalidRequest(getErrorBadRequest()));
        ResponseEntity<?> response = beerItemController.addBeers(newBadBeer);
        //THEN should be return details bad request error
        assertEquals(getErrorBadRequest(), response.getBody());
        //AND status created
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void GIVEN_null_beer_WHEN_add_beer_THEN_shouldbe_return_invalid_request_AND_status_bad_request()
            throws BeerInvalidRequest, BeerInternalServerError, BeerAlreadyExists {
        //GIVEN a new bad beer
        BeerItem newBadBeer = getNewBadBeer();
        //WHEN add new bad beer
        Mockito.when(beerItemService.addBeers(newBadBeer)).thenThrow(new BeerInvalidRequest(getErrorBadRequest()));
        ResponseEntity<?> response = beerItemController.addBeers(newBadBeer);
        //THEN should be return details bad request error
        assertEquals(getErrorBadRequest(), response.getBody());
        //AND status created
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void GIVEN_ANY_beer_WHEN_ADD_this_beer_THEN_shouldBe_return_status_internal_server_error()
            throws BeerInvalidRequest, BeerInternalServerError, BeerAlreadyExists {
        //GIVEN any beer
        BeerItem anyBeer = getAnyBeer();
        //WHEN add any beer
        Mockito.when(beerItemService.addBeers(anyBeer)).
                thenThrow(new BeerInternalServerError("Any Error", new Exception("Internal Server Error")));
        ResponseEntity<?> response = beerItemController.addBeers(anyBeer);
        //THEN should be return an error message
        assertNotNull(response.getBody());
        //AND status internal server error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    private BeerItem getNewBeer() {
        BeerItem newBeer = getBeerWithoutID();
        newBeer.setId(null);
        return newBeer;
    }

    private BeerItem getBeerWithoutID(){
        BeerItem beer = new BeerItem();
        beer.setBrewery("Bendel");
        beer.setCountry("Chile");
        beer.setName("God Beer");
        beer.setPrice(10.00);
        return beer;
    }

    private BeerItem getBeerExpected() {
        BeerItem expectedBeer = getBeerWithoutID();
        expectedBeer.setId(1);
        return expectedBeer;
    }

    private BeerItem getAnyBeer() {
        return getBeerExpected();
    }

    private BeerItem getBeerAlreadyRegistered() {
        return getBeerExpected();
    }

    private BeerItem  getNewBadBeer(){
        BeerItem badBeer = new BeerItem();
        return badBeer;
    }
    private String getErrorBadRequest(){
         return "Request invalido";
    }

}
