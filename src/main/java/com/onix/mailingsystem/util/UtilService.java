package com.onix.mailingsystem.util;

import com.onix.mailingsystem.log.model.response.MailCount;
import com.onix.mailingsystem.user.model.dto.UserDTO;
import com.onix.mailingsystem.user.model.entity.User;

import java.text.ParseException;
import java.time.LocalDateTime;

public interface UtilService {
    User findByUsernameOrEmail(String usernameOrEmail);

    void checkIfUserDTOIsEmpty(UserDTO userDTO);

    void checkIfUserDTOEquals(User user, UserDTO updatedUser);

    void checkIfExpressionIsEmpty(String expression);

    void checkIfExpressionEquals(String expression, String newExpression);

    String validateField(String requestField, String field);

    void checkIfInvalidEmail(String email);

    void checkIfInvalidCronExpression(String cronExpression) throws ParseException;

    MailCount getMailCountByTypeAndUser(User user);

    LocalDateTime getFirstLogByUser(User user);

    LocalDateTime getLastLogByUser(User user);
}
