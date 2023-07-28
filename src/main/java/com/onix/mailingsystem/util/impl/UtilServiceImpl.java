package com.onix.mailingsystem.util.impl;

import com.onix.mailingsystem.exception.exceptions.BadRequestException;
import com.onix.mailingsystem.exception.exceptions.UserNotFoundException;
import com.onix.mailingsystem.user.model.dto.UserDTO;
import com.onix.mailingsystem.user.model.entity.User;
import com.onix.mailingsystem.user.repository.UserRepository;
import com.onix.mailingsystem.util.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UtilServiceImpl implements UtilService {

    private UserRepository userRepository;

    @Override
    public User findByUsernameOrEmail(String usernameOrEmail){
        return userRepository.findByUsernameOrEmail(usernameOrEmail.trim(), usernameOrEmail.trim()).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void checkIfUserDTOIsEmpty(UserDTO userDTO) {
        if (userDTO == null || userDTO.getEmail() == null || userDTO.getUsername() == null){
            throw new BadRequestException("Username and email must not be null");
        }
    }

    @Override
    public void checkIfUserDTOEquals(User user, UserDTO updatedUser) {
        if (updatedUser.getUsername().equals(user.getUsername()) || updatedUser.getEmail().equals(user.getEmail())) {
            throw new BadRequestException("No changes were applied");
        }
    }

    @Override
    public void checkIfExpressionIsEmpty(String expression) {
        if (expression == null){
            throw new BadRequestException("Cron expression cannot be empty");
        }
    }

    @Override
    public void checkIfExpressionEquals(String expression, String newExpression) {
        if (expression.equals(newExpression)) {
            throw new BadRequestException("No changes were applied");
        }
    }

    @Override
    public String validateField(String requestField, String field) {
        return requestField == null ? field : requestField.trim();
    }

    @Override
    public void checkIfInvalidCronExpression(String cronExpression){
        if (!CronExpression.isValidExpression(cronExpression)) {
            throw new BadRequestException("Incorrect cron job input");
        }
    }
}
