package com.example.recommendapp.repository.impl;

import com.example.recommendapp.dto.UserResponseDto;
import com.example.recommendapp.entity.User;
import com.example.recommendapp.repository.inter.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public CompletableFuture<Void> save(User user) {
        return CompletableFuture.runAsync(() -> {
            try {
                UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                        .setEmail(user.getEmail())
                        .setPassword(user.getPassword())
                        .setDisplayName(user.getUserName())
                        .setDisabled(false);
                FirebaseAuth.getInstance().createUser(request);
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }
        }, Executors.newCachedThreadPool());
    }

    @Override
    public CompletableFuture<String> findByEmail(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
                return userRecord.getDisplayName();
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }
        }, Executors.newCachedThreadPool());
    }

    @Override
    public CompletableFuture<Boolean> existsByEmail(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                FirebaseAuth.getInstance().getUserByEmail(email);
                return true;
            } catch (FirebaseAuthException e) {
                return false;
            }
        }, Executors.newCachedThreadPool());
    }
}
