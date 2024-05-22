package com.example.recommendapp.dto;

import lombok.Data;


public record ReviewRequestDto(String viewerEmail, String viewedEmail, Integer score) {
}
