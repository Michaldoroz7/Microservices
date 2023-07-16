package com.mdoroz.microservices.currencyconversionservice.controller;


import com.mdoroz.microservices.currencyconversionservice.bean.CurrencyConversion;
import com.mdoroz.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;
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
public class CurrencyConversionController {

    Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    private CurrencyExchangeProxy proxy;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = restTemplate
                .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        logger.info("CalculateCurrencyConversion called with {} to {} with {}", from, to, quantity);

        assert currencyConversion != null;
        return new CurrencyConversion(currencyConversion.getId(),
                from,
                to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " " + "REST template");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversion currencyConversion = proxy.getExchangeValue(from, to);

        logger.info("CalculateCurrencyConversion called with {} to {} with {}", from, to, quantity);


        return new CurrencyConversion(currencyConversion.getId(),
                from,
                to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " " + "feign");
    }
}
