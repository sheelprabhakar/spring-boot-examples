package com.code4copy.example.rest;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="Home Controller")
@RestController()
@RequestMapping("/")
@Validated
public class HomeController {
    @GetMapping(produces = "text/html")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().body("hi");
    }
}
