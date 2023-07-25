package com.onix.mailingsystem.user.service.impl;

import com.onix.mailingsystem.exception.exceptions.BadRequestException;
import com.onix.mailingsystem.exception.exceptions.UserNotFoundException;
import com.onix.mailingsystem.user.model.dto.UserDTO;
import com.onix.mailingsystem.user.model.entity.User;
import com.onix.mailingsystem.user.repository.UserRepository;
import com.onix.mailingsystem.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> createUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Username is already taken.");
        }

        User newUser = User.builder()
                .username(userDTO.getUsername().trim())
                .email(userDTO.getEmail().trim())
                .createdOn(LocalDateTime.now()).build();
        userRepository.save(newUser);

        return new ResponseEntity<>("User has been created successfully!", HttpStatus.OK);
    }

    @Override
    public Page<User> getUsersPage(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<User> pageUsers = userRepository.findAllByOrderByIdDesc(pageable);
        if (pageUsers.isEmpty()) {
            throw new UserNotFoundException();
        }

        return pageUsers;
    }

    @Override
    public User getUserByUsernameOrEmail(String request) {
        return userRepository.findByUsernameOrEmail(request.trim(), request.trim()).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public ResponseEntity<String> editUserByUsernameOrEmail(String request, UserDTO updatedUser) {
        User user = userRepository.findByUsernameOrEmail(request.trim(), request.trim()).orElseThrow(UserNotFoundException::new);
        checkIfFieldsNotEmpty(updatedUser, user);
        userRepository.save(user);
        return new ResponseEntity<>("User has been edited successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteUserByUsernameOrEmail(String request) {
        User user = userRepository.findByUsernameOrEmail(request.trim(), request.trim()).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return new ResponseEntity<>("User has been deleted successfully!", HttpStatus.OK);
    }


    private void checkIfFieldsNotEmpty(UserDTO updatedUser, User user) {
        if (updatedUser == null) {
            throw new BadRequestException("No changes were applied");
        }

        user.setEmail(validateField(updatedUser.getEmail(), user.getEmail()));
        user.setUsername(validateField(updatedUser.getUsername(), user.getUsername()));
    }
    private String validateField(String requestField, String field) {
        return requestField == null ? field : requestField.trim();
    }

}
