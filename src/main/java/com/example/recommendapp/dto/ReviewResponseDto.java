package com.example.recommendapp.dto;

public record ReviewResponseDto(String id, String viewerEmail, String viewedEmail, Integer score) {
}