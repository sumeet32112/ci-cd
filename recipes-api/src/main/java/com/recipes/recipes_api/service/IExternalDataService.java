package com.recipes.recipes_api.service;

import com.recipes.recipes_api.dto.ExternalRecipeResponse;

public interface IExternalDataService {
    ExternalRecipeResponse fetchAllRecipes();
    int loadRecipesFromExternalApi();
}
