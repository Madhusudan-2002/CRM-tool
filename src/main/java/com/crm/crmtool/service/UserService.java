package com.crm.crmtool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.crmtool.entity.User;
import com.crm.crmtool.repository.UserRepository;

@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user after validating mandatory fields.
     *
     * @param user user details
     * @return saved user
     */
    public User createUser(User user) {

        logger.info("UserService.createUser - start");

        // Validate email
        if (user.getEmail() == null
                || user.getEmail().isBlank()) {

            logger.warn(
                    "UserService.createUser - email is missing"
            );

            throw new RuntimeException(
                    "Email is required"
            );
        }

        // Validate first name
        if (user.getFirstName() == null
                || user.getFirstName().isBlank()) {

            logger.warn(
                    "UserService.createUser - first name is missing"
            );

            throw new RuntimeException(
                    "First name is required"
            );
        }

        // Prevent duplicate users with the same email
        if (userRepository
                .findByEmail(user.getEmail())
                .isPresent()) {

            logger.warn(
                    "UserService.createUser - duplicate email: {}",
                    user.getEmail()
            );

            throw new RuntimeException(
                    "User already exists with this email"
            );
        }

        User savedUser =
                userRepository.save(user);

        logger.info(
                "UserService.createUser - success - userId={}",
                savedUser.getId()
        );

        return savedUser;
    }

    /**
     * Fetches all users using pagination.
     *
     * @param pageable pagination details
     * @return paginated list of users
     */
    public Page<User> getAllUsers(Pageable pageable) {

        logger.info(
                "UserService.getAllUsers - start - page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<User> users =
                userRepository.findAll(pageable);

        logger.info(
                "UserService.getAllUsers - success - count={}",
                users.getNumberOfElements()
        );

        return users;
    }
}



