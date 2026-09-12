package com.crm.crmtool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.crmtool.entity.User;
import com.crm.crmtool.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        if (user.getEmail() == null ||
                user.getEmail().isBlank()) {

            throw new RuntimeException("Email is required");
        }

        if (user.getFirstName() == null ||
                user.getFirstName().isBlank()) {

            throw new RuntimeException(
                    "First name is required");
        }

        return userRepository.save(user);
    }

    public Page<User> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable);
    }
}



