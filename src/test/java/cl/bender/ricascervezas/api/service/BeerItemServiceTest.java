package cl.bender.ricascervezas.api.service;

import cl.bender.ricascervezas.api.exception.BeerAlreadyExists;
import cl.bender.ricascervezas.api.exception.BeerInternalServerError;
import cl.bender.ricascervezas.api.exception.BeerInvalidRequest;
import cl.bender.ricascervezas.api.model.BeerItem;
import cl.bender.ricascervezas.api.repository.BeerItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BeerItemServiceTest {
    @InjectMocks
    BeerItemServiceImpl beerItemService;
    @Mock
    BeerItemRepository beerItemRepository;

    @Test
    public void GIVEN_a_NEW_beer_WHEN_ADD_this_beer_THEN_shouldBe_return_a_new_beer()
            throws BeerInvalidRequest, BeerInternalServerError, BeerAlreadyExists {
        //GIVE a new beer
        BeerItem  newBeer = getNewBeer();
        //WHEN add new bear
        BeerItem expectedBeer = getBeerExpected();
        Mockito.when(beerItemRepository.save(newBeer)).thenReturn(expectedBeer);

        BeerItem returnedBeer =  beerItemService.addBeers(newBeer);
        //THEN new Id should be equals expected Id
        assertEquals(expectedBeer.getId(),returnedBeer.getId());
        //AND new Brewery should be equals expected Brewery
        assertEquals(expectedBeer.getBrewery(),returnedBeer.getBrewery());
        //AND new Country should be equals expected Country
        assertEquals(expectedBeer.getCountry(),returnedBeer.getCountry());
        //AND new Name should be equals expected Name
        assertEquals(expectedBeer.getName(),returnedBeer.getName());
        //AND new Price should be equals expected Price
        assertEquals(expectedBeer.getPrice(),returnedBeer.getPrice());
    }

    @Test(expected = BeerInvalidRequest.class)
    public void GIVEN_a_bad_request_beer_WHEN_add_this_beer_THEN_shouldbe_return_invalid_request()
            throws BeerInvalidRequest, BeerInternalServerError, BeerAlreadyExists {
        //GIVEN a new bad beer
        BeerItem newBadBeer = getNewBadBeer();
        //WHEN add new bad beer
        //THEN return invalid request
        Mockito.when(beerItemService.addBeers(newBadBeer)).thenThrow(BeerAlreadyExists.class);
    }

    @Test(expected = BeerAlreadyExists.class)
    public void GIVEN_exists_beer_WHEN_add_this_beer_THEN_shouldbe_return_already_exists()
            throws BeerInvalidRequest, BeerInternalServerError, BeerAlreadyExists {
        //GIVEN a beer already registered
        BeerItem newBeer = getBeerExpected();
        BeerItem beerExpected = getBeerExpected();
        Mockito.when(beerItemRepository.findById(beerExpected.getId())).thenReturn(Optional.of(beerExpected));
        Mockito.when(beerItemService.addBeers(newBeer)).thenReturn(beerExpected).thenThrow(BeerAlreadyExists.class);
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
    private BeerItem  getNewBadBeer(){
        BeerItem badBeer = new BeerItem();
        return badBeer;
    }

    private String getErrorBadRequest(){
        return "Request invalido";

    }

}
