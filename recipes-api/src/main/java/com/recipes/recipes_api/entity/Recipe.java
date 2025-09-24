package com.recipes.recipes_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Indexed
public class Recipe {

    @Id
    @Column(name = "external_id")
    private Long externalId;

    @NotBlank(message = "Recipe name cannot be blank")
    @Size(max = 255, message = "Recipe name cannot exceed 255 characters")
    @FullTextField
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> ingredients;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_instructions", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "instruction", columnDefinition = "TEXT")
    @OrderColumn(name = "instruction_order")
    private List<String> instructions;

    @Min(value = 0, message = "Prep time cannot be negative")
    @Column(name = "prep_time_minutes")
    private Integer prepTimeMinutes;

    @Min(value = 0, message = "Cook time cannot be negative")
    @Column(name = "cook_time_minutes")
    private Integer cookTimeMinutes;

    @Min(value = 1, message = "Servings must be at least 1")
    private Integer servings;

    private String difficulty;

    @NotBlank(message = "Cuisine cannot be blank")
    @FullTextField
    private String cuisine;

    @Min(value = 0, message = "Calories cannot be negative")
    @Column(name = "calories_per_serving")
    private Integer caloriesPerServing;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_tags", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Column(name = "user_id")
    private Long userId;

    private String image;

    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0")
    private Double rating;

    @Min(value = 0, message = "Review count cannot be negative")
    @Column(name = "review_count")
    private Integer reviewCount;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_meal_types", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "meal_type")
    private List<String> mealType;

}
