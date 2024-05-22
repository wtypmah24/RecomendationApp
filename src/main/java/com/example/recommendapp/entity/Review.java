package com.example.recommendapp.entity;

import lombok.Data;

@Data
public class Review {
    private String id;
    private String viewerEmail;
    private String viewedEmail;
    private int score;
}