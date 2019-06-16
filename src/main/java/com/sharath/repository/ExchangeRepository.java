package com.sharath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharath.model.ExchangeValue;

public interface ExchangeRepository extends JpaRepository<ExchangeValue, Long> {
	
	ExchangeValue findByFromAndTo(String from, String to);

}
