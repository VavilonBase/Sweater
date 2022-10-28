package com.sweater.sweater.controllers;

import com.sweater.sweater.entities.dtos.CaptchaResponseDto;
import com.sweater.sweater.entities.User;
import com.sweater.sweater.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${recaptcha.url}")
    private String recaptchaUrl;

    private final UserService userService;
    private final RestTemplate restTemplate;

    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        String fullRecaptchaUrl = String.format("%s?secret=%s&response=%s", recaptchaUrl, recaptchaSecret, captchaResponse);
        CaptchaResponseDto captchaResponseDto = restTemplate.postForObject(fullRecaptchaUrl, Collections.emptyList(), CaptchaResponseDto.class);

        boolean isPasswordConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        boolean isPasswordDifferent = user.getPassword() != null && !user.getPassword().equals(passwordConfirm);
        boolean isCaptchaSuccess = captchaResponseDto.isSuccess();

        if (isPasswordDifferent) model.addAttribute("passwordError", "Passwords are different");
        if (isPasswordConfirmEmpty) model.addAttribute("password2Error", "Password confirm is not be empty");
        if (!isCaptchaSuccess) model.addAttribute("captchaError", "Fill captcha");

        if (isPasswordDifferent || isPasswordConfirmEmpty || !isCaptchaSuccess || bindingResult.hasErrors()) {
            Map<String, String> errorsMap = CollectorUtils.getErrors(bindingResult);
            CollectorUtils.printErrors(errorsMap);

            model.mergeAttributes(errorsMap);

            model.addAttribute("user", user);

            return "registration";

        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateCode(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code not found");
        }

        return "login";
    }
}
