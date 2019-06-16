/**
 * 
 */
package com.sharath.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sharath.model.ExchangeValue;
import com.sharath.repository.ExchangeRepository;

/**
 * @author shar939
 *
 */
@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment env; 
	
	@Autowired
	private ExchangeRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveUSDtoINR(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchngValue = repository.findByFromAndTo(from, to); 
		exchngValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return exchngValue;  
	}
}
