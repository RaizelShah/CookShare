package com.raizelshahprojects.cookshare.service.Like;

import com.raizelshahprojects.cookshare.model.Like;
import com.raizelshahprojects.cookshare.model.Recipe;
import com.raizelshahprojects.cookshare.repository.LikeRepository;
import com.raizelshahprojects.cookshare.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService {
    private final LikeRepository likeRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public int likeRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .map(recipe -> {
                    if (!likeRepository.existsByRecipeId(recipe.getId())) {
                        Like like = new Like(recipe);
                        likeRepository.save(like);
                    }

                    recipe.setLikeCount(recipe.getLikeCount() + 1);
                    recipeRepository.save(recipe);
                    return recipe.getLikeCount();
                }).orElseThrow(() -> new EntityNotFoundException("Recipe not found!"));
    }

    @Override
    public int unlikeRecipe(Long recipeId) {
        return likeRepository.findFirstByRecipeId(recipeId).map(like -> {
            Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
            if (recipe.getLikeCount() > 0) {
                recipe.setLikeCount(recipe.getLikeCount() - 1);
                recipeRepository.save(recipe);
            } else {
                throw new IllegalArgumentException("Like is already zero");
            }
            return recipe.getLikeCount();
        }).orElseThrow(() -> new EntityNotFoundException("No likes found for this recipe"));
    }

    @Override
    public Long getLikesCount(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .map(recipe -> likeRepository.countByRecipeId(recipe.getId()))
                .orElse(0L);
    }
}
