package com.example.recommendapp.controller;

import com.example.recommendapp.dto.UserRatingResponseDto;
import com.example.recommendapp.service.UserRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class UserRatingController {

    private final UserRatingService userRatingService;

    @GetMapping("/all")
    public CompletableFuture<List<UserRatingResponseDto>> getAll(){
        return userRatingService.findAll();
    }

    @GetMapping("/top10")
    public CompletableFuture<List<UserRatingResponseDto>> getTop10(){
        return userRatingService.getTop10();
    }

    @GetMapping("/ordered")
    public CompletableFuture<List<UserRatingResponseDto>> getOrdered(){
        return userRatingService.getOrdered();
    }
}
