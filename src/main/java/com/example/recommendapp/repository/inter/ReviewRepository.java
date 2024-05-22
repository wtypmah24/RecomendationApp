package com.example.recommendapp.repository.inter;

import com.example.recommendapp.entity.Review;

import java.util.concurrent.CompletableFuture;

public interface ReviewRepository {
    CompletableFuture<Void> save(Review review);
}
