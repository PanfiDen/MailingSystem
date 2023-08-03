package com.onix.mailingsystem.user.controller;

import com.onix.mailingsystem.user.model.dto.UserDTO;
import com.onix.mailingsystem.user.model.entity.User;
import com.onix.mailingsystem.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @Operation(summary = "Create new recipient.")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO newUser){
        return userService.createUser(newUser);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recipients (pagination).")
    public Page<User> getUsers(@RequestParam(value = "page" , defaultValue = "1") @PositiveOrZero Integer page,
                               @RequestParam(value = "size", defaultValue = "5") @Min(1) @Max(100) Integer size){
        return userService.getUsersPage(page, size);
    }

    @GetMapping("/{username-or-email}")
    @Operation(summary = "Get short info about recipient by username or email.")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByUsernameOrEmail(@PathVariable("username-or-email") String request){
        return userService.getUserByUsernameOrEmail(request);
    }

    @PatchMapping("/{username-or-email}")
    @Operation(summary = "Edit recipient by username or email.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> editUserByUsernameOrEmail(@PathVariable("username-or-email") String request,
                                           @RequestBody(required = false) @Valid UserDTO updatedUser){
        return userService.editUserByUsernameOrEmail(request, updatedUser);
    }

    @DeleteMapping("/{username-or-email}")
    @Operation(summary = "Delete recipient by username or email.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUserByUsernameOrEmail(@PathVariable("username-or-email") String username){
        return userService.deleteUserByUsernameOrEmail(username);
    }

}
