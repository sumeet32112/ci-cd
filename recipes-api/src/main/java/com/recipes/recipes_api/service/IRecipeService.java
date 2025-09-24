package com.recipes.recipes_api.service;

import com.recipes.recipes_api.dto.RecipeDTO;
import com.recipes.recipes_api.dto.RecipeSearchCriteria;
import org.springframework.data.domain.Page;

public interface IRecipeService {
    Page<RecipeDTO> searchRecipes(RecipeSearchCriteria criteria);
    RecipeDTO findById(Long id);
}
