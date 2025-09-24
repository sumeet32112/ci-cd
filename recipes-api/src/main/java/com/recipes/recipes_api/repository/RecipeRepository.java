package com.recipes.recipes_api.repository;

import com.recipes.recipes_api.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE " +
            "(:query IS NULL OR :query = '' OR " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.cuisine) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Recipe> findByNameOrCuisineContainingIgnoreCase(
            @Param("query") String query,
            Pageable pageable);

    boolean existsByExternalId(Long externalId);
}
