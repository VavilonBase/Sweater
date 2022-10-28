package com.sweater.sweater.entities.utils;

import com.sweater.sweater.entities.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}
