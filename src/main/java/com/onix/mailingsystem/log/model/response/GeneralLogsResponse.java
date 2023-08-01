package com.onix.mailingsystem.log.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class GeneralLogsResponse {
    long totalItems;
    int totalPage;
    int currentPage;
     List<LogResponse> logs;
}
