package com.babacar.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InvoiceConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
