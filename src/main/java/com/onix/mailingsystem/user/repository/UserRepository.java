package com.onix.mailingsystem.user.repository;

import com.onix.mailingsystem.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsernameOrEmail(String username, String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Page<User> findAllByOrderByIdDesc(Pageable pageable);
    @Query("SELECT u FROM User u ORDER BY SIZE(u.logs) DESC")
    Page<User> findAllOrderByLogsSizeDesc(Pageable pageable);
}
