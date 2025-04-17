package com.example.camelplay.routes;

import org.apache.camel.builder.RouteBuilder;

public class HttpLogRouteBuilder extends RouteBuilder {

    public String routeId;
    public String url;

    public HttpLogRouteBuilder() {
        // default constructor for reflection-based instantiation
    }
    public HttpLogRouteBuilder(String routeId, String url) {
        this.routeId = routeId;
        this.url = url;
    }

    @Override
    public void configure() {
        from("timer:" + routeId + "?period=10000")
                .routeId(routeId)
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD(url)
                .log("Response from " + url + ": ${body}");
    }
}
