package com.demo.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    //@Retry(name = "sample",fallbackMethod = "hardCodedResponse")
    @CircuitBreaker(name = "default",fallbackMethod = "hardCodedResponse")
    //@RateLimiter(name = "default")
    //10s =10000 calls to sample api
    @Bulkhead(name = "default")
    //no of concurrent request allow
    public String sampleApi() {
        //brew install watch
        //curl http://localhost:8000/sample-api
        //watch curl http://localhost:8000/sample-api hit the request in every 2 sec
        //watch -n 0.1 curl http://localhost:8000/sample-api  request in every .1 sec
        //at the time you see it return response without entering into calling method
        logger.info("Sample API call received");
        //throw new RuntimeException();
       ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http:localhost:8080/dummy", String.class);
            return forEntity.getBody();

    }

    public String hardCodedResponse(Exception exception) {
        return "fallback response";
    }
}
