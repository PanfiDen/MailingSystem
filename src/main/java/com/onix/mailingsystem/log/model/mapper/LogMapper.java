package com.onix.mailingsystem.log.model.mapper;

import com.onix.mailingsystem.log.model.response.LogResponse;
import com.onix.mailingsystem.user.model.entity.User;
import com.onix.mailingsystem.util.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogMapper {
    private UtilService utilService;

    public LogResponse toLogResponse(User user){
        return LogResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .count(utilService.getMailCountByTypeAndUser(user))
                .first(utilService.getFirstLogByUser(user))
                .last(utilService.getLastLogByUser(user))
                .build();
    }
}
