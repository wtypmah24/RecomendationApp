package com.example.recommendapp.dto;

public record ReviewResponseDto(String id, String viewerUserName, String viewedUserName, Integer score) {
}