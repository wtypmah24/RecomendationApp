package com.example.recommendapp.service;

import com.example.recommendapp.dto.ReviewRequestDto;
import com.example.recommendapp.dto.ReviewResponseDto;
import com.example.recommendapp.entity.Review;
import com.example.recommendapp.entity.UserRating;
import com.example.recommendapp.mapper.ReviewMapper;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper mapper;
    private final FirebaseDatabase firebaseDatabase;
    private final AuthService authService;


    public ReviewResponseDto addReview(ReviewRequestDto reviewRequestDto) {

        DatabaseReference usersRef = firebaseDatabase.getReference("reviews");

        if (!isUserExist(reviewRequestDto)) {
            throw new RuntimeException("User doesn't exist!");
        }

        String reviewId = usersRef.push().getKey();

        Review review = mapper.reviewRequestDtoToReview(reviewRequestDto);
        review.setId(reviewId);

        updateScore(review);

        usersRef.child(reviewId).setValueAsync(review);

        return mapper.reviewToReviewResponseDto(review);
    }

    private void updateScore(Review review) {
        DatabaseReference userRatingRef = firebaseDatabase.getReference("userRatings").child(getViewedUserName(review));
        userRatingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserRating userRating = dataSnapshot.getValue(UserRating.class);
                    if (userRating != null) {
                        int currentScore = userRating.getScore() != null ? userRating.getScore() : 0;
                        int newScore = review.getScore();
                        userRating.setScore(currentScore + newScore);
                        dataSnapshot.getRef().setValueAsync(userRating);
                    }
                } else {
                    UserRating newUserRating = new UserRating();
                    newUserRating.setId(review.getViewedEmail());
                    newUserRating.setUserName(getViewedUserName(review));
                    newUserRating.setScore(review.getScore());
                    dataSnapshot.getRef().setValueAsync(newUserRating);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new RuntimeException("Database operation cancelled: " + databaseError.getMessage());
            }
        });
    }

    private boolean isUserExist(ReviewRequestDto requestDto) {
        return authService.findUser(requestDto.viewerEmail()) && authService.findUser(requestDto.viewedEmail());
    }

    private String getViewedUserName(Review review) {
        String viewedEmail = review.getViewedEmail();
        return authService.getUserName(viewedEmail);
    }
}
