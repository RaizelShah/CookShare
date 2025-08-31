package com.raizelshahprojects.cookshare.service.Like;

public interface ILikeService {
    int likeRecipe(Long recipeId);

    int unlikeRecipe(Long recipeId);

    Long getLikesCount(Long recipeId);
}
