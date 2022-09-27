package com.sweater.sweater.services;

import com.sweater.sweater.entities.RolesEnum;
import com.sweater.sweater.entities.User;
import com.sweater.sweater.repositories.UserRepo;
import freemarker.template.utility.StringUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    private final MailSender mailSender;

    public UserService(UserRepo userRepo, MailSender mailSender) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(RolesEnum.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        if (!StringUtils.isEmpty(user.getEmail())) {

            String message = String.format(
                    "Hello, %s!\n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }


        userRepo.save(user);

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        System.out.println(code);
        if (user == null) {
            return false;
        }

        user.setActive(true);
        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }
}
