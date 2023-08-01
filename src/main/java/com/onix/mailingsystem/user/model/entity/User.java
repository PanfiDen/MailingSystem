package com.onix.mailingsystem.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onix.mailingsystem.log.model.entity.Log;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private LocalDateTime createdOn;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Log> logs;
}
