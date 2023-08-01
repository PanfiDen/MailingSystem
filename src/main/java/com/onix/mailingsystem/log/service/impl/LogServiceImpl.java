package com.onix.mailingsystem.log.service.impl;

import com.onix.mailingsystem.exception.exceptions.BadRequestException;
import com.onix.mailingsystem.exception.exceptions.LogsNotFoundException;
import com.onix.mailingsystem.log.model.mapper.LogMapper;
import com.onix.mailingsystem.log.model.response.GeneralLogsResponse;
import com.onix.mailingsystem.log.model.response.LogResponse;
import com.onix.mailingsystem.log.service.LogService;
import com.onix.mailingsystem.user.model.entity.User;
import com.onix.mailingsystem.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class LogServiceImpl implements LogService {

    private UserRepository userRepository;
    private LogMapper logMapper;


    @Override
    public GeneralLogsResponse getLogsByPage(Integer page, Integer size) {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Invalid page or size value.");
        }

        PageRequest pageable = PageRequest.of(page-1, size);

        Page<User> pageUsers = userRepository.findAllOrderByLogsSizeDesc(pageable);

        if (pageUsers.isEmpty())
            throw new LogsNotFoundException();

        int totalPages = pageUsers.getTotalPages();

        if (page > totalPages) {
            throw new BadRequestException("Page index must not be bigger than expected");
        }

        List<LogResponse> logs = new ArrayList<>(pageUsers.stream()
                .map(logMapper::toLogResponse)
                .toList());

        return GeneralLogsResponse.builder()
                .totalItems(pageUsers.getTotalElements())
                .totalPage(totalPages)
                .currentPage(page)
                .logs(logs)
                .build();
    }
}
