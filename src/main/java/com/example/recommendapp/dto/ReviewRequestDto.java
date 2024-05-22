package com.example.recommendapp.dto;

import lombok.Data;


public record ReviewRequestDto(String viewerUserName, String viewedUserName, Integer score) {
}
