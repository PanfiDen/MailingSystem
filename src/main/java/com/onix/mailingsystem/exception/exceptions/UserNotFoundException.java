package com.onix.mailingsystem.exception.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User with such credentials is not found");
    }
}
