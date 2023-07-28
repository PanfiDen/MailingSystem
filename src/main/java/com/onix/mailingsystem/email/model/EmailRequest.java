package com.onix.mailingsystem.email.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmailRequest {
    private String to;
    private String subject;
    private String text;
}