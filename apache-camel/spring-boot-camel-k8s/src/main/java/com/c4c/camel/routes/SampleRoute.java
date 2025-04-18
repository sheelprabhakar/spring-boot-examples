package com.c4c.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class SampleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:tick?period=1000")
            .setBody(constant("Hello from Camel!"))
            .to("log:info");
    }
}