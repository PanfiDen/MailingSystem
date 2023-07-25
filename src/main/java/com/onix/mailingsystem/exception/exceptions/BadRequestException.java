package com.onix.mailingsystem.exception.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String exception){
        super(exception);
    }
}
