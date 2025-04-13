package com.c4c.camel.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @PostMapping("/api/process")
    public String processFile(@RequestBody String fileContent) {
        System.out.println("Received file content: " + fileContent);
        return "File processed successfully!";
    }
}