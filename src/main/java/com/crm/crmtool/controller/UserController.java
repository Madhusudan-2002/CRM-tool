package com.crm.crmtool.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crm.crmtool.entity.User;
import com.crm.crmtool.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody User user) {

        User savedUser =
                userService.createUser(user);

        return new ResponseEntity<>(
                savedUser,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            Pageable pageable) {

        Page<User> users =
                userService.getAllUsers(pageable);

        return ResponseEntity.ok(users);
    }
}