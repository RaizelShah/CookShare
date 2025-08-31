package com.raizelshahprojects.cookshare.repository;

import com.raizelshahprojects.cookshare.model.Like;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByRecipeId(Long recipeId);

    boolean existsByRecipeId(long id);

    Optional<Like> findFirstByRecipeId(Long recipeId);
}
