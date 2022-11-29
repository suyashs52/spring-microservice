package com.demo.microservices.currencyconversionservice.controller;

import com.demo.microservices.currencyconversionservice.beans.CurrencyConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    Logger logger= LoggerFactory.getLogger(CurrencyController.class);

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
      logger.info("calculateCurrencyConversion called with {} to with {} for {}",from,to,quantity);
        HashMap<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from", from);
        uriVariable.put("to", to);
        ResponseEntity<CurrencyConversion> forEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariable);
        CurrencyConversion currencyConversion = forEntity.getBody();

        return new CurrencyConversion(currencyConversion.getId()
                , currencyConversion.getFrom(), to, quantity,
                currencyConversion.getConversionMultiple()
                , quantity.multiply(currencyConversion.getConversionMultiple())
                , currencyConversion.getEnvironment()+" rest template");
    }

    @GetMapping("/currency-conversion/feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        logger.info("calculateCurrencyConversion called with {} to with {} for {}",from,to,quantity);

        HashMap<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from", from);
        uriVariable.put("to", to);
        CurrencyConversion currencyConversion = currencyExchangeProxy.retriveExchangeValue(from, to);
        return new CurrencyConversion(currencyConversion.getId()
                , currencyConversion.getFrom(), to, quantity,
                currencyConversion.getConversionMultiple()
                , quantity.multiply(currencyConversion.getConversionMultiple())
                , currencyConversion.getEnvironment()+" feign");
    }
}
