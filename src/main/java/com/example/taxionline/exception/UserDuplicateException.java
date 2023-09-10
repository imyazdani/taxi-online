package com.example.taxionline.exception;

public class UserDuplicateException extends RuntimeException {

    public UserDuplicateException(String username) {
        super(String.format("User %s is duplicate.", username));
    }
}
