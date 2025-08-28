package com.raizelshahprojects.cookshare.controller;

import com.raizelshahprojects.cookshare.dto.RecipeDto;
import com.raizelshahprojects.cookshare.model.Recipe;
import com.raizelshahprojects.cookshare.request.CreateRecipeRequest;
import com.raizelshahprojects.cookshare.request.UpdateRecipeRequest;
import com.raizelshahprojects.cookshare.service.Recipe.IRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final IRecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody CreateRecipeRequest request) {
        Recipe recipe = recipeService.createRecipe(request);
        return ResponseEntity.ok(recipeService.convertToDto(recipe));
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        List<RecipeDto> recipeDto = recipeService.getConvertedRecipes(recipes);

        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping("/{recipeId}/recipe")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        RecipeDto recipeDto = recipeService.convertToDto(recipe);
        return ResponseEntity.ok(recipeDto);

    }

    @PutMapping("/{recipeId}/update")
    public ResponseEntity<RecipeDto> updateRecipe(@RequestBody UpdateRecipeRequest request, @PathVariable Long recipeId) {
        Recipe updatedRecipe = recipeService.updateRecipe(recipeId, request);
        RecipeDto recipeDto = recipeService.convertToDto(updatedRecipe);
        return ResponseEntity.ok(recipeDto);
    }

    @DeleteMapping("/{recipeId}/delete")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<Set<String>> getRecipesByCategory() {
        return ResponseEntity.ok(recipeService.getRecipesByCategory());
    }

    @GetMapping("/cuisines")
    public ResponseEntity<Set<String>> getRecipesByCuisine() {
        return ResponseEntity.ok(recipeService.getRecipesByCuisine());
    }
}
