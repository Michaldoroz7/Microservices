package com.mdoroz.microservices.limitsservice.controller;


import com.mdoroz.microservices.limitsservice.bean.Limits;
import com.mdoroz.microservices.limitsservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    public LimitsController(Configuration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/limits")
    public Limits getLimits(){
        return new Limits(configuration.getMin(),configuration.getMax());
    }
}
