package com.example.recommendapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class User {
    private String id;
    private String userName;
    private String email;
    private String password;
    private String fullName;
}