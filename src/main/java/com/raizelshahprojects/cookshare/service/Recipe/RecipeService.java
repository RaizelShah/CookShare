package com.raizelshahprojects.cookshare.service.Recipe;

import com.raizelshahprojects.cookshare.dto.RecipeDto;
import com.raizelshahprojects.cookshare.dto.UserDto;
import com.raizelshahprojects.cookshare.model.Recipe;
import com.raizelshahprojects.cookshare.model.User;
import com.raizelshahprojects.cookshare.repository.RecipeRepository;
import com.raizelshahprojects.cookshare.repository.UserRepository;
import com.raizelshahprojects.cookshare.request.CreateRecipeRequest;
import com.raizelshahprojects.cookshare.request.UpdateRecipeRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService implements IRecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Recipe createRecipe(CreateRecipeRequest request) {
        if (request == null || request.getUser() == null) {
            throw new IllegalArgumentException("Invalid request");
        }
        User user = Optional.ofNullable(userRepository.findByUsername(request.getUser().getUsername()))
                .map(existingUser -> {
                    existingUser.getRecipe().add(request.getRecipe());
                    return existingUser;
                }).orElseGet(() -> {
                    User newUser = new User(request.getUser().getUsername());
                    userRepository.save(newUser);
                    return newUser;
                });
        Recipe recipe = IRecipeService.createRecipe(request, user);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Long recipeId, UpdateRecipeRequest request) {
        Recipe recipe = getRecipeById(recipeId);
        Recipe updatedRecipe = IRecipeService.updateRecipe(recipe, request);
        return recipeRepository.save(updatedRecipe);
    }

    @Override
    public Recipe getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Set<String> getRecipesByCategory() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe::getCategory)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRecipesByCuisine() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe::getCuisine)
                .collect(Collectors.toSet());
    }

    @Override
    public List<RecipeDto> getConvertedRecipes(List<Recipe> recipes) {
        return recipes
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public RecipeDto convertToDto(Recipe recipe) {
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        UserDto userDto = modelMapper.map(recipe.getUser(), UserDto.class);
        recipeDto.setUser(userDto);
        return recipeDto;
    }
}
