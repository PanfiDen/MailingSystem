package com.onix.mailingsystem.email.service;

import org.springframework.http.ResponseEntity;

public interface EmailService{
    ResponseEntity<String> sendEmail(String to, String subject, String text);

}
