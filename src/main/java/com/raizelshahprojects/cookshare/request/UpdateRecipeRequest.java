package com.raizelshahprojects.cookshare.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRecipeRequest {
    private String title;
    private String description;
    private List<String> ingredients;
    private String instructions;
    private String category;
    private String cuisine;
    private String prepTime;
    private String cookTime;

}
