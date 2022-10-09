package com.sweater.sweater.controllers;

import com.sweater.sweater.entities.RolesEnum;
import com.sweater.sweater.entities.User;
import com.sweater.sweater.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model) {

        List<User> users = userService.findAll();

        model.addAttribute("users", users);

        return "userList";
    }

    @GetMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editUser(@PathVariable(name = "userId") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", RolesEnum.values());

        return "editUser";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(
            @RequestParam(name = "userId") User user,
            @RequestParam String username,
            @RequestParam Map<String, String> form
    ) {
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.subscribe(currentUser, user);

        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.unsubscribe(currentUser, user);

        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("subscribers/{user}/list")
    public String getSubscribers(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("users", user.getSubscribers());

        return "subscribers";
    }

    @GetMapping("subscriptions/{user}/list")
    public String getSubscriptions(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("users", user.getSubscriptions());

        return "subscribtions";
    }
}
