package com.example.recommendapp.mapper;

import com.example.recommendapp.dto.UserRatingResponseDto;
import com.example.recommendapp.entity.UserRating;
import com.google.firebase.database.DataSnapshot;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRatingMapper {
    UserRatingResponseDto toResponseDto(UserRating userRating);
    List<UserRatingResponseDto> toResponseDtos(Iterable<DataSnapshot> userRating);
}