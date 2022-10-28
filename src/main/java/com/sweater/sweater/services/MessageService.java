package com.sweater.sweater.services;

import com.sweater.sweater.entities.User;
import com.sweater.sweater.entities.dtos.MessageDto;
import com.sweater.sweater.repositories.MessageRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service()
public class MessageService {
    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Page<MessageDto> getMessagePage(Pageable pageable, String filter, User user) {
        if (!filter.isEmpty()) {
            return messageRepo.findByTag(filter, pageable, user);
        } else {
            return messageRepo.findAll(pageable, user);
        }
    }

    public Page<MessageDto> getMessagePageForUser(Pageable pageable, User currentUser, User author) {
        return messageRepo.findByUser(pageable, author, currentUser);
    }
}
