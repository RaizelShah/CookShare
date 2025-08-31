package com.raizelshahprojects.cookshare.controller;

import com.raizelshahprojects.cookshare.service.Like.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final ILikeService likeService;

    @PostMapping("/recipe/{recipeId}/like")
    public ResponseEntity<Integer> likeRecipe(@PathVariable Long recipeId) {
        int like = likeService.likeRecipe(recipeId);
        return ResponseEntity.ok(like);
    }

    @PutMapping("/recipe/{recipeId}/unlike")
    public ResponseEntity<Integer> unlikeRecipe(@PathVariable Long recipeId) {
        int unLike = likeService.unlikeRecipe(recipeId);
        return ResponseEntity.ok(unLike);
    }

    @GetMapping("/recipe/{recipeId}/like-count")
    public ResponseEntity<Long> likesCount(@PathVariable Long recipeId) {
        Long likesCount = likeService.getLikesCount(recipeId);
        return ResponseEntity.ok(likesCount);
    }
}
