package guru.springframework.msscbeerservice.services.brewing;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import guru.springframework.msscbeerservice.config.JMSConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    
    private final BeerRepository beerRepository;
    private final BeerInventoryService inventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;
    
    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
	Iterable<Beer> beers = beerRepository.findAll();
	beers.forEach(beer -> {
	    Integer beerQOH = inventoryService.getOnhandInventory(beer.getUpc());
	    log.debug("Min on hand is: " + beer.getMinOnHand());
	    log.debug("Inventory is: " +  beerQOH);
	    
	    if (beer.getMinOnHand() >= beerQOH) {
		jmsTemplate.convertAndSend(JMSConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
		
	    }
	});
    }

}
