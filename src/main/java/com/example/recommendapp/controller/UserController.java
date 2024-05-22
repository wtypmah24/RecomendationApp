package com.example.recommendapp.controller;

import com.example.recommendapp.dto.UserRequestDto;
import com.example.recommendapp.dto.UserResponseDto;
import com.example.recommendapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public UserResponseDto register(@RequestBody UserRequestDto userCandidate) {
        return userService.addUser(userCandidate);
    }

    @GetMapping
    public CompletableFuture<List<UserResponseDto>> getAllUsers() {
        return userService.getAllUsers();
    }
}