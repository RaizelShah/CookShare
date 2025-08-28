package com.raizelshahprojects.cookshare.request;

import com.raizelshahprojects.cookshare.model.Recipe;
import com.raizelshahprojects.cookshare.model.User;
import lombok.Data;

@Data
public class CreateRecipeRequest {
    private Recipe recipe;
    private User user;
}
