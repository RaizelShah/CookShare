package com.raizelshahprojects.cookshare.dto;

import com.raizelshahprojects.cookshare.model.User;
import lombok.Data;

@Data
public class RecipeDto {
    private long id;

    private String title;
    private String description;
    private String instruction;
    private String prepTime;
    private String cookTime;
    private String category;
    private String cuisine;
    private int likeCount;
    private double averageRating;
    private int totalRateCount;
    private UserDto user;

}
