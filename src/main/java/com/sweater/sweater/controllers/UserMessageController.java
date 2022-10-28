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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/user-messages")
public class UserMessageController {
    private final MessageRepo messageRepo;
    private final FileService fileService;
    private final MessageService messageService;

    public UserMessageController(MessageRepo messageRepo, FileService fileService, MessageService messageService) {
        this.messageRepo = messageRepo;
        this.fileService = fileService;
        this.messageService = messageService;
    }

    @GetMapping("{author}")
    private String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "author") User author,
            @RequestParam(required = false) Message message,
            Model model,
            @RequestParam(required = false, defaultValue = "") String filter,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
            ) {
        Page<MessageDto> page = messageService.getMessagePageForUser(pageable, currentUser, author);

        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("url", "/user-messages/" + author.getId());
        model.addAttribute("isCurrentUser", currentUser.equals(author));

        return "userMessages";
    }

    @PostMapping("{user}")
    private String saveMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "user") Long userId,
            @RequestParam(name = "id") Message message,
            @RequestParam(name = "text") String text,
            @RequestParam(name = "tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }

            message.setFilename(fileService.saveFile(file));

            messageRepo.save(message);
        }

        return "redirect:/user-messages/" + userId;
    }
}
