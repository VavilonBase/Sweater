package com.sweater.sweater.controllers;

import com.sweater.sweater.entities.Message;
import com.sweater.sweater.entities.User;
import com.sweater.sweater.entities.dtos.MessageDto;
import com.sweater.sweater.repositories.MessageRepo;
import com.sweater.sweater.services.FileService;
import com.sweater.sweater.services.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class MessageController {

    private final MessageRepo messageRepo;
    private final FileService fileService;

    private final MessageService messageService;

    public MessageController(MessageRepo messageRepo, FileService fileService, MessageService messageService) {
        this.messageRepo = messageRepo;
        this.fileService = fileService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user) {

        Page<MessageDto> page = messageService.getMessagePage(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = CollectorUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            message.setAuthor(user);

            message.setFilename(fileService.saveFile(file));

            messageRepo.save(message);

            model.addAttribute("message", null);
        }

        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "redirect:/main";
    }

    @GetMapping("/messages/{message}/like")
    public String like(
            @AuthenticationPrincipal User user,
            @PathVariable Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = message.getLikes();
        if (likes.contains(user)) {
            likes.remove(user);
        } else {
            likes.add(user);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();

    }

}
