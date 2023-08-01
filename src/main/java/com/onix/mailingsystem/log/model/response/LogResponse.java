package com.onix.mailingsystem.log.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogResponse {
    String username;
    String email;
    MailCount count;
    LocalDateTime first;
    LocalDateTime last;
}
