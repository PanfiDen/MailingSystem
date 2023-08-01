package com.onix.mailingsystem.log.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailCount {
    Integer rest;
    Integer cron;
}
