package com.onix.mailingsystem.cron.controller;

import com.onix.mailingsystem.cron.model.Cron;
import com.onix.mailingsystem.cron.model.dto.CronDTO;
import com.onix.mailingsystem.cron.service.CronService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/crons")
public class CronController {

    @Autowired
    private CronService cronService;

    @PostMapping
    @Operation(summary = "Create new cron.")
    public ResponseEntity<String> createCron(@RequestBody CronDTO cronDTO) throws ParseException {
        return cronService.createCron(cronDTO);
    }

    @PatchMapping("/{expression}")
    @Operation(summary = "Edit cron by expression.")
    public ResponseEntity<String> editCron(@PathVariable("expression") String expression,
                                           @RequestBody CronDTO cronDTO) throws ParseException {
        return cronService.editCron(expression, cronDTO);
    }

    @DeleteMapping("/{expression}")
    @Operation(summary = "Delete cron by expression.")
    public ResponseEntity<String> deleteCron(@PathVariable("expression") String expression) {
        return cronService.deleteCron(expression);
    }

    @GetMapping
    @Operation(summary = "Get all crons (pagination).")
    public Page<Cron> getCronsByPage(@RequestParam(value = "page" , defaultValue = "1") @PositiveOrZero Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(100) Integer size){
        return cronService.getCronsByPage(page, size);
    }
}
