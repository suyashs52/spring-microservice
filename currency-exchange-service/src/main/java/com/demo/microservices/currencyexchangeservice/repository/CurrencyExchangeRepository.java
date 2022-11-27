package com.demo.microservices.currencyexchangeservice.repository;

import com.demo.microservices.currencyexchangeservice.bean.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {

   CurrencyExchange findByFromAndTo(String from,String to);
}
