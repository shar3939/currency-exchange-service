/**
 * 
 */
package com.sharath.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchngValue = repository.findByFromAndTo(from, to); 
		exchngValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return exchngValue;  
	}
	
	@GetMapping("/currency-exchange-cb/from/{from}/to/{to}")
	@HystrixCommand(fallbackMethod = "dummyRetrieveExchangeValue")
	public ExchangeValue retrieveExchangeValueCB(@PathVariable String from, @PathVariable String to) {
		/*
		 * ExchangeValue exchngValue = repository.findByFromAndTo(from, to);
		 * exchngValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		 */
		throw new RuntimeException("RUN TIME EXCEPTION");  
	}
	
	public ExchangeValue dummyRetrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchngValue = new ExchangeValue(99999L, from, to, new BigDecimal(60)); 
		log.error("From ERROR Fall back method");
		exchngValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return exchngValue;  
	}
}
