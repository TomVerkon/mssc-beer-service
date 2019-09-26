package guru.springframework.msscbeerservice.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

/**
 * Created by jt on 2019-05-17.
 */
public interface BeerRepository extends PagingAndSortingRepository<Beer, Long> {
    
    Page<Beer> findAllByBeerName(String beerName, Pageable pageable); 
    
    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, Pageable pageable); 
    
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable); 
    
    Optional<Beer> findByUpc(String upc);
    
}
