package com.ciro.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ciro.model.Stock;
import com.ciro.model.to.StockTO;
import com.ciro.repository.StockRepository;
import com.ciro.service.StockService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/stock")
public class StockController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private StockService stockService;
	
	@PostMapping
	public ResponseEntity<StockTO> create(@Valid @RequestBody(required = true) StockTO stockTO){
		StockTO stock = stockService.create(stockTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().buildAndExpand(stock).toUri();
		return ResponseEntity.created(uri).body(stock);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Stock> findById(@PathVariable Long id){
		Stock stock = stockRepository.findById(id).orElse(null);
		return ResponseEntity.ok(stock);
	}
	
	@GetMapping
	public ResponseEntity<Page<Stock>> findAllStocks(Pageable pageable){
		Page<Stock> stocks =  stockRepository.findAll(pageable);
		return ResponseEntity.ok(stocks);
	}
	
	@DeleteMapping("/stockcache")
	public ResponseEntity<?> deleteStockCache() {

		stockService.clearCache("stock");

		return ResponseEntity.ok(null);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void registerItself() {

		JSONObject json = new JSONObject();
		json.put("host", "localhost");
		json.put("port", "8081");
		
		restTemplate.postForEntity("http://localhost:8080/notification", json, String.class);
		
	}

}
