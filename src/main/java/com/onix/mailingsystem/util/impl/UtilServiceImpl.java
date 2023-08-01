package com.onix.mailingsystem.util.impl;

import com.onix.mailingsystem.email.model.MailType;
import com.onix.mailingsystem.exception.exceptions.BadRequestException;
import com.onix.mailingsystem.exception.exceptions.LogsNotFoundException;
import com.onix.mailingsystem.exception.exceptions.UserNotFoundException;
import com.onix.mailingsystem.log.model.entity.Log;
import com.onix.mailingsystem.log.model.response.MailCount;
import com.onix.mailingsystem.log.repository.LogRepository;
import com.onix.mailingsystem.user.model.dto.UserDTO;
import com.onix.mailingsystem.user.model.entity.User;
import com.onix.mailingsystem.user.repository.UserRepository;
import com.onix.mailingsystem.util.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UtilServiceImpl implements UtilService {

    private UserRepository userRepository;
    private LogRepository logRepository;

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
    public void checkIfInvalidEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()){
            throw new BadRequestException("Invalid email format");
        }
    }

    @Override
    public void checkIfInvalidCronExpression(String cronExpression){
        if (!CronExpression.isValidExpression(cronExpression)) {
            throw new BadRequestException("Invalid cron format");
        }
    }

    @Override
    public MailCount getMailCountByTypeAndUser(User user){
        if (user == null)
            throw new UserNotFoundException();
        Integer restAmount = logRepository.countByMailTypeAndUser(MailType.REST, user)
                .orElseThrow(LogsNotFoundException::new);

        Integer cronAmount = logRepository.countByMailTypeAndUser(MailType.CRON, user)
                .orElseThrow(LogsNotFoundException::new);

        return new MailCount(restAmount, cronAmount);
    }

    @Override
    public LocalDateTime getFirstLogByUser(User user){
        if (user == null)
            throw new UserNotFoundException();
        Optional<Log> firstlogOptional = logRepository.findFirstByUserOrderByCreatedOnAsc(user);
        return firstlogOptional.map(Log::getCreatedOn).orElse(null);
    }

    @Override
    public LocalDateTime getLastLogByUser(User user){
        if (user == null)
            throw new UserNotFoundException();
        Optional<Log> lastlogOptional = logRepository.findFirstByUserOrderByCreatedOnDesc(user);
        return lastlogOptional.map(Log::getCreatedOn).orElse(null);
    }
}
