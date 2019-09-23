package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;

public class InventoryEvent extends BeerEvent {

    private static final long serialVersionUID = -4943933801359231762L;

    public InventoryEvent(BeerDto beerDto) {
	super(beerDto);
    }

}
