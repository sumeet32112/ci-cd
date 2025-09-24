package com.recipes.recipes_api.controller;

import com.recipes.recipes_api.dto.PagedResponse;
import com.recipes.recipes_api.dto.RecipeDTO;
import com.recipes.recipes_api.dto.RecipeSearchCriteria;
import com.recipes.recipes_api.service.IExternalDataService;
import com.recipes.recipes_api.service.IRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/recipes")
@Validated
@CrossOrigin(origins = "*")
@Tag(name = "Recipe Management", description = "APIs for managing recipes")
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private IRecipeService recipeService;

    @Autowired
    private IExternalDataService externalDataService;

    @Operation(summary = "Load recipes from external API",
            description = "Loads all recipes from external DummyJSON API into local H2 database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes loaded successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/load")
    public ResponseEntity<Map<String, Object>> loadRecipes() {
        logger.info("Received request to load recipes from external API");

        int loadedCount = externalDataService.loadRecipesFromExternalApi();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Recipes loaded successfully");
        response.put("loadedCount", loadedCount);
        response.put("status", "success");

        logger.info("Successfully loaded {} recipes", loadedCount);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search recipes",
            description = "Search recipes by name or cuisine with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<PagedResponse<RecipeDTO>> searchRecipes(
            @Parameter(description = "Search query for recipe name or cuisine")
            @RequestParam(required = false) String query,

            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") @Min(1) int size,

            @Parameter(description = "Sort field")
            @RequestParam(defaultValue = "name") String sortBy,

            @Parameter(description = "Sort direction (ASC or DESC)")
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        logger.info("Received search request: query='{}', page={}, size={}", query, page, size);

        RecipeSearchCriteria criteria = new RecipeSearchCriteria(query, page, size, sortBy, sortDirection);
        Page<RecipeDTO> recipes = recipeService.searchRecipes(criteria);
        PagedResponse<RecipeDTO> response = new PagedResponse<>(
                recipes.getContent(),
                recipes.getNumber(),
                recipes.getSize(),
                recipes.getTotalElements(),
                recipes.getTotalPages(),
                recipes.isLast()
        );
        logger.info("Returning {} recipes out of {} total", recipes.getNumberOfElements(), recipes.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get recipe by ID",
            description = "Retrieve a specific recipe by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "400", description = "Invalid recipe ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(
            @Parameter(description = "Recipe ID", required = true)
            @PathVariable @Min(1) Long id) {

        logger.info("Received request to get recipe by ID: {}", id);

        RecipeDTO recipe = recipeService.findById(id);

        logger.info("Successfully found recipe: {}", recipe.getName());
        return ResponseEntity.ok(recipe);
    }

    @Operation(summary = "Get all recipes",
            description = "Retrieve all recipes with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<RecipeDTO>> getAllRecipes(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") @Min(1) int size,

            @Parameter(description = "Sort field")
            @RequestParam(defaultValue = "name") String sortBy,

            @Parameter(description = "Sort direction (ASC or DESC)")
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        logger.info("Received request to get all recipes: page={}, size={}", page, size);

        RecipeSearchCriteria criteria = new RecipeSearchCriteria(null, page, size, sortBy, sortDirection);
        Page<RecipeDTO> recipes = recipeService.searchRecipes(criteria);
        PagedResponse<RecipeDTO> response = new PagedResponse<>(
                recipes.getContent(),
                recipes.getNumber(),
                recipes.getSize(),
                recipes.getTotalElements(),
                recipes.getTotalPages(),
                recipes.isLast()
        );
        logger.info("Returning {} recipes out of {} total", recipes.getNumberOfElements(), recipes.getTotalElements());
        return ResponseEntity.ok(response);
    }
}
