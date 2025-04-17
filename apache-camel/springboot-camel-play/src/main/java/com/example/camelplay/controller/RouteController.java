package com.example.camelplay.controller;

import com.example.camelplay.routes.HttpLogRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesBuilderLoader;
import org.apache.camel.support.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private CamelContext camelContext;
    @Autowired
    private RoutesBuilderLoader yamlRoutesBuilderLoader;

    @GetMapping
    public List<Map<String, String>> listRoutes() {
        return camelContext.getRoutes().stream().map(route -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", route.getId());
            map.put("status", camelContext.getRouteController().getRouteStatus(route.getId()).name());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{routeId}")
    public Map<String, String> getRoute(@PathVariable String routeId) {
        try {
            String status = camelContext.getRouteController().getRouteStatus(routeId).name();
            return Map.of("id", routeId, "status", status);
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    @PostMapping
    public String deployRoute(@RequestBody Map<String, String> request) {
        String routeId = request.get("id");
        String message = request.get("message");
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("timer:" + routeId + "?period=5000")
                            .routeId(routeId)
                            .log("Dynamic route message: " + message);
                }
            });
            return "Route '" + routeId + "' deployed successfully.";
        } catch (Exception e) {
            return "Error deploying route: " + e.getMessage();
        }
    }

    @PutMapping("/{routeId}/stop")
    public String stopRoute(@PathVariable String routeId) throws Exception {
        camelContext.getRouteController().stopRoute(routeId);
        return "Route stopped.";
    }

    @PutMapping("/{routeId}/start")
    public String startRoute(@PathVariable String routeId) throws Exception {
        camelContext.getRouteController().startRoute(routeId);
        return "Route started.";
    }

    @PutMapping("/{routeId}/reload")
    public String reloadRoute(@PathVariable String routeId, @RequestBody Map<String, String> request) throws Exception {
        String message = request.get("message");
        try {
            camelContext.removeRoute(routeId);
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("timer:" + routeId + "?period=5000")
                            .routeId(routeId)
                            .log("[RELOADED] Dynamic route message: " + message);
                }
            });
            return "Route reloaded.";
        } catch (Exception e) {
            return "Error reloading route: " + e.getMessage();
        }
    }

    @DeleteMapping("/{routeId}")
    public String removeRoute(@PathVariable String routeId) throws Exception {
        camelContext.removeRoute(routeId);
        return "Route removed.";
    }

    @PostMapping("/http")
    public String deployHttpRoute(@RequestBody Map<String, String> request) {
        String routeId = request.get("id");
        String url = request.get("url");
        try {
            camelContext.addRoutes(new HttpLogRouteBuilder(routeId, url));
            return "HTTP route '" + routeId + "' deployed to fetch from " + url;
        } catch (Exception e) {
            return "Error deploying HTTP route: " + e.getMessage();
        }
    }

    @PostMapping("/yaml")
    public String deployYamlRoute(@RequestBody String yamlContent) {
        try {
            // Create Camel Resource from raw string content
            Resource resource = ResourceHelper.fromString("yaml:in-memory.yaml", yamlContent);

            // Load the YAML route as RoutesBuilder
            RoutesBuilder routesBuilder = yamlRoutesBuilderLoader.loadRoutesBuilder(resource);

            // Add it to the Camel context
            camelContext.addRoutes(routesBuilder);

            return "YAML route deployed successfully.";
        } catch (Exception e) {
            return "Error deploying YAML route: " + e.getMessage();
        }
    }

    @PostMapping("/java")
    public String deployJavaRoute(@RequestBody Map<String, Object> request) {
        String className = (String) request.get("className");
        Map<String, Object> params = (Map<String, Object>) request.get("params");

        try {
            // Load class dynamically
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // Inject parameters by field name
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(instance, value);
            }

            // Must be a RouteBuilder
            if (!(instance instanceof RoutesBuilder)) {
                return "Provided class is not a Camel RouteBuilder.";
            }

            camelContext.addRoutes((RoutesBuilder) instance);
            return "Java DSL route deployed from: " + className;
        } catch (Exception e) {
            return "Error deploying Java route: " + e.getMessage();
        }
    }
}