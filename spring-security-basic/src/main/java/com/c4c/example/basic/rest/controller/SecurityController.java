package com.c4c.example.basic.rest.controller;

import com.c4c.example.basic.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class SecurityController {
  @Autowired
  UserService userService;
  @GetMapping("/home")
  public  String home(){
    return "This is Home";
  }
  @GetMapping("/student")
  public  String student(){
    return "This is Student";
  }
  @GetMapping("/admin")
  public  String admin(){
    return "This is Admin";
  }
  @PostMapping("/create")
  public String create(@RequestParam("username") String username, @RequestParam("password") String password){
    return  userService.create(username, password);
  }
}