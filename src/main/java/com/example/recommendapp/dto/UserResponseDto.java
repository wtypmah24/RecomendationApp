package com.example.recommendapp.dto;

import lombok.Data;


public record UserResponseDto(String id, String userName, String email, String password, String fullName) {
}
