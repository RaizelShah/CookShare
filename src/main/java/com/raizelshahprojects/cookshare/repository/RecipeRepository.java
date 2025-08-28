package com.raizelshahprojects.cookshare.repository;

import com.raizelshahprojects.cookshare.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
}
