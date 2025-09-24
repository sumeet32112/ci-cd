package com.recipes.recipes_api.controller;

import com.recipes.recipes_api.dto.PagedResponse;
import com.recipes.recipes_api.dto.RecipeDTO;
import com.recipes.recipes_api.exception.ExternalApiException;
import com.recipes.recipes_api.exception.RecipeNotFoundException;
import com.recipes.recipes_api.service.IExternalDataService;
import com.recipes.recipes_api.service.IRecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeControllerUnitTest {

    @Mock
    private IRecipeService recipeService;

    @Mock
    private IExternalDataService externalDataService;

    @InjectMocks
    private RecipeController recipeController;  // controller under test

    private RecipeDTO sampleRecipeDTO;

    @BeforeEach
    void setUp() {
        sampleRecipeDTO = new RecipeDTO();
        sampleRecipeDTO.setId(1L);
        sampleRecipeDTO.setName("Test Recipe");
        sampleRecipeDTO.setCuisine("Italian");
        sampleRecipeDTO.setDifficulty("Easy");
        sampleRecipeDTO.setPrepTimeMinutes(15);
        sampleRecipeDTO.setCookTimeMinutes(30);
        sampleRecipeDTO.setServings(4);
        sampleRecipeDTO.setCaloriesPerServing(250);
        sampleRecipeDTO.setRating(4.5);
        sampleRecipeDTO.setReviewCount(10);
    }

    @Test
    void loadRecipes_ShouldReturnSuccess() {
        when(externalDataService.loadRecipesFromExternalApi()).thenReturn(5);

        ResponseEntity<Map<String, Object>> response = recipeController.loadRecipes();

        assertEquals("success", response.getBody().get("status"));
        assertEquals("Recipes loaded successfully", response.getBody().get("message"));
        assertEquals(5, response.getBody().get("loadedCount"));
    }

    @Test
    void loadRecipes_ShouldHandleFailure() {
        when(externalDataService.loadRecipesFromExternalApi())
                .thenThrow(new ExternalApiException("External API failed"));

        assertThrows(ExternalApiException.class, () -> recipeController.loadRecipes());

    }

    @Test
    void searchRecipes_ShouldReturnPage() {
        Page<RecipeDTO> recipePage = new PageImpl<>(List.of(sampleRecipeDTO));
        when(recipeService.searchRecipes(any())).thenReturn(recipePage);

        ResponseEntity<PagedResponse<RecipeDTO>> result = recipeController.searchRecipes("pizza", 0, 10,"name","ASC");

        assertFalse(result.getBody().getContent().isEmpty());
        assertEquals("Test Recipe", result.getBody().getContent().get(0).getName());
    }

    @Test
    void searchRecipes_ShouldReturnEmptyPage() {
        Page<RecipeDTO> emptyPage = new PageImpl<>(Collections.emptyList());
        when(recipeService.searchRecipes(any())).thenReturn(emptyPage);

        ResponseEntity<PagedResponse<RecipeDTO>> result = recipeController.searchRecipes("invalid", 0, 10,"name","ASC");

        assertTrue(result.getBody().getContent().isEmpty());
    }

    @Test
    void getRecipeById_ShouldReturnRecipe() {
        when(recipeService.findById(1L)).thenReturn(sampleRecipeDTO);

        RecipeDTO result = recipeController.getRecipeById(1L).getBody();

        assertNotNull(result);
        assertEquals("Test Recipe", result.getName());
    }

    @Test
    void getRecipeById_ShouldThrowException_WhenNotFound() {
        when(recipeService.findById(99L)).thenThrow(new RecipeNotFoundException("Recipe not found"));

        assertThrows(RecipeNotFoundException.class, () -> recipeController.getRecipeById(99L));
    }

    @Test
    void getAllRecipes_ShouldReturnPage() {
        Page<RecipeDTO> recipePage = new PageImpl<>(List.of(sampleRecipeDTO));
        when(recipeService.searchRecipes(any())).thenReturn(recipePage);

        ResponseEntity<PagedResponse<RecipeDTO>> result = recipeController.getAllRecipes(0, 10,"name","ASC");

        assertEquals(1, result.getBody().getTotalElements());
        assertEquals("Test Recipe", result.getBody().getContent().get(0).getName());
    }
}
