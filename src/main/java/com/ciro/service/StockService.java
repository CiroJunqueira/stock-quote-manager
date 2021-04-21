package com.ciro.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ciro.exception.ErroException;
import com.ciro.model.Quote;
import com.ciro.model.Stock;
import com.ciro.model.dto.StockDTO;
import com.ciro.model.to.StockTO;
import com.ciro.repository.StockRepository;

@Service
@CacheConfig(cacheNames = { "stock" })
public class StockService {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private ApplicationContext applicationContext;

	public StockTO create(StockTO stockTO) {

		boolean idInCache = false;

		if (null != cacheManager.getCache("stock")) {
			idInCache = getSpringProxy().getStocks().stream().anyMatch(stock -> stock.getId().equals(stockTO.getId()));
		}

		// Caso o stock não tiver no gerenciador
		validateStock(stockTO, idInCache);

		Set<LocalDate> dates = stockTO.getQuotes().keySet();
		Set<Quote> quotes = new HashSet<>();

		// Cria Quotes
		for (LocalDate date : dates) {
			quotes.add(Quote.builder().date(date).value(stockTO.getQuotes().get(date)).build());
		}

		// Buscar Stock do banco de dados
		Stock stock = stockRepository.findByName(stockTO.getId());

		if (null == stock) {
			Stock s = new Stock();
			s.setName(stockTO.getId());
			s.addQuote(quotes);
			stockRepository.save(s);
		} else {
			stock.addQuote(quotes);
			stockRepository.save(stock);
		}

		return stockTO;

	}

	private void validateStock(StockTO stockTO, boolean idExistsInCache) {
		if (!idExistsInCache) {
			ResponseEntity<StockDTO> response = restTemplate
					.getForEntity("http://localhost:8080/stock/" + stockTO.getId(), StockDTO.class);
			if (null == response.getBody()) {
				throw new ErroException("Stock not found. ", HttpStatus.BAD_REQUEST);
			}
		}
	}

	@Cacheable(value = "stocks")
	public List<StockDTO> getStocks() {
		
		ResponseEntity<List<StockDTO>> responseEntity = restTemplate.exchange("http://localhost:8080/stock", HttpMethod.GET, null, new ParameterizedTypeReference<List<StockDTO>>() {});
		List<StockDTO> stocks = responseEntity.getBody();

		return stocks;
	}

	public void clearCache(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}

	private StockService getSpringProxy() {
		return applicationContext.getBean(StockService.class);
	}
	
}
