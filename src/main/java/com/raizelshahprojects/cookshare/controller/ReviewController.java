package com.raizelshahprojects.cookshare.controller;

import com.raizelshahprojects.cookshare.request.ReviewRequest;
import com.raizelshahprojects.cookshare.service.Review.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;


    @PostMapping("/recipe/{recipeId}/review")
    public ResponseEntity<Void> rateRecipe(@RequestBody ReviewRequest request, @PathVariable Long recipeId) {
        System.out.println("The request is: " + request.toString());
        reviewService.addReview(recipeId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipe/{recipeId}/totalRatings")
    public ResponseEntity<Integer> getRateCount(@PathVariable Long recipeId) {
        int totalRatings = reviewService.getTotalReviews(recipeId);
        return ResponseEntity.ok(totalRatings);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRating(@RequestParam Long ratingId, @RequestParam Long recipeId) {
        reviewService.deleteReview(ratingId, recipeId);
        return ResponseEntity.ok("Rating deleted successfully");
    }
}
