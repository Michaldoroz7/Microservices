package com.mdoroz.microservices.limitsservice.configuration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("limits-service")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Configuration {

    private int min;
    private int max;
}
