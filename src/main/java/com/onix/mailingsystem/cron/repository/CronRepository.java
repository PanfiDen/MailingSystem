package com.onix.mailingsystem.cron.repository;

import com.onix.mailingsystem.cron.model.Cron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CronRepository extends JpaRepository <Cron, Integer> {
    boolean existsByExpression(String expression);
    Optional<Cron> findByExpression(String expression);
    Page<Cron> findAllByOrderById(Pageable pageable);
}
