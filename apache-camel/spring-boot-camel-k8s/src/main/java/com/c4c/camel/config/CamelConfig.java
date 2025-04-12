package com.c4c.camel.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Bean
    public CamelContext camelContext() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(sampleRoute());
        context.start();
        return context;
    }

    @Bean
    public RouteBuilder sampleRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                // Define your routes here
            }
        };
    }
}