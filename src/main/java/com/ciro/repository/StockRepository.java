package com.ciro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ciro.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{
	
	Stock findByName(String id);

}
