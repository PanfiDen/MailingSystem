package com.onix.mailingsystem.user.service;

import com.onix.mailingsystem.user.model.dto.UserDTO;
import com.onix.mailingsystem.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> createUser(UserDTO newUser);

    Page<User> getUsersPage(Integer page, Integer size);

    User getUserByUsernameOrEmail(String request);

    ResponseEntity<String> editUserByUsernameOrEmail(String request, UserDTO updatedUser);

    ResponseEntity<String> deleteUserByUsernameOrEmail(String username);
}
