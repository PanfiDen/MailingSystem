package com.onix.mailingsystem.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotEmpty
    @Email
    String email;
    @NotEmpty
    @JsonProperty("username")
    String username;
}
