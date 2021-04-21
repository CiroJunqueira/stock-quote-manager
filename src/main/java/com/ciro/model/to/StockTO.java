package com.ciro.model.to;

import java.time.LocalDate;
import java.util.HashMap;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class StockTO {
	
	@NotNull
	private String id;
	
	@NotNull
	private HashMap<LocalDate, Double> quotes;

}
