package com.sweater.sweater.services;

import com.sweater.sweater.entities.RolesEnum;
import com.sweater.sweater.entities.User;
import com.sweater.sweater.repositories.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUser() {
        User user = new User();

        user.setEmail("some@mail.ru");

        boolean isCreatedUser = userService.addUser(user);

        assertTrue(isCreatedUser);
        assertNotNull(user.getActivationCode());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(RolesEnum.USER)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to Sweater"));
    }

    @Test
    public void addUserFailText() {
        User user = new User();

        user.setUsername("John");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("John");

        boolean isCreatedUser = userService.addUser(user);

        assertFalse(isCreatedUser);
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void activateUser() {
        User user = new User();

        user.setActivationCode("bingo!");

        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        assertTrue(isUserActivated);
        assertNull(user.getActivationCode());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void activateUserFailTest() {
        boolean isUserActivated = userService.activateUser("activate me");

        assertFalse(isUserActivated);

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}