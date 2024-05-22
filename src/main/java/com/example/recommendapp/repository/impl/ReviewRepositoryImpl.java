package com.example.recommendapp.repository.impl;

import com.example.recommendapp.entity.Review;
import com.example.recommendapp.repository.inter.ReviewRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    @Override
    public CompletableFuture<Void> save(Review review) {
        return CompletableFuture.runAsync(() -> {
            DatabaseReference reviewsRef = firebaseDatabase.getReference("reviews");
            String reviewId = reviewsRef.push().getKey();
            review.setId(reviewId);
            reviewsRef.child(reviewId).setValueAsync(review);
        }, Executors.newCachedThreadPool());
    }
}