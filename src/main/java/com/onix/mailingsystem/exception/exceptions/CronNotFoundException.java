package com.onix.mailingsystem.exception.exceptions;

public class CronNotFoundException extends RuntimeException{
    public CronNotFoundException() {
        super("Cron with such credentials is not found");
    }
}
