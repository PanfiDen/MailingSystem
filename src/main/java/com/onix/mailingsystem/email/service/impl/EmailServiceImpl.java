package com.onix.mailingsystem.email.service.impl;

import com.onix.mailingsystem.email.service.EmailService;
import com.onix.mailingsystem.util.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;
    private UtilService utilService;

    @Override
    public ResponseEntity<String> sendEmail(String to, String subject, String text){
        utilService.findByUsernameOrEmail(to);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String senderEmail = System.getenv("EMAIL");
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        this.mailSender.send(simpleMailMessage);

        return new ResponseEntity<>("Email has been sent successfully!", HttpStatus.OK);
    }
}
