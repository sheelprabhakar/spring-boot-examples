package org.c4c.mysql.controller;

import org.c4c.mysql.entity.User;
import org.c4c.mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(@RequestParam String tenantId) {
        List<User> users = userService.getUsersByTenantId(tenantId);
        return ResponseEntity.ok(users);
    }
}
