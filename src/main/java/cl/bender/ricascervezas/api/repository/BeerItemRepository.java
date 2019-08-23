package cl.bender.ricascervezas.api.repository;

import cl.bender.ricascervezas.api.model.BeerItem;
import org.springframework.data.repository.CrudRepository;

public interface BeerItemRepository extends CrudRepository<BeerItem, Integer> {
}
