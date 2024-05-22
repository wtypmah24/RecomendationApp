package com.example.recommendapp.dto;

import lombok.Data;


public record UserRatingResponseDto(String id, String userName, Integer score) {
}
