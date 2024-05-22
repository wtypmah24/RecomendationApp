package com.example.recommendapp.service;

import com.example.recommendapp.dto.UserRatingResponseDto;
import com.example.recommendapp.entity.UserRating;
import com.example.recommendapp.mapper.UserRatingMapper;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserRatingService {

    private final UserRatingMapper mapper;
    private final FirebaseDatabase firebaseDatabase;

    public CompletableFuture<List<UserRatingResponseDto>> findAll() {
        DatabaseReference ratingRef = firebaseDatabase.getReference("userRatings");
        CompletableFuture<List<UserRatingResponseDto>> future = new CompletableFuture<>();

        ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserRatingResponseDto> ratingList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserRating userRating = snapshot.getValue(UserRating.class);
                    if (userRating != null) {
                        UserRatingResponseDto responseDto = mapper.toResponseDto(userRating);
                        ratingList.add(responseDto);
                    }
                }
                future.complete(ratingList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }

    public CompletableFuture<List<UserRatingResponseDto>> getTop10() {
        DatabaseReference ratingRef = firebaseDatabase.getReference("userRatings");
        CompletableFuture<List<UserRatingResponseDto>> future = new CompletableFuture<>();

        Query query = ratingRef.orderByChild("score").limitToLast(10);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserRatingResponseDto> ratingList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserRating userRating = snapshot.getValue(UserRating.class);
                    if (userRating != null) {
                        UserRatingResponseDto responseDto = mapper.toResponseDto(userRating);
                        ratingList.add(responseDto);
                    }
                }
                future.complete(ratingList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }

    public CompletableFuture<List<UserRatingResponseDto>> getOrdered() {
        DatabaseReference ratingRef = firebaseDatabase.getReference("userRatings");
        CompletableFuture<List<UserRatingResponseDto>> future = new CompletableFuture<>();

        Query query = ratingRef.orderByChild("score");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserRatingResponseDto> ratingList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserRating userRating = snapshot.getValue(UserRating.class);
                    if (userRating != null) {
                        UserRatingResponseDto responseDto = mapper.toResponseDto(userRating);
                        ratingList.add(responseDto);
                    }
                }
                future.complete(ratingList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }
}
