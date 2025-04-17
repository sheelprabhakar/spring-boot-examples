package com.example.camelplay.config;

import org.apache.camel.CamelContext;
import org.apache.camel.dsl.yaml.YamlRoutesBuilderLoader;
import org.apache.camel.spi.RoutesBuilderLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelYamlLoaderConfig {

    @Bean
    public RoutesBuilderLoader yamlRoutesBuilderLoader(CamelContext camelContext) {
        YamlRoutesBuilderLoader loader = new YamlRoutesBuilderLoader();
        loader.setCamelContext(camelContext);
        loader.build(); // initializes the loader
        return loader;
    }
}