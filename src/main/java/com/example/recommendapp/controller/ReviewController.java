package com.example.recommendapp.controller;

import com.example.recommendapp.dto.ReviewRequestDto;
import com.example.recommendapp.dto.ReviewResponseDto;
import com.example.recommendapp.service.todelete.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "Review", description = "Endpoints for adding and managing reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Adds a new review.
     *
     * @param reviewRequestDto The review data to add.
     * @return The added review's data.
     */
    @PostMapping("/add")
    @Operation(summary = "Add a new review", description = "Provide review details to add a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid review details"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ReviewResponseDto addReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.addReview(reviewRequestDto);
    }
}
