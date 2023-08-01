package com.onix.mailingsystem.exception.exceptions;

public class LogsNotFoundException extends RuntimeException{
    public LogsNotFoundException() {
        super("Logs not found");
    }
}
