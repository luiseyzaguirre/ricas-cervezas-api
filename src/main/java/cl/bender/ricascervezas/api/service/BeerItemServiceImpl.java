package cl.bender.ricascervezas.api.service;

import cl.bender.ricascervezas.api.exception.BeerAlreadyExists;
import cl.bender.ricascervezas.api.exception.BeerInternalServerError;
import cl.bender.ricascervezas.api.exception.BeerInvalidRequest;
import cl.bender.ricascervezas.api.exception.BeerNotFoundException;
import cl.bender.ricascervezas.api.model.BeerItem;
import cl.bender.ricascervezas.api.repository.BeerItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BeerItemServiceImpl implements BeerItemService {

    @Autowired
    private BeerItemRepository beerItemRepository;
    @Override
    public List<BeerItem> searchBeers() throws BeerInternalServerError {
        try {
            return (List<BeerItem>) beerItemRepository.findAll();
        }catch (DataAccessException e){
            throw new BeerInternalServerError(e.getMessage(), e.getMostSpecificCause());
        }
    }
    @Override
    public BeerItem searchBeerById(Integer id) throws BeerInternalServerError, BeerNotFoundException {
        try {
            BeerItem beerItem =  beerItemRepository.findById(id).orElse(null);
            if(beerItem==null){
                throw new BeerNotFoundException("El Id de la cerveza no existe ".concat(id.toString()));
            }
            return beerItem;
        }catch (DataAccessException e){
            throw new BeerInternalServerError(e.getMessage(), e.getMostSpecificCause());
        }
    }
    @Override
    public BeerItem addBeers(BeerItem beerItem)
            throws BeerAlreadyExists, BeerInvalidRequest, BeerInternalServerError {
        try{
            if (isBadRequest(beerItem)) {
                throw new BeerInvalidRequest("Request invalido");
            }
            if (validateIfBeerExists(beerItem.getId())){
                throw new BeerAlreadyExists("El ID de la cerveza ya existe ".concat(beerItem.getId().toString()));
            }
            return beerItemRepository.save(beerItem);
        }catch (DataAccessException e){
            throw new BeerInternalServerError(e.getMessage(), e.getMostSpecificCause());
        }
    }

    private boolean validateIfBeerExists(Integer id) throws BeerInternalServerError{
        try{
            if (null == id){
                return false;
            }
            return beerItemRepository.findById(id).isPresent();
        }catch (DataAccessException e){
            throw new BeerInternalServerError(e.getMessage(), e.getMostSpecificCause());
        }
    }

    private boolean isBadRequest(BeerItem beerItem) {
        if (null == beerItem){
            return true;
        }
        if (null == beerItem.getName() ||  beerItem.getName().isEmpty()){
            return true;
        }
        if (null == beerItem.getBrewery() ||  beerItem.getBrewery().isEmpty()){
            return true;
        }
        if (null == beerItem.getCountry() ||  beerItem.getCountry().isEmpty()){
            return true;
        }
        if (null == beerItem.getPrice() ||  beerItem.getPrice().floatValue() == 0){
            return true;
        }
        return false;
    }

}
