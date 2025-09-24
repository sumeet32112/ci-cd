package com.recipes.recipes_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Schema(description = "Recipe Data Transfer Object")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    @Schema(description = "Recipe ID", example = "1")
    private Long id;

    @NotBlank(message = "Recipe name cannot be blank")
    @Schema(description = "Recipe name", example = "Classic Margherita Pizza", required = true)
    private String name;

    @Schema(description = "List of ingredients")
    private List<String> ingredients;

    @Schema(description = "Cooking instructions")
    private List<String> instructions;

    @Min(value = 0, message = "Prep time cannot be negative")
    @Schema(description = "Preparation time in minutes", example = "20")
    private Integer prepTimeMinutes;

    @Min(value = 0, message = "Cook time cannot be negative")
    @Schema(description = "Cooking time in minutes", example = "15")
    private Integer cookTimeMinutes;

    @Min(value = 1, message = "Servings must be at least 1")
    @Schema(description = "Number of servings", example = "4")
    private Integer servings;

    @Schema(description = "Difficulty level", example = "Easy")
    private String difficulty;

    @NotBlank(message = "Cuisine cannot be blank")
    @Schema(description = "Cuisine type", example = "Italian", required = true)
    private String cuisine;

    @Min(value = 0, message = "Calories cannot be negative")
    @Schema(description = "Calories per serving", example = "300")
    private Integer caloriesPerServing;

    @Schema(description = "Recipe tags")
    private List<String> tags;

    @Schema(description = "User ID who created the recipe")
    private Long userId;

    @Schema(description = "Recipe image URL")
    private String image;

    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0")
    @Schema(description = "Recipe rating", example = "4.6")
    private Double rating;

    @Min(value = 0, message = "Review count cannot be negative")
    @Schema(description = "Number of reviews", example = "3")
    private Integer reviewCount;

    @Schema(description = "Meal types")
    private List<String> mealType;

}
