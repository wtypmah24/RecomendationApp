package com.example.recommendapp.service.todelete;

import com.example.recommendapp.dto.UserRequestDto;
import com.example.recommendapp.dto.UserResponseDto;
import com.example.recommendapp.entity.User;
import com.example.recommendapp.mapper.UserMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling user authentication and registration using Firebase.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper mapper;

    /**
     * Registers a new user with the provided user data.
     *
     * @param candidate The user data transfer object containing user information.
     * @return The response data transfer object with the registered user's information.
     * @throws RuntimeException if the user already exists or if there is an error during registration.
     */
    public UserResponseDto registerUser(UserRequestDto candidate) {
        candidateCheck(candidate);
        User user = mapper.userRequestDtoToUser(candidate);

        if (findUser(user.getEmail())) {
            throw new RuntimeException("User already exists!");
        }

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword())
                    .setDisplayName(user.getUserName())
                    .setDisabled(false);

            FirebaseAuth.getInstance().createUser(request);
            return mapper.userToUserResponseDto(user);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves the username associated with the given email.
     *
     * @param email The email of the user.
     * @return The username associated with the given email.
     * @throws RuntimeException if there is an error retrieving the user information.
     */
    public String getUserName(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return userRecord.getDisplayName();
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email of the user.
     * @return {@code true} if the user exists, {@code false} otherwise.
     */
    public boolean findUser(String email) {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            return true;
        } catch (FirebaseAuthException e) {
            return false;
        }
    }

    /**
     * Validates the candidate user data.
     *
     * @param candidate The user data transfer object containing user information.
     * @throws RuntimeException if any required field is empty.
     */
    private void candidateCheck(UserRequestDto candidate) {
        if (candidate.userName().isEmpty()) {
            throw new RuntimeException("User name cannot be empty!");
        }
        if (candidate.fullName().isEmpty()) {
            throw new RuntimeException("Email cannot be empty!");
        }
        if (candidate.email().isEmpty()) {
            throw new RuntimeException("Password cannot be empty!");
        }
        if (candidate.password().isEmpty()) {
            throw new RuntimeException("Password cannot be empty!");
        }
    }
}
