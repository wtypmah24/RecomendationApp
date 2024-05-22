package com.example.recommendapp.entity;

import lombok.Data;

@Data
public class Review {
    private String id;
    private String viewerUserName;
    private String viewedUserName;
    private int score;
}