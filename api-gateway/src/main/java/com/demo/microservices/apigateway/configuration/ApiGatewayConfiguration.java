package com.demo.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator getewayRouter(RouteLocatorBuilder builder){
        Function<PredicateSpec, Buildable<Route>> routeFunction=p->p.path("/get")
                .filters(f->f.addRequestHeader("MYHeader","MYURI")
                        .addRequestParameter("Param","MyValue"))
                .uri("http://httpbin.org:80");
        return  builder.routes().route(routeFunction)
                .route(p->p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
               .route(p->p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion-new/**")
                        .filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion/${segment}"))
                        .uri("lb://currency-conversion"))

                .build();
        //if uri start with currency-exchange then redirect it using
        //naming server:eureka and also do load balancing(name on eureka server is currency-exchange)
        //by checking in name server ,find the location of service and do load balancing

        //http://localhost:8765/currency-conversion/feign/from/USD/to/INR/quantity/5
        //http://localhost:8765/currency-conversion-new/feign/from/USD/to/INR/quantity/5
    }
}
