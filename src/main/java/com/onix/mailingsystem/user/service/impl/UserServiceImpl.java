package com.onix.mailingsystem.user.service.impl;

import com.onix.mailingsystem.exception.exceptions.BadRequestException;
import com.onix.mailingsystem.exception.exceptions.UserNotFoundException;
import com.onix.mailingsystem.user.model.dto.UserDTO;
import com.onix.mailingsystem.user.model.entity.User;
import com.onix.mailingsystem.user.repository.UserRepository;
import com.onix.mailingsystem.user.service.UserService;
import com.onix.mailingsystem.util.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UtilService utilService;

    @Override
    public ResponseEntity<String> createUser(UserDTO userDTO) {
        utilService.checkIfUserDTOIsEmpty(userDTO);
        if (userRepository.existsByUsernameOrEmailIgnoreCase(userDTO.getUsername(), userDTO.getEmail())) {
            throw new BadRequestException("User with such username or email is already exists");
        }
        utilService.checkIfInvalidEmail(userDTO.getEmail());

        User newUser = User.builder()
                .username(userDTO.getUsername().trim())
                .email(userDTO.getEmail().trim())
                .createdOn(LocalDateTime.now()).build();
        userRepository.save(newUser);

        return new ResponseEntity<>("User has been created successfully!", HttpStatus.OK);
    }

    @Override
    public Page<User> getUsersPage(Integer page, Integer size) {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Invalid page or size value.");
        }

        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<User> pageUsers = userRepository.findAllByOrderByIdDesc(pageable);

        if (pageUsers.isEmpty()) {
            throw new UserNotFoundException();
        }

        return pageUsers;
    }

    @Override
    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        return utilService.findByUsernameOrEmail(usernameOrEmail);
    }

    @Override
    public ResponseEntity<String> editUserByUsernameOrEmail(String usernameOrEmail, UserDTO updatedUser) {
        User user = utilService.findByUsernameOrEmail(usernameOrEmail);
        utilService.checkIfUserDTOIsEmpty(updatedUser);
        utilService.checkIfUserDTOEquals(user, updatedUser);
        utilService.checkIfInvalidEmail(updatedUser.getEmail());
        user.setEmail(utilService.validateField(updatedUser.getEmail(), user.getEmail()));
        user.setUsername(utilService.validateField(updatedUser.getUsername(), user.getUsername()));
        userRepository.save(user);
        return new ResponseEntity<>("User has been edited successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteUserByUsernameOrEmail(String usernameOrEmail) {
        User user = utilService.findByUsernameOrEmail(usernameOrEmail);
        userRepository.delete(user);
        return new ResponseEntity<>("User has been deleted successfully!", HttpStatus.OK);
    }
}
