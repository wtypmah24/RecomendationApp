package com.example.recommendapp.controller;

import com.example.recommendapp.dto.UserRatingResponseDto;
import com.example.recommendapp.service.UserRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@Tag(name = "User Rating", description = "Endpoints for managing user ratings")
public class UserRatingController {

    private final UserRatingService userRatingService;

    @GetMapping("/all")
    @Operation(summary = "Get all user ratings", description = "Retrieve all user ratings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all user ratings"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public CompletableFuture<List<UserRatingResponseDto>> getAll() {
        return userRatingService.findAll();
    }

    @GetMapping("/top10")
    @Operation(summary = "Get top 10 user ratings", description = "Retrieve the top 10 user ratings based on score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the top 10 user ratings"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public CompletableFuture<List<UserRatingResponseDto>> getTop10() {
        return userRatingService.getTop10();
    }

    @GetMapping("/ordered")
    @Operation(summary = "Get ordered user ratings", description = "Retrieve user ratings ordered by score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ordered user ratings"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public CompletableFuture<List<UserRatingResponseDto>> getOrdered() {
        return userRatingService.getOrdered();
    }
}
