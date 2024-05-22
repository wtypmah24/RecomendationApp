package com.example.recommendapp.repository.inter;

import com.example.recommendapp.entity.User;

import java.util.concurrent.CompletableFuture;

public interface UserRepository {
    CompletableFuture<Void> save(User user);
    CompletableFuture<String> findByEmail(String email);
    CompletableFuture<Boolean> existsByEmail(String email);

}
