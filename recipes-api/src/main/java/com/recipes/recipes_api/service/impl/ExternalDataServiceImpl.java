package com.recipes.recipes_api.service.impl;

import com.recipes.recipes_api.dto.ExternalRecipeResponse;
import com.recipes.recipes_api.entity.Recipe;
import com.recipes.recipes_api.exception.ExternalApiException;
import com.recipes.recipes_api.mapper.RecipeMapper;
import com.recipes.recipes_api.repository.RecipeRepository;
import com.recipes.recipes_api.service.IExternalDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalDataServiceImpl implements IExternalDataService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalDataServiceImpl.class);

    @Value("${external.api.recipes.url}")
    private String externalApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    @Retryable(value = {RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public ExternalRecipeResponse fetchAllRecipes() {
        try {
            logger.info("Fetching recipes from external API: {}", externalApiUrl);
            String url = externalApiUrl + "?limit=0"; // Get all recipes
            ExternalRecipeResponse response = restTemplate.getForObject(url, ExternalRecipeResponse.class);

            if (response != null && response.getRecipes() != null) {
                logger.info("Successfully fetched {} recipes from external API", response.getRecipes().size());
                return response;
            } else {
                throw new ExternalApiException("Empty response from external API");
            }
        } catch (RestClientException e) {
            logger.error("Error fetching recipes from external API", e);
            throw new ExternalApiException("Failed to fetch recipes from external API", e);
        }
    }

    @Override
    public int loadRecipesFromExternalApi() {
        logger.info("Starting to load recipes from external API");

        ExternalRecipeResponse response = fetchAllRecipes();
        List<Recipe> newRecipes = response.getRecipes().stream()
                .filter(externalRecipe -> !recipeRepository.existsByExternalId(externalRecipe.getId()))
                .map(recipeMapper::fromExternalRecipe)
                .collect(Collectors.toList());

        if (!newRecipes.isEmpty()) {
            recipeRepository.saveAll(newRecipes);
            logger.info("Successfully loaded {} new recipes into database", newRecipes.size());
        } else {
            logger.info("No new recipes to load - all recipes already exist in database");
        }

        return newRecipes.size();
    }

    @Recover
    public ExternalRecipeResponse recoverFromExternalApiFailure(RestClientException ex) {
        logger.error("Failed to fetch recipes from external API after retries", ex);
        throw new ExternalApiException("External API is currently unavailable after multiple retry attempts", ex);
    }
}
