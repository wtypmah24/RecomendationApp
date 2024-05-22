package com.example.recommendapp.entity;

import lombok.Data;

@Data
public class UserRating {
    private String id;
    private String userName;
    private Integer score;
}