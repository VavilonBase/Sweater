package com.sweater.sweater.entities.dtos;

import com.sweater.sweater.entities.Message;
import com.sweater.sweater.entities.User;
import com.sweater.sweater.entities.utils.MessageHelper;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class MessageDto {
    private Long id;
    @NotBlank(message = "Message cannot be empty")
    @Length(max = 2048, message = "Message is too long (more than 2KB")
    private String text;
    @Length(max = 255, message = "Tag is too long (more than 256B")
    private String tag;
    private String filename;
    private User author;
    private Long likes;
    private Boolean meLiked;

    public MessageDto(Message message, Long likes, Boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.filename = message.getFilename();
        this.author = message.getAuthor();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(this.author);
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public String getFilename() {
        return filename;
    }

    public User getAuthor() {
        return author;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }
}
