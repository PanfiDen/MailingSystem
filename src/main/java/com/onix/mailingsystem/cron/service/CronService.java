package com.onix.mailingsystem.cron.service;

import com.onix.mailingsystem.cron.model.Cron;
import com.onix.mailingsystem.cron.model.dto.CronDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface CronService {


    ResponseEntity<String> createCron(CronDTO cronDTO) throws ParseException;

    ResponseEntity<String> editCron(String expression, CronDTO cronDTO) throws ParseException ;

    ResponseEntity<String> deleteCron(String expression);

    Page<Cron> getCronsByPage(Integer page, Integer size);

}
