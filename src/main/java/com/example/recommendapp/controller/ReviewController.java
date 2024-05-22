package com.example.recommendapp.controller;

import com.example.recommendapp.dto.ReviewRequestDto;
import com.example.recommendapp.dto.ReviewResponseDto;
import com.example.recommendapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ReviewResponseDto addReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.addReview(reviewRequestDto);
    }
}
