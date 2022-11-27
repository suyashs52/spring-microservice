package com.demo.microservices.limitsservice.contoller;

import com.demo.microservices.limitsservice.bean.Limits;
import com.demo.microservices.limitsservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public Limits getAllLimits() {
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
    }
}
