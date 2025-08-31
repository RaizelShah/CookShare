package com.raizelshahprojects.cookshare.service.Review;

import com.raizelshahprojects.cookshare.model.Recipe;
import com.raizelshahprojects.cookshare.model.Review;
import com.raizelshahprojects.cookshare.model.User;
import com.raizelshahprojects.cookshare.repository.RecipeRepository;
import com.raizelshahprojects.cookshare.repository.ReviewRepository;
import com.raizelshahprojects.cookshare.repository.UserRepository;
import com.raizelshahprojects.cookshare.request.ReviewRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public void addReview(Long recipeId, ReviewRequest request) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        reviewRepository.findByRecipeIdAndUserId(recipeId, user.getId())
                .ifPresentOrElse(existingReview -> updateReview(existingReview, request),
                        () -> createReview(request, recipe));

        double averageRating = recipe.getAverageRating();
        recipe.setAverageRating(averageRating);

        int totalReviews = getTotalReviews(recipeId);
        recipe.setTotalRateCount(totalReviews);

        recipeRepository.save(recipe);
    }

    private void updateReview(Review existingReview, ReviewRequest request) {
        existingReview.setStars(request.getStars());
        existingReview.setFeedBack(request.getFeedBack());
        reviewRepository.save(existingReview);
    }

    private void createReview(ReviewRequest request, Recipe recipe) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        Review newReview = new Review();
        newReview.setUser(user);
        newReview.setStars(request.getStars());
        newReview.setFeedBack(request.getFeedBack());
        newReview.setRecipe(recipe);
        newReview.setUser(user);
        reviewRepository.save(newReview);
    }

    @Override
    public void deleteReview(Long recipeId, Long reviewId) {
        reviewRepository.findById(reviewId).ifPresentOrElse(review -> {
            Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe not found!"));
            recipe.getReview().remove(review);
            recipeRepository.save(recipe);

            reviewRepository.delete(review);
        }, () -> {
            throw new EntityNotFoundException("Review not found!");
        });
    }

    @Override
    public int getTotalReviews(Long recipeId) {
        return reviewRepository.countAllByRecipeId(recipeId);
    }
}
