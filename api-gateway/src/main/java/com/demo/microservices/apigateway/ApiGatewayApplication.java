package com.demo.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
//http://localhost:8765/CURRENCY-CONVERSION/currency-conversion/feign/from/USD/to/INR/quantity/5
	//http://localhost:8765/CURRENCY-EXCHANGE/currency-exchange/from/AUD/to/INR
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
