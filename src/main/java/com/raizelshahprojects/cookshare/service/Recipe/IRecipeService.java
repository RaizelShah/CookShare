package com.raizelshahprojects.cookshare.service.Recipe;

import com.raizelshahprojects.cookshare.dto.RecipeDto;
import com.raizelshahprojects.cookshare.model.Recipe;
import com.raizelshahprojects.cookshare.model.User;
import com.raizelshahprojects.cookshare.request.CreateRecipeRequest;
import com.raizelshahprojects.cookshare.request.UpdateRecipeRequest;

import java.util.List;
import java.util.Set;

public interface IRecipeService {
    Recipe createRecipe(CreateRecipeRequest request);

    Recipe getRecipeById(Long recipeId);

    Recipe updateRecipe(Long recipeId, UpdateRecipeRequest request);

    void deleteRecipe(Long recipeId);

    List<Recipe> getAllRecipes();

    Set<String> getRecipesByCategory();

    Set<String> getRecipesByCuisine();

    static Recipe createRecipe(CreateRecipeRequest request, User user) {
        Recipe recipe = new Recipe();
        Recipe createRequest = request.getRecipe();
        recipe.setTitle(createRequest.getTitle());
        recipe.setCuisine(createRequest.getCuisine());
        recipe.setCategory(createRequest.getCategory());
        recipe.setInstruction(createRequest.getInstruction());
        recipe.setDescription(createRequest.getDescription());
        recipe.setPrepTime(createRequest.getPrepTime());
        recipe.setCookTime(createRequest.getCookTime());
        recipe.setIngredients(createRequest.getIngredients());
        recipe.setUser(user);
        return recipe;
    }

    static Recipe updateRecipe(Recipe existingRecipe, UpdateRecipeRequest request) {
        existingRecipe.setTitle(request.getTitle());
        existingRecipe.setDescription(request.getDescription());
        existingRecipe.setInstruction(request.getInstructions());
        existingRecipe.setCuisine(request.getCuisine());
        existingRecipe.setPrepTime(request.getPrepTime());
        existingRecipe.setCookTime(request.getCookTime());
        existingRecipe.setCategory(request.getCategory());
        existingRecipe.setIngredients(request.getIngredients());
        return existingRecipe;
    }

    List<RecipeDto> getConvertedRecipes(List<Recipe> recipes);

    RecipeDto convertToDto(Recipe recipe);
}
