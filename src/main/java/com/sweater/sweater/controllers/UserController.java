package com.sweater.sweater.controllers;

import com.sweater.sweater.entities.RolesEnum;
import com.sweater.sweater.entities.User;
import com.sweater.sweater.repositories.UserRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String userList(Model model) {

        List<User> users = userRepo.findAll();

        model.addAttribute("users", users);

        return "userList";
    }

    @GetMapping("{userId}")
    public String editUser(@PathVariable(name = "userId") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", RolesEnum.values());

        return "editUser";
    }

    @PostMapping
    public String userSave(
            @RequestParam(name = "userId") User user,
            @RequestParam String username,
            @RequestParam Map<String, String> form
    ) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(RolesEnum.values())
                .map(RolesEnum::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(RolesEnum.valueOf(key));
            }
        }

        userRepo.save(user);

        return "redirect:/user";
    }
}
