package com.onix.mailingsystem.log.service;

import com.onix.mailingsystem.log.model.response.GeneralLogsResponse;

public interface LogService {
    GeneralLogsResponse getLogsByPage(Integer page, Integer size);
}
