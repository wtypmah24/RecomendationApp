package com.example.recommendapp.schedule;

import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class Top10Scheduler {
    private final FirebaseDatabase firebaseDatabase;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOutdatedRatings() throws IOException {
        DatabaseReference ratingRef = firebaseDatabase.getReference("userRatings");

        ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DataSnapshot> allRatings = new ArrayList<>();
                for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                    allRatings.add(ratingSnapshot);
                }

                // Sort ratings by score in descending order and get the top 10
                allRatings.sort((a, b) -> {
                    Long scoreA = a.child("score").getValue(Long.class);
                    Long scoreB = b.child("score").getValue(Long.class);
                    return scoreB.compareTo(scoreA);
                });

                Set<String> top10Keys = new HashSet<>();
                for (int i = 0; i < Math.min(10, allRatings.size()); i++) {
                    top10Keys.add(allRatings.get(i).getKey());
                }

                // Delete all ratings not in the top 10
                for (DataSnapshot ratingSnapshot : allRatings) {
                    String ratingKey = ratingSnapshot.getKey();
                    if (!top10Keys.contains(ratingKey)) {
                        try {
                            ratingRef.child(ratingKey).removeValueAsync();
                        } catch (DatabaseException e) {
                            System.out.println("Error deleting rating: " + ratingKey + " - " + e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error deleting outdated ratings: " + databaseError.getMessage());
            }
        });
    }
}
