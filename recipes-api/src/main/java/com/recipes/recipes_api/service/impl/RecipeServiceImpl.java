package com.recipes.recipes_api.service.impl;

import com.recipes.recipes_api.dto.RecipeDTO;
import com.recipes.recipes_api.dto.RecipeSearchCriteria;
import com.recipes.recipes_api.entity.Recipe;
import com.recipes.recipes_api.exception.RecipeNotFoundException;
import com.recipes.recipes_api.mapper.RecipeMapper;
import com.recipes.recipes_api.repository.RecipeRepository;
import com.recipes.recipes_api.service.IRecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements IRecipeService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public Page<RecipeDTO> searchRecipes(RecipeSearchCriteria criteria) {
        logger.info("Searching recipes with criteria: query='{}', page={}, size={}",
                criteria.getQuery(), criteria.getPage(), criteria.getSize());

        Sort sort = Sort.by(Sort.Direction.fromString(criteria.getSortDirection()), criteria.getSortBy());
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);

        Page<Recipe> recipePage = recipeRepository.findByNameOrCuisineContainingIgnoreCase(
                criteria.getQuery(), pageable);

        logger.info("Found {} recipes matching search criteria", recipePage.getTotalElements());

        return recipePage.map(recipeMapper::toDTO);
    }

    @Override
    public RecipeDTO findById(Long id) {
        logger.info("Finding recipe by ID: {}", id);

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Recipe not found with ID: {}", id);
                    return new RecipeNotFoundException("Recipe not found with ID: " + id);
                });

        logger.info("Successfully found recipe: {}", recipe.getName());
        return recipeMapper.toDTO(recipe);
    }
}
