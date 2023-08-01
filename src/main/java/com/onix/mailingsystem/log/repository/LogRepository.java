package com.onix.mailingsystem.log.repository;

import com.onix.mailingsystem.email.model.MailType;
import com.onix.mailingsystem.log.model.entity.Log;
import com.onix.mailingsystem.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Integer> {
    Optional<Integer> countByMailTypeAndUser(MailType mailType, User user);
    Optional<Log> findFirstByUserOrderByCreatedOnAsc(User user);
    Optional<Log> findFirstByUserOrderByCreatedOnDesc(User user);
}
