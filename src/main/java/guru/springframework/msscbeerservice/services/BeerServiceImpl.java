package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jt on 2019-06-06.
 */
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
	
	if (null == showInventoryOnHand || !showInventoryOnHand) {
	    return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
	} else {
	    return beerMapper
		    .beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
	}
    }

    @Override
    @Cacheable(cacheNames = "beerUpcCache", key = "#upc", condition = "#showInventoryOnHand == false")
    public BeerDto getByUpc(String upc, Boolean showInventoryOnHand) {
	
	if (null == showInventoryOnHand || !showInventoryOnHand) {
	    return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc).orElseThrow(NotFoundException::new));
	} else {
	    return beerMapper
		    .beerToBeerDtoWithInventory(beerRepository.findByUpc(upc).orElseThrow(NotFoundException::new));
	}
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
	return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
	Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

	beer.setBeerName(beerDto.getBeerName());
	beer.setBeerStyle(beerDto.getBeerStyle().name());
	beer.setPrice(beerDto.getPrice());
	beer.setUpc(beerDto.getUpc());

	return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
	    Boolean showInventoryOnHand) {

	BeerPagedList beerPagedList;
	Page<Beer> beerPage;

	if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
	    beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
	} else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
	    beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
	} else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
	    beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
	} else {
	    beerPage = beerRepository.findAll(pageRequest);
	}
	
	List<BeerDto> beerDtos = null;
	if (null == showInventoryOnHand || !showInventoryOnHand) {
	    beerDtos = beerPage.getContent().stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList());
	} else {
	    beerDtos = beerPage.getContent().stream().map(beerMapper::beerToBeerDtoWithInventory).collect(Collectors.toList());
	}

	beerPagedList = new BeerPagedList(beerDtos,
		PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
		beerPage.getTotalElements());

	return beerPagedList;
    }
}
