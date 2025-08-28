package com.raizelshahprojects.cookshare.controller;

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
    public ResponseEntity<Recipe> createRecipe(@RequestBody CreateRecipeRequest request) {
        return ResponseEntity.ok(recipeService.createRecipe(request));
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{recipeId}/recipe")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long recipeId) {
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }

    @PutMapping("/{recipeId}/update")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody UpdateRecipeRequest request, @PathVariable Long recipeId) {
        return ResponseEntity.ok(recipeService.updateRecipe(recipeId, request));
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
