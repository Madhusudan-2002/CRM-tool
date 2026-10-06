package com.crm.crmtool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crm.crmtool.entity.User;
import com.crm.crmtool.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param user user details
     * @return created user
     */
    @PostMapping
    public ResponseEntity<User> createUser(
            @Valid @RequestBody User user) {

        logger.info(
                "UserController.createUser - request received"
        );

        User savedUser =
                userService.createUser(user);

        logger.info(
                "UserController.createUser - success - userId={}",
                savedUser.getId()
        );

        return new ResponseEntity<>(
                savedUser,
                HttpStatus.CREATED
        );
    }

    /**
     * Fetches all users using pagination.
     *
     * @param pageable pagination information
     * @return paginated users
     */
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            Pageable pageable) {

        logger.info(
                "UserController.getAllUsers - request received - page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<User> users =
                userService.getAllUsers(pageable);

        logger.info(
                "UserController.getAllUsers - success - count={}",
                users.getNumberOfElements()
        );

        return ResponseEntity.ok(users);
    }
}