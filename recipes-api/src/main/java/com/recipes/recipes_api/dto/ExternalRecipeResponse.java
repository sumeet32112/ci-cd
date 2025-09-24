package com.recipes.recipes_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExternalRecipeResponse {

    @JsonProperty("recipes")
    private List<ExternalRecipe> recipes;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("skip")
    private Integer skip;

    @JsonProperty("limit")
    private Integer limit;

    // Inner class for individual recipe
    @Getter
    @Setter
    public static class ExternalRecipe {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("ingredients")
        private List<String> ingredients;

        @JsonProperty("instructions")
        private List<String> instructions;

        @JsonProperty("prepTimeMinutes")
        private Integer prepTimeMinutes;

        @JsonProperty("cookTimeMinutes")
        private Integer cookTimeMinutes;

        @JsonProperty("servings")
        private Integer servings;

        @JsonProperty("difficulty")
        private String difficulty;

        @JsonProperty("cuisine")
        private String cuisine;

        @JsonProperty("caloriesPerServing")
        private Integer caloriesPerServing;

        @JsonProperty("tags")
        private List<String> tags;

        @JsonProperty("userId")
        private Long userId;

        @JsonProperty("image")
        private String image;

        @JsonProperty("rating")
        private Double rating;

        @JsonProperty("reviewCount")
        private Integer reviewCount;

        @JsonProperty("mealType")
        private List<String> mealType;
    }

}