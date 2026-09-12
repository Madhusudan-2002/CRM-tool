package com.crm.crmtool.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crm.crmtool.entity.User;
import com.crm.crmtool.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1L);
        user.setFirstName("Amit");
        user.setLastName("Verma");
        user.setEmail("amit.verma@gmail.com");
        user.setPasswordHash("amit123");
        user.setUserRole("SALES_EXECUTIVE");
        user.setIsActive(true);
    }

    @Test
    void createUserSuccess() {

        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("Amit", result.getFirstName());
        assertEquals("amit.verma@gmail.com", result.getEmail());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUserWithoutEmail() {

        user.setEmail(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.createUser(user)
        );

        assertEquals(
                "Email is required",
                exception.getMessage()
        );
    }

    @Test
    void createUserWithoutFirstName() {

        user.setFirstName(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.createUser(user)
        );

        assertEquals(
                "First name is required",
                exception.getMessage()
        );
    }
}




