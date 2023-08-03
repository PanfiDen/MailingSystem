package com.onix.mailingsystem.email.controller;

import com.onix.mailingsystem.email.model.EmailRequest;
import com.onix.mailingsystem.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private EmailService emailService;

    @PostMapping
    @Operation(summary = "Send messages based on predefined parameters.")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        return emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
    }
}
