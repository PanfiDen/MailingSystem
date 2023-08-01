package com.onix.mailingsystem.cron.service.impl;

import com.onix.mailingsystem.cron.model.Cron;
import com.onix.mailingsystem.cron.model.dto.CronDTO;
import com.onix.mailingsystem.cron.repository.CronRepository;
import com.onix.mailingsystem.cron.service.CronService;
import com.onix.mailingsystem.email.service.EmailService;
import com.onix.mailingsystem.exception.exceptions.BadRequestException;
import com.onix.mailingsystem.exception.exceptions.CronNotFoundException;
import com.onix.mailingsystem.user.model.entity.User;
import com.onix.mailingsystem.user.repository.UserRepository;
import com.onix.mailingsystem.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class CronServiceImpl implements CronService {

    @Autowired
    private CronRepository cronRepository;
    @Autowired
    private UtilService utilService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Map<Integer, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    @Override
    public ResponseEntity<String> createCron(CronDTO cronDTO) throws ParseException {
        utilService.checkIfInvalidCronExpression(cronDTO.getNewExpression());
        utilService.checkIfExpressionIsEmpty(cronDTO.getNewExpression());
        if (cronRepository.existsByExpression(cronDTO.getNewExpression().trim())) {
            throw new BadRequestException("Cron with such expression is already exists");
        }

        Cron newCron = Cron.builder()
                .expression(cronDTO.getNewExpression().trim())
                .createdOn(LocalDateTime.now()).build();
        cronRepository.save(newCron);
        scheduleCronJob(newCron);

        return new ResponseEntity<>("Cron has been created successfully!", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<String> editCron(String expression, CronDTO cronDTO) throws ParseException {
        utilService.checkIfInvalidCronExpression(cronDTO.getNewExpression());
        Cron cron = cronRepository.findByExpression(expression.trim()).orElseThrow(CronNotFoundException::new);
        utilService.checkIfExpressionIsEmpty(cronDTO.getNewExpression());
        utilService.checkIfExpressionEquals(expression, cronDTO.getNewExpression());
        cron.setExpression(utilService.validateField(cronDTO.getNewExpression(), expression));
        cronRepository.save(cron);
        return new ResponseEntity<>("Cron has been edited successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteCron(String expression) {
        Cron cron = cronRepository.findByExpression(expression.trim()).orElseThrow(CronNotFoundException::new);
        cronRepository.delete(cron);
        return new ResponseEntity<>("Cron has been deleted successfully!", HttpStatus.OK);
    }

    @Override
    public Page<Cron> getCronsByPage(Integer page, Integer size) {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Invalid page or size value.");
        }

        PageRequest pageable = PageRequest.of(page-1, size);
        Page<Cron> pageCron = cronRepository.findAllByOrderById(pageable);

        if (pageCron.isEmpty())
            throw new CronNotFoundException();

        return pageCron;
    }
    private void scheduleCronJob(Cron cron) {
        String cronExpression = cron.getExpression();
        ScheduledFuture<?> future = executorService.schedule(() -> executeCronJob(cron), getNextExecutionDelay(cronExpression), TimeUnit.SECONDS);
        scheduledTasks.put(cron.getId(), future);
    }

    private void executeCronJob(Cron cron) {

        String subject = "subject";
        String text = "text";
        List<User> users = userRepository.findAll();
        for (User user : users) {
            emailService.sendEmail(user.getEmail(), subject, text);
        }

        scheduleCronJob(cron);
    }

    private long getNextExecutionDelay(String cronExpression) {
        CronExpression cron = CronExpression.parse(cronExpression);
        ZonedDateTime nextTime = cron.next(ZonedDateTime.now().withNano(0));
        Duration duration = Duration.between(ZonedDateTime.now().withNano(0), nextTime);
        return duration.getSeconds();
    }

}
