package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {

    private static final long serialVersionUID = 938264382931294419L;

    public BrewBeerEvent(BeerDto beerDto) {
	super(beerDto);
    }

}
