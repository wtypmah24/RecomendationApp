package com.example.recommendapp.dto;

import lombok.Data;

public record UserRequestDto(String userName, String email, String password, String fullName) {
}