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
        return queryRatings(firebaseDatabase.getReference("userRatings"));
    }

    public CompletableFuture<List<UserRatingResponseDto>> getTop10() {
        return queryRatings(firebaseDatabase.getReference("userRatings").orderByChild("score").limitToLast(10));
    }

    public CompletableFuture<List<UserRatingResponseDto>> getOrdered() {
        return queryRatings(firebaseDatabase.getReference("userRatings").orderByChild("score"));
    }

    private CompletableFuture<List<UserRatingResponseDto>> queryRatings(Query query) {
        CompletableFuture<List<UserRatingResponseDto>> future = new CompletableFuture<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserRatingResponseDto> ratingList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserRating userRating = snapshot.getValue(UserRating.class);
                    if (userRating != null) {
                        ratingList.add(mapper.toResponseDto(userRating));
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
