package com.example.recommendapp.repository.inter;

import com.example.recommendapp.entity.UserRating;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RatingRepository {
    CompletableFuture<Void> save(UserRating userRating);

    CompletableFuture<UserRating> findByUserName(String userName);

    CompletableFuture<List<UserRating>> findAll();

    CompletableFuture<List<UserRating>> findTop10();

    CompletableFuture<List<UserRating>> findOrdered();
}
