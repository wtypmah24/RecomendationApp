package com.example.recommendapp.mapper;

import com.example.recommendapp.dto.ReviewRequestDto;
import com.example.recommendapp.dto.ReviewResponseDto;
import com.example.recommendapp.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review reviewRequestDtoToReview(ReviewRequestDto reviewRequestDto);

    ReviewResponseDto reviewToReviewResponseDto(Review review);
}
