package com.c4c.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:/input?noop=true") // Monitor the '/input' directory
            .routeId("file-to-rest-route")
            .log("Processing file: ${header.CamelFileName}")
            .convertBodyTo(String.class) // Convert file content to String
            .log("File content: ${body}")
            .setHeader("Content-Type", constant("application/json"))
            .to("http://localhost:8080/api/process") // Send to external REST API
            .log("File sent to REST API successfully.");
    }
}
