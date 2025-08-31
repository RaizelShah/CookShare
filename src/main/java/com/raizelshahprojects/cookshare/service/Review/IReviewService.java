package com.raizelshahprojects.cookshare.service.Review;

import com.raizelshahprojects.cookshare.request.ReviewRequest;

public interface IReviewService {
    void addReview(Long recipeId, ReviewRequest reviewRequest);

    void deleteReview(Long recipeId, Long reviewId);

    int getTotalReviews(Long recipeId);
}
