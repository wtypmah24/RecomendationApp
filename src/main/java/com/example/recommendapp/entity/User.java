package com.example.recommendapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String id;
    private String userName;
    private String email;
    private String password;
    private String fullName;
}