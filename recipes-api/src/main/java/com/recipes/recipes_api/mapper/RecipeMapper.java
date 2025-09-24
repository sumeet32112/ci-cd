package com.recipes.recipes_api.mapper;

import com.recipes.recipes_api.dto.ExternalRecipeResponse.ExternalRecipe;
import com.recipes.recipes_api.dto.RecipeDTO;
import com.recipes.recipes_api.entity.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {
    public RecipeDTO toDTO(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        return new RecipeDTO(
                recipe.getExternalId(),
                recipe.getName(),
                recipe.getIngredients(),
                recipe.getInstructions(),
                recipe.getPrepTimeMinutes(),
                recipe.getCookTimeMinutes(),
                recipe.getServings(),
                recipe.getDifficulty(),
                recipe.getCuisine(),
                recipe.getCaloriesPerServing(),
                recipe.getTags(),
                recipe.getUserId(),
                recipe.getImage(),
                recipe.getRating(),
                recipe.getReviewCount(),
                recipe.getMealType()
        );
    }

    public Recipe toEntity(RecipeDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Recipe(
                dto.getId(),
                dto.getName(),
                dto.getIngredients(),
                dto.getInstructions(),
                dto.getPrepTimeMinutes(),
                dto.getCookTimeMinutes(),
                dto.getServings(),
                dto.getDifficulty(),
                dto.getCuisine(),
                dto.getCaloriesPerServing(),
                dto.getTags(),
                dto.getUserId(),
                dto.getImage(),
                dto.getRating(),
                dto.getReviewCount(),
                dto.getMealType()
        );
    }

    public Recipe fromExternalRecipe(ExternalRecipe externalRecipe) {
        if (externalRecipe == null) {
            return null;
        }

        return new Recipe(
                externalRecipe.getId(),
                externalRecipe.getName(),
                externalRecipe.getIngredients(),
                externalRecipe.getInstructions(),
                externalRecipe.getPrepTimeMinutes(),
                externalRecipe.getCookTimeMinutes(),
                externalRecipe.getServings(),
                externalRecipe.getDifficulty(),
                externalRecipe.getCuisine(),
                externalRecipe.getCaloriesPerServing(),
                externalRecipe.getTags(),
                externalRecipe.getUserId(),
                externalRecipe.getImage(),
                externalRecipe.getRating(),
                externalRecipe.getReviewCount(),
                externalRecipe.getMealType()
        );
    }
}
