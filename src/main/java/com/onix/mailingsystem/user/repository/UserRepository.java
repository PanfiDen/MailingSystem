package com.onix.mailingsystem.user.repository;

import com.onix.mailingsystem.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsernameOrEmail(String username, String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Page<User> findAllByOrderByIdDesc(Pageable pageable);
}
