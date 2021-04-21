package com.ciro.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ciro.model.Stock;
import com.ciro.repository.StockRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace =Replace.NONE)
public class StockControllerTest {

	@Autowired
	private StockRepository stockRepository;
	
	@Test
	@Rollback(false)
	public void testCreate() {
		Stock s = new Stock();
		s.setName("petr4");
		
		Stock savedStock = stockRepository.save(s);

		assertNotNull(savedStock);
	}
	
	@Test
	@Rollback(false)
	public void testFindStockById() {
		Stock s = new Stock();
		s.setId(1l);
		
		Stock stock = stockRepository.findById(s.getId()).orElse(null);
		
		assertEquals(stock.getId(),1l);
	}
	
	@Test
	@Rollback(false)
	public void testFindAllStocks() {
		List<Stock> stocks =  stockRepository.findAll();
		
		assertThat(stocks).size().isGreaterThan(0);
	}
	
}
