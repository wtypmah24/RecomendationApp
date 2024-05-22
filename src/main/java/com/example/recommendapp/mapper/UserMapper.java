package com.example.recommendapp.mapper;

import com.example.recommendapp.dto.UserRequestDto;
import com.example.recommendapp.dto.UserResponseDto;
import com.example.recommendapp.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestDtoToUser(UserRequestDto userRequestDto);
    UserResponseDto userToUserResponseDto(User user);
}
