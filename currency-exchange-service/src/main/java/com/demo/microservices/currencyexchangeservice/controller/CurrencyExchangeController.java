package com.demo.microservices.currencyexchangeservice.controller;

import com.demo.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.demo.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    Logger logger= LoggerFactory.getLogger(CurrencyExchangeController.class);

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
        //  CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
        logger.info("Retrieve exchange value called with {} to {} ",from,to);
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        if(currencyExchange==null){
            throw new RuntimeException("Unable to find data for "+from +" to "+to);
        }
        String host=environment.getProperty("HOSTNAME");
        String port=environment.getProperty("local.server.port");
        String version="v1";
        currencyExchange.setEnvironment(port+" "+version+" "+host);
        return currencyExchange;
    }
}
