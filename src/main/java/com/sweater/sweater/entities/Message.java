package com.sweater.sweater.entities;

import com.sweater.sweater.entities.utils.MessageHelper;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;

    @NotBlank(message = "Message cannot be empty")
    @Length(max = 2048, message = "Message is too long (more than 2KB")
    private String text;
    @Length(max = 255, message = "Tag is too long (more than 256B")
    private String tag;

    private String filename;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = { @JoinColumn(name = "message_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likes = new HashSet<User>();

    public Message() {
    }

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(this.author);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }
}
