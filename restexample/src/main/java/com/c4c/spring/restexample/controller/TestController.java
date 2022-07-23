package com.c4c.spring.restexample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test/")
    public ResponseEntity<?> sayHello(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
