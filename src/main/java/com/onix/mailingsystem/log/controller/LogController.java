package com.onix.mailingsystem.log.controller;

import com.onix.mailingsystem.log.model.response.GeneralLogsResponse;
import com.onix.mailingsystem.log.service.LogService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping
    public GeneralLogsResponse getLogsByPage(@RequestParam(value = "page" , defaultValue = "0") @PositiveOrZero Integer page,
                                             @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(100) Integer size){
        return logService.getLogsByPage(page, size);
    }
}
