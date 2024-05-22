package com.example.recommendapp.repository.impl;

import com.example.recommendapp.entity.UserRating;
import com.example.recommendapp.repository.inter.RatingRepository;
import com.google.firebase.database.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Repository
public class RatingRepositoryImpl implements RatingRepository {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    public CompletableFuture<Void> save(UserRating userRating) {
        return CompletableFuture.runAsync(() -> {
            DatabaseReference userRatingsRef = firebaseDatabase.getReference("userRatings").child(userRating.getUserName());
            userRatingsRef.setValueAsync(userRating);
        }, Executors.newCachedThreadPool());
    }

    @Override
    public CompletableFuture<UserRating> findByUserName(String userName) {
        CompletableFuture<UserRating> future = new CompletableFuture<>();
        DatabaseReference userRatingsRef = firebaseDatabase.getReference("userRatings").child(userName);
        userRatingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserRating userRating = dataSnapshot.getValue(UserRating.class);
                future.complete(userRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }

    @Override
    public CompletableFuture<List<UserRating>> findAll() {
        return queryRatings(firebaseDatabase.getReference("userRatings"));
    }

    @Override
    public CompletableFuture<List<UserRating>> findTop10() {
        return queryRatings(firebaseDatabase.getReference("userRatings").orderByChild("score").limitToLast(10));
    }

    @Override
    public CompletableFuture<List<UserRating>> findOrdered() {
        return queryRatings(firebaseDatabase.getReference("userRatings").orderByChild("score"));
    }

    private CompletableFuture<List<UserRating>> queryRatings(Query query) {
        CompletableFuture<List<UserRating>> future = new CompletableFuture<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserRating> ratingList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserRating userRating = snapshot.getValue(UserRating.class);
                    if (userRating != null) {
                        ratingList.add(userRating);
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
