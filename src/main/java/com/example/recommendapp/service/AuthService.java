package com.example.recommendapp.service;

import com.example.recommendapp.dto.UserRequestDto;
import com.example.recommendapp.dto.UserResponseDto;
import com.example.recommendapp.entity.User;
import com.example.recommendapp.mapper.UserMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper mapper;

    public UserResponseDto registerUser(UserRequestDto candidate){
        User user = mapper.userRequestDtoToUser(candidate);
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword())
                    .setDisplayName(user.getFullName())
                    .setDisabled(false);

            FirebaseAuth.getInstance().createUser(request);
            return mapper.userToUserResponseDto(user);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String loginUser(String email) {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            return "User logged in successfully!";
        } catch (FirebaseAuthException e) {
            return "Error: " + e.getMessage();
        }
    }
}
