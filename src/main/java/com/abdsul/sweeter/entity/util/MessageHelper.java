package com.abdsul.sweeter.entity.util;

import com.abdsul.sweeter.entity.User;

public abstract class MessageHelper {
    public  static String getAuthorName(User user){
        return user != null ? user.getUsername(): "<none>";
    }
}
