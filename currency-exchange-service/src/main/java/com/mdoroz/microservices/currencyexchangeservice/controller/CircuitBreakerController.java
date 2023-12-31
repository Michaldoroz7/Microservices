package com.mdoroz.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
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
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
//    @CircuitBreaker(name = "defualt", fallbackMethod = "hardcodedResponse")
//    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String sampleApi() {

//        logger.info("Sample API call received -> ");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy-url", String.class);

        return "sample-api";
    }

    public String hardcodedResponse(Exception exception) {
        return "Fallback response to dummy sample API";
    }

}
