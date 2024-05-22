package com.example.recommendapp.service;

import com.example.recommendapp.dto.ReviewRequestDto;
import com.example.recommendapp.dto.ReviewResponseDto;
import com.example.recommendapp.entity.Review;
import com.example.recommendapp.entity.UserRating;
import com.example.recommendapp.mapper.ReviewMapper;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling review-related operations.
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper mapper;
    private final FirebaseDatabase firebaseDatabase;
    private final AuthService authService;

    /**
     * Adds a new review to the database.
     *
     * @param reviewRequestDto The data transfer object containing review information.
     * @return The response data transfer object with the added review's information.
     * @throws RuntimeException if the user does not exist.
     */
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

    /**
     * Updates the score for the user being reviewed.
     *
     * @param review The review containing the score to be added.
     * @throws RuntimeException if there is an error updating the score in the database.
     */
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

    /**
     * Checks if the users associated with the review request exist.
     *
     * @param requestDto The review request data transfer object.
     * @return {@code true} if both users exist, {@code false} otherwise.
     */
    private boolean isUserExist(ReviewRequestDto requestDto) {
        return authService.findUser(requestDto.viewerEmail()) && authService.findUser(requestDto.viewedEmail());
    }

    /**
     * Retrieves the username of the user being reviewed.
     *
     * @param review The review containing the user's email.
     * @return The username of the user being reviewed.
     */
    private String getViewedUserName(Review review) {
        String viewedEmail = review.getViewedEmail();
        return authService.getUserName(viewedEmail);
    }
}
