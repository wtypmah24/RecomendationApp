package com.example.recommendapp.service.newv;

import com.example.recommendapp.dto.UserRequestDto;
import com.example.recommendapp.dto.UserResponseDto;
import com.example.recommendapp.entity.User;
import com.example.recommendapp.mapper.UserMapper;
import com.example.recommendapp.repository.inter.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper mapper;
    private final UserRepository userRepository;

    public UserResponseDto registerUser(UserRequestDto candidate) {
        candidateCheck(candidate);
        User user = mapper.userRequestDtoToUser(candidate);

        if (userRepository.existsByEmail(user.getEmail()).join()) {
            throw new RuntimeException("User already exists!");
        }

        userRepository.save(user).join();
        return mapper.userToUserResponseDto(user);
    }

    public String getUserName(String email) {
        return userRepository.findByEmail(email).join();
    }

    private void candidateCheck(UserRequestDto candidate) {
        if (candidate.userName().isEmpty()) {
            throw new RuntimeException("Username cannot be empty!");
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
