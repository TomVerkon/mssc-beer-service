package guru.springframework.msscbeerservice.services.brewing;

import javax.transaction.Transactional;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import guru.sfg.common.events.BrewBeerEvent;
import guru.sfg.common.events.InventoryEvent;
import guru.springframework.msscbeerservice.config.JMSConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewBeerListener {
    
    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;
    
    @Transactional
    @JmsListener(destination = JMSConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
	BeerDto beerDto = event.getBeerDto();
	Beer beer = beerRepository.findByUpc(beerDto.getUpc()).orElseThrow(() -> new NotFoundException("Beer with upc: " + beerDto.getUpc()));
	beerDto.setQuantityOnHand(beer.getQuantityToBrew());
	log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH " + beerDto.getQuantityOnHand());
	jmsTemplate.convertAndSend(JMSConfig.NEW_INVENTORY_QUEUE, new InventoryEvent(beerDto));
    }

}
