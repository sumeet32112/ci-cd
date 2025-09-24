package com.recipes.recipes_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Recipe search criteria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSearchCriteria {

    @Schema(description = "Search query for recipe name or cuisine", example = "pizza")
    private String query;

    @Min(value = 0, message = "Page number cannot be negative")
    @Schema(description = "Page number (0-based)", example = "0", defaultValue = "0")
    private int page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    @Schema(description = "Number of items per page", example = "10", defaultValue = "10")
    private int size = 10;

    @Schema(description = "Sort field", example = "name", defaultValue = "name")
    private String sortBy = "name";

    @Schema(description = "Sort direction (ASC or DESC)", example = "ASC", defaultValue = "ASC")
    private String sortDirection = "ASC";

}