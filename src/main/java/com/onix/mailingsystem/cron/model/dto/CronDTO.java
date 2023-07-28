package com.onix.mailingsystem.cron.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CronDTO {
    @NotEmpty
    String newExpression;
}
