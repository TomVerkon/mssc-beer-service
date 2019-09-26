package guru.springframework.msscbeerservice.services;

import org.springframework.data.domain.PageRequest;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

/**
 * Created by jt on 2019-06-06.
 */
public interface BeerService {
    BeerDto getById(Long beerId, Boolean showInventoryOnHand);
    
    BeerDto getByUpc(String upc, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(String upc, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
	    Boolean showInventoryOnHand);
}
